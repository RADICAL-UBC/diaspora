package diaspora.dms;

import java.rmi.RemoteException;
import java.util.ArrayList;

import diaspora.common.AppObject;
import diaspora.kernel.common.KernelOID;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;
import diaspora.dms.DiasporaPolicyLibrary.DiasporaGroupPolicyLibrary;
import diaspora.dms.DiasporaPolicyLibrary.DiasporaServerPolicyLibrary;
import diaspora.runtime.Diaspora;

public abstract class DefaultDiasporaPolicyUpcallImpl extends DiasporaPolicyLibrary {

	public abstract static class DefaultDiasporaClientPolicyUpcallImpl extends DiasporaClientPolicyLibrary {
		public Object onRPC(String method, ArrayList<Object> params) throws Exception {
			/* The default behavior is to just perform the RPC to the Policy Server */
			Object ret = null;
			
			try {
				ret = getServer().onRPC(method, params);
			} catch (RemoteException e) {
				setServer(getGroup().onRefRequest());
			}
			return ret;
		}
	}
	
	public abstract static class DefaultDiasporaServerPolicyUpcallImpl extends DiasporaServerPolicyLibrary {
		public Object onRPC(String method, ArrayList<Object> params) throws Exception {
			/* The default behavior is to just invoke the method on the Diaspora Object this Server Policy Object manages */
			return appObject.invoke(method, params);
		}
	}
	
	public abstract static class DefaultDiasporaGroupPolicyUpcallImpl extends DiasporaGroupPolicyLibrary {
		
		/*
		 * INTERNAL FUNCTIONS (Used by Diaspora runtime)
		 */
		public void $__initialize(String appObjectClassName, ArrayList<Object> params) {
			this.appObjectClassName = appObjectClassName;
			this.params = params;
		}
		
		public DiasporaServerPolicy onRefRequest() {
			return getServers().get(0);
		}
	}
}
