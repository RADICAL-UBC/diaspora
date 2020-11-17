package diaspora.common;

public class DiasporaReplicaID {
	private DiasporaObjectID oid;
	private int rid;
	
	public DiasporaReplicaID(DiasporaObjectID oid, int rid) {
		this.oid = oid;
		this.rid = rid;
	}
	
	public DiasporaObjectID getOID() {
		return this.oid;
	}
	
	public int getID() {
		return this.rid;
	}
	
	@Override
	public boolean equals(Object obj) {
		final DiasporaReplicaID other = (DiasporaReplicaID) obj;
		if (oid != other.getOID() || rid != other.getID())
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return (oid.getID()<<16)|rid;
	}
	
	@Override
	public String toString() {
		String ret = "DiasporaReplica("+oid+","+rid+")";
		return ret;
	}
}