package diaspora.dms;

import java.io.Serializable;
import java.util.ArrayList;

import diaspora.dms.DiasporaPolicy.DiasporaGroupPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;

public interface DiasporaCachingPolicyUpcalls extends Serializable {
	
	public interface  DiasporaCachingClientUpcalls extends Serializable {
		public void onCreate(DiasporaGroupPolicy group);
		public void setServer(DiasporaServerPolicy server);
		public DiasporaServerPolicy getServer();
		public DiasporaGroupPolicy getGroup();
		public Object onRPC(String method, ArrayList<Object> params) throws Exception;
	}
	
	public interface DiasporaCachingServerUpcalls extends Serializable {
		public void onCreate(DiasporaGroupPolicy group);
		public DiasporaGroupPolicy getGroup();
		public Object onRPC(String method, ArrayList<Object> params) throws Exception;
	}
	

}
