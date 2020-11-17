package diaspora.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.harmony.rmi.common.RMIUtil;

import diaspora.app.DiasporaObject;
import diaspora.common.AppObjectStub;
import diaspora.compiler.GlobalStubConstants;
import diaspora.kernel.common.GlobalKernelReferences;
import diaspora.kernel.common.KernelOID;
import diaspora.kernel.common.KernelObjectFactory;
import diaspora.kernel.common.KernelObjectNotCreatedException;
import diaspora.kernel.common.KernelObjectNotFoundException;
import diaspora.kernel.common.KernelObjectStub;
import diaspora.dms.DefaultDiasporaPolicy;
import diaspora.dms.DefaultDiasporaPolicy.DefaultClientPolicy;
import diaspora.dms.DefaultDiasporaPolicy.DefaultGroupPolicy;
import diaspora.dms.DefaultDiasporaPolicy.DefaultServerPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaClientPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaGroupPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;

/**
 * Used by the developer to create a Diaspora Object given
 * the Application Object class and the Policy Object class.
 * 
 * 
 * @author aaasz
 *
 */
public class Diaspora {
	static Logger logger = Logger.getLogger(Diaspora.class.getName());

	/**
	 * Creates a Diaspora Object:
	 *  [App Object + App Object Stub + Kernel Object (Server Policy) + Kernel Object Stub + Client Policy + Group Policy]
	 * 
	 * @param appObjectClass
	 * @param args
	 * @param diasporaPolicyClass
	 * @param policyArgs
	 * @return The App Object Stub
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws KernelObjectNotCreatedException
	 */
	public static Object new_(Class<?> appObjectClass, Object ... args) {
		try {

			/* Get the policy used by the Diaspora Object we need to create */
			Class<?> policy = getPolicy(appObjectClass.getGenericInterfaces());

			/* Extract the policy component classes (server, client and group) */
			Class<?> [] policyClasses = policy.getDeclaredClasses();

			Class<?> diasporaServerPolicyClass = null;
			Class<?> diasporaClientPolicyClass = null;
			Class<?> diasporaGroupPolicyClass = null;

			for (Class<?> c : policyClasses) {
				if (DiasporaServerPolicy.class.isAssignableFrom(c)) {
					diasporaServerPolicyClass = c;
					continue;
				}
				if (DiasporaClientPolicy.class.isAssignableFrom(c)) {
					diasporaClientPolicyClass = c;
					continue;
				}
				if (DiasporaGroupPolicy.class.isAssignableFrom(c)) {
					diasporaGroupPolicyClass = c;
					continue;
				}
			}

			/* If no policies specified use the defaults */
			if (diasporaServerPolicyClass == null)
				diasporaServerPolicyClass = DefaultServerPolicy.class;
			if (diasporaClientPolicyClass == null)
				diasporaClientPolicyClass = DefaultClientPolicy.class;
			if (diasporaGroupPolicyClass == null)
				diasporaGroupPolicyClass = DefaultGroupPolicy.class;

			/* Create and the Kernel Object for the Group Policy and get the Group Policy Stub */
			DiasporaGroupPolicy groupPolicyStub = (DiasporaGroupPolicy) getPolicyStub(diasporaGroupPolicyClass);

			/* Create the Kernel Object for the Server Policy, and get the Server Policy Stub */
			DiasporaServerPolicy serverPolicyStub = (DiasporaServerPolicy) getPolicyStub(diasporaServerPolicyClass);

			/* Create the Client Policy Object */
			DiasporaClientPolicy client = (DiasporaClientPolicy) diasporaClientPolicyClass.newInstance();

			/* Initialize the group policy and return a local pointer to the object itself */
			DiasporaGroupPolicy groupPolicy = initializeGroupPolicy(groupPolicyStub);

			/* Initialize the server policy and return a local pointer to the object itself */
			DiasporaServerPolicy serverPolicy = initializeServerPolicy(serverPolicyStub);

			/* Create the App Object and return the App Stub */
			AppObjectStub appStub = getAppStub(appObjectClass, serverPolicy, args);

			/* Link everything together */
			client.setServer(serverPolicyStub);
			client.onCreate(groupPolicyStub);
			appStub.$__initialize(client);
			serverPolicy.onCreate(groupPolicyStub);
			groupPolicy.onCreate(serverPolicyStub);

			logger.info("Diaspora Object created: " + appObjectClass.getName());
			return appStub;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
			//throw new AppObjectNotCreatedException();
		}
	}

