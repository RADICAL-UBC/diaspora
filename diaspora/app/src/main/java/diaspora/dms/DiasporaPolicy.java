package diaspora.dms;

/**
 *  Class that describes how Diaspora Policies look like. Each policy should extend this class.
 *  Each Diaspora Policy contains a Server Policy, a Client Policy and a Group Policy.
 *  The Policies contain a set of internal functions (used by the diaspora runtime system), a
 *  set of upcall functions that are called when an event happened and a set of functions that
 *  implement the diaspora API for policies.
 */
public abstract class DiasporaPolicy extends DefaultDiasporaPolicyUpcallImpl {
	public abstract static class DiasporaClientPolicy extends DefaultDiasporaClientPolicyUpcallImpl {

	}
	
	public abstract static class DiasporaServerPolicy extends DefaultDiasporaServerPolicyUpcallImpl {
	    
	}

	public abstract static class DiasporaGroupPolicy extends DefaultDiasporaGroupPolicyUpcallImpl {

	}
}
