package diaspora.dms;

import java.io.Serializable;
import java.util.ArrayList;

import diaspora.dms.DiasporaPolicy.DiasporaGroupPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;

public interface DiasporaPolicyUpcalls {
	public interface  DiasporaClientPolicyUpcalls extends Serializable {
		public void onCreate(DiasporaGroupPolicy group);
		public void setServer(DiasporaServerPolicy server);
		public DiasporaServerPolicy getServer();
		public DiasporaGroupPolicy getGroup();
		public Object onRPC(String method, ArrayList<Object> params) throws Exception;
	}
	
	public interface DiasporaServerPolicyUpcalls extends Serializable {
		public void onCreate(DiasporaGroupPolicy group);
		public DiasporaGroupPolicy getGroup();
		public Object onRPC(String method, ArrayList<Object> params) throws Exception;
		public void onMembershipChange();
	}
	
	public interface DiasporaGroupPolicyUpcalls extends Serializable {
		public void onCreate(DiasporaServerPolicy server);
		public void addServer(DiasporaServerPolicy server);
		public ArrayList<DiasporaServerPolicy> getServers();
		public void onFailure(DiasporaServerPolicy server);
		public DiasporaServerPolicy onRefRequest();
	}
}
