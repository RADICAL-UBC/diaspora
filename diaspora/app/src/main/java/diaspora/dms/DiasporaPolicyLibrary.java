package diaspora.dms;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.harmony.rmi.common.RMIUtil;

import diaspora.common.AppObject;
import diaspora.common.AppObjectStub;
import diaspora.compiler.GlobalStubConstants;
import diaspora.kernel.common.GlobalKernelReferences;
import diaspora.kernel.common.KernelOID;
import diaspora.kernel.common.KernelObjectFactory;
import diaspora.kernel.common.KernelObjectNotCreatedException;
import diaspora.kernel.common.KernelObjectNotFoundException;
import diaspora.kernel.common.KernelObjectStub;
import diaspora.kernel.server.KernelServerImpl;
import diaspora.oms.OMSServer;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;
import diaspora.runtime.Diaspora;

public abstract class DiasporaPolicyLibrary implements DiasporaPolicyUpcalls {
	public static abstract class DiasporaClientPolicyLibrary implements DiasporaClientPolicyUpcalls {
		/*
		 * INTERNAL FUNCTIONS (Used by diaspora runtime system)
		 */
	}

	public static abstract class DiasporaServerPolicyLibrary implements DiasporaServerPolicyUpcalls {
		protected AppObject appObject;
		protected KernelOID oid;
		static Logger logger = Logger.getLogger("diaspora.dms.DiasporaPolicyLibrary");

		private OMSServer oms() {
			return GlobalKernelReferences.nodeServer.oms;
		}

		private KernelServerImpl kernel() {
			return GlobalKernelReferences.nodeServer;
		}

		/*
		 * DIASPORA API FOR SERVER POLICIES
		 */

		/**
		 * Creates a replica of this server and registers it with the group
		 */
		// TODO: Also replicate teh policy ??
		public DiasporaServerPolicy diaspora_replicate() {
			KernelObjectStub serverPolicyStub = null;
			String policyStubClassName = GlobalStubConstants.getPolicyPackageName() + "." + RMIUtil.getShortName(this.getClass()) + GlobalStubConstants.STUB_SUFFIX;
			try {
				serverPolicyStub = (KernelObjectStub) KernelObjectFactory.create(policyStubClassName);
				DiasporaServerPolicy serverPolicy = (DiasporaServerPolicy) kernel().getObject(serverPolicyStub.$__getKernelOID());
				serverPolicy.$__initialize(appObject);
				serverPolicy.$__setKernelOID(serverPolicyStub.$__getKernelOID());
				serverPolicy.onCreate(getGroup());
				getGroup().addServer((DiasporaServerPolicy)serverPolicyStub);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Error("Could not find the class for replication!");
			} catch (KernelObjectNotCreatedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Error("Could not create a replica!");
			} catch (KernelObjectNotFoundException e) {
				e.printStackTrace();
				throw new Error("Could not find object to replicate!");
			}
			return (DiasporaServerPolicy) serverPolicyStub;
		}

		public AppObject diaspora_getAppObject() {
			return appObject;
		}

		public void diaspora_pin(String region) throws RemoteException {
			logger.info("Pinning Diaspora object " + oid.toString() + " to " + region);
			InetSocketAddress server = null;
			try {
				server = oms().getServerInRegion(region);
			} catch (RemoteException e) {
				throw new RemoteException("Could not contact oms.");
			}

			try {
				kernel().moveKernelObjectToServer(server, oid);
			} catch (KernelObjectNotFoundException e) {
				e.printStackTrace();
				throw new Error("Could not find myself on this server!");
			}
		}

		/*
		 * INTERNAL FUNCTIONS
		 */
		/**
		 * Internal function used to initialize the App Object
		 * 
		 * @param appObjectClassName
		 * @param params
		 */
		//TODO: not final (stub overrides it)
		public AppObjectStub $__initialize(Class<?> appObjectStubClass, Object[] params) {
			AppObjectStub actualAppObject = null; // The Actual App Object, managed by an AppObject Handler
			try {
				// Construct the list of classes of the arguments as Class[]
				if (params != null) {
					Class<?>[] argClasses = Diaspora.getParamsClasses(params);
					actualAppObject = (AppObjectStub) appObjectStubClass.getConstructor(argClasses).newInstance(params);
				}
				else
					actualAppObject = (AppObjectStub) appObjectStubClass.newInstance();

				actualAppObject.$__initialize(true);

				// Create the App Object
				appObject = new AppObject(actualAppObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return actualAppObject;
		}

		public void $__initialize(AppObject appObject) {
			this.appObject = appObject;
		}

		public void $__setKernelOID(KernelOID oid) {
			this.oid = oid;
		}

		public KernelOID $__getKernelOID() {
			return oid;
		}
	}

	public static abstract class DiasporaGroupPolicyLibrary implements DiasporaGroupPolicyUpcalls {
		protected String appObjectClassName;
		protected ArrayList<Object> params;
		protected KernelOID oid;

		private OMSServer oms() {
			return GlobalKernelReferences.nodeServer.oms;
		}

		/* 
		 * DIASPORA API FOR GROUP POLICIES
		 */

		public ArrayList<String> diaspora_getRegions() throws RemoteException {
			return oms().getRegions();
		}

		public void $__setKernelOID(KernelOID oid) {
			this.oid = oid;
		}
	}
}