	/* Returns a pointer to the given Diaspora Object */
	// TODO: how to implement this ?
	public static Object this_(DiasporaObject<?> so) {

		AppObjectStub appObject = (AppObjectStub) so;
		return null;
	}

	/* Returns the policy used by the Diaspora Object */
	private static Class<?> getPolicy(Type[] genericInterfaces) throws Exception {

		for (Type t : genericInterfaces) {
			if (t instanceof ParameterizedType) {
				ParameterizedType extInterfaceType = (ParameterizedType)t;
				Class<?> tClass = (Class<?>)extInterfaceType.getRawType();

				if (!tClass.getName().equals("diaspora.app.DiasporaObject"))
					continue;

				Type[] tt = extInterfaceType.getActualTypeArguments();
				return (Class<?>) tt[0];
			}
			else if (!((Class<?>) t).getName().equals("diaspora.app.DiasporaObject"))
				continue;
			else
				return DefaultDiasporaPolicy.class;
		}

		// Shouldn't get here
		throw new Exception("The Object doesn't implement the DiasporaObject interface.");
	}

	private static KernelObjectStub getPolicyStub(Class<?> policyClass)
			throws ClassNotFoundException, KernelObjectNotCreatedException {
		String policyStubClassName = GlobalStubConstants.getPolicyPackageName() + "." + RMIUtil.getShortName(policyClass) + GlobalStubConstants.STUB_SUFFIX;
		KernelObjectStub policyStub =  KernelObjectFactory.create(policyStubClassName);
		return policyStub;
	}

	private static DiasporaGroupPolicy initializeGroupPolicy(DiasporaGroupPolicy groupPolicyStub)
			throws KernelObjectNotFoundException {
		KernelOID groupOID = ((KernelObjectStub)groupPolicyStub).$__getKernelOID();
		DiasporaGroupPolicy groupPolicy = (DiasporaGroupPolicy) GlobalKernelReferences.nodeServer.getObject(groupOID);
		groupPolicy.$__setKernelOID(groupOID);
		return groupPolicy;
	}

	private static DiasporaServerPolicy initializeServerPolicy(DiasporaServerPolicy serverPolicyStub)
			throws KernelObjectNotFoundException {
		KernelOID serverOID = ((KernelObjectStub)serverPolicyStub).$__getKernelOID();
		DiasporaServerPolicy serverPolicy = (DiasporaServerPolicy) GlobalKernelReferences.nodeServer.getObject(serverOID);
		serverPolicy.$__setKernelOID(serverOID);
		return serverPolicy;
	}

	private static AppObjectStub getAppStub(Class<?> appObjectClass, DiasporaServerPolicy serverPolicy, Object[] args)
			throws Exception {
		String appStubClassName = GlobalStubConstants.getAppPackageName(RMIUtil.getPackageName(appObjectClass)) + "." + RMIUtil.getShortName(appObjectClass) + GlobalStubConstants.STUB_SUFFIX;
		return extractAppStub(serverPolicy.$__initialize(Class.forName(appStubClassName), args));
	}

	private static AppObjectStub extractAppStub(AppObjectStub appObject) throws Exception {
		// Return shallow copy of the kernel object
		AppObjectStub obj = (AppObjectStub)appObject.$__clone();

		// Replace all superclass fields with null
		Field[] fields = obj.getClass().getSuperclass().getFields();
		for (Field f : fields) {
			f.setAccessible(true);
			f.set(obj, null);
		}

		// Replace the values in stub with new values - is this necessary?

		// Update the directInvocation
		obj.$__initialize(false);
		return obj;
	}

	private static Class<?> getParamClassStripStub(Object param) throws ClassNotFoundException {
		String paramClassName = param.getClass().getName();
		int index = paramClassName.lastIndexOf("_");

		if (index == -1)
			return Class.forName(paramClassName);

		if (paramClassName.substring(index).equals(GlobalStubConstants.STUB_SUFFIX))
			/* TODO: Is it correct all times ? */
			paramClassName = param.getClass().getSuperclass().getName();
		//paramClassName = paramClassName.substring(0, index);

		return Class.forName(paramClassName);
	}

	public static Class<?>[] getParamsClasses(Object[] params) throws ClassNotFoundException {
		ArrayList<Class<?>> argClassesList = new ArrayList<Class<?>>();
		for (Object param : params) {
			argClassesList.add(getParamClassStripStub(param));
		}
		Class<?>[] argClasses = new Class<?>[argClassesList.size()];
		return argClassesList.toArray(argClasses);
	}
}
