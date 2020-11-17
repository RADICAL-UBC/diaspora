package diaspora.dms.servercache;

import java.util.ArrayList;



import diaspora.dms.DiasporaPolicy;
import diaspora.dms.cache.CacheLeasePolicy.CacheLeaseServerPolicy;

/* 
 * This class must be applied to an object that extends the List class.
 * 
 * It interposes on each method call, stores everything to disk and caches sublists. (see Facebook's TAO paper)
 */

public class DurableOnDemandCachedList extends DiasporaPolicy {

	public static class DurableOnDemandCachedListClientPolicy extends DiasporaClientPolicy {
		private DurableOnDemandCachedListServerPolicy server;
		private DurableOnDemandCachedListGroupPolicy group;

		@Override
		public void onCreate(DiasporaGroupPolicy group) {
			this.group = (DurableOnDemandCachedListGroupPolicy) group;
		}

		@Override
		public DiasporaGroupPolicy getGroup() {
			return group;
		}

		@Override
		public DiasporaServerPolicy getServer() {
			return server;
		}

		@Override
		public void setServer(DiasporaServerPolicy server) {
			this.server = (DurableOnDemandCachedListServerPolicy) server;
		}

		@Override
		public Object onRPC(String method, ArrayList<Object> params) throws Exception {
			/* Switch on the method we need to execute */



			return null;
		}
	}


	// TODO: think about concurrency
	public static class DurableOnDemandCachedListServerPolicy extends DiasporaServerPolicy {
		private DurableOnDemandCachedListGroupPolicy group;
		int listSize;  // cache the size of the list
		int numMisses; // to automatically grow the cache if possible


		@Override
		public void onCreate(DiasporaGroupPolicy group) {
			// TODO Auto-generated method stub
			this.group = (DurableOnDemandCachedListGroupPolicy) group;
		}

		@Override
		public DiasporaGroupPolicy getGroup() {
			return group;
		}

		@Override
		public void onMembershipChange() {

		}
		
		
		@Override
		public Object onRPC(String method, ArrayList<Object> params) throws Exception {
			return null;
		}
		
	}

	public static class DurableOnDemandCachedListGroupPolicy extends DiasporaGroupPolicy {
		CacheLeaseServerPolicy server;

		@Override
		public void addServer(DiasporaServerPolicy server) {
			this.server = (CacheLeaseServerPolicy) server;
		}

		@Override
		public void onFailure(DiasporaServerPolicy server) {

		}

		@Override
		public DiasporaServerPolicy onRefRequest() {
			return server;
		}

		@Override
		public ArrayList<DiasporaServerPolicy> getServers() {
			return null;
		}

		@Override
		public void onCreate(DiasporaServerPolicy server) {
			addServer(server);
		}
	}
}
