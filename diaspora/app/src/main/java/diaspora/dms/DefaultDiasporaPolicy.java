package diaspora.dms;

import java.util.ArrayList;

public class DefaultDiasporaPolicy extends DiasporaPolicy {
	
	public static class DefaultServerPolicy extends DiasporaServerPolicy {
		private DefaultGroupPolicy group;
		
		@Override
		public DiasporaGroupPolicy getGroup() {
			return group;
		}

		@Override
		public void onMembershipChange() {}

		@Override
		public void onCreate(DiasporaGroupPolicy group) {
			// TODO Auto-generated method stub
			this.group = (DefaultGroupPolicy) group;
		}
	}
	
	public static class DefaultClientPolicy extends DiasporaClientPolicy {
		private DefaultServerPolicy server;
		private DefaultGroupPolicy group;
		
		@Override
		public void setServer(DiasporaServerPolicy server) {
			this.server = (DefaultServerPolicy) server;
		}

		@Override
		public DiasporaServerPolicy getServer() {
			return server;
		}

		@Override
		public DiasporaGroupPolicy getGroup() {
			return group;
		}

		@Override
		public void onCreate(DiasporaGroupPolicy group) {
			// TODO Auto-generated method stub
			this.group = (DefaultGroupPolicy) group;
		}
	}
	
	public static class DefaultGroupPolicy extends DiasporaGroupPolicy {

		@Override
		public void addServer(DiasporaServerPolicy server) {}

		@Override
		public void onFailure(DiasporaServerPolicy server) {}

		@Override
		public DiasporaServerPolicy onRefRequest() {
			return null;
		}

		@Override
		public ArrayList<DiasporaServerPolicy> getServers() {
			return null;
		}

		@Override
		public void onCreate(DiasporaServerPolicy server) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
