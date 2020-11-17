package diaspora.dms;

import java.io.Serializable;
import java.util.ArrayList;

import diaspora.dms.DiasporaPolicy.DiasporaGroupPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;

public interface DiasporaReplicationPolicyUpcalls extends Serializable {
	public interface DiasporaReplicationServerUpcalls extends Serializable {
		public void onCreate(DiasporaGroupPolicy group);
		public DiasporaGroupPolicy getGroup();
		public Object onRPC(String method, ArrayList<Object> params) throws Exception;
		public void onMembershipChange();
	}
	
	public interface DiasporaReplicationGroupUpcalls extends Serializable {
		public void onCreate(DiasporaServerPolicy server);
		public void addServer(DiasporaServerPolicy server);
		public ArrayList<DiasporaServerPolicy> getServers();
		public void onFailure(DiasporaServerPolicy server);
		public DiasporaServerPolicy onRefRequest();
	}
}
