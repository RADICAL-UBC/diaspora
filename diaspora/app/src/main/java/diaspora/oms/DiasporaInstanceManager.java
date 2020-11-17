package diaspora.oms;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import diaspora.common.DiasporaObjectID;
import diaspora.common.DiasporaObjectNotFoundException;
import diaspora.common.DiasporaReplicaID;
import diaspora.runtime.EventHandler;

public class DiasporaInstanceManager {

	private DiasporaObjectID oid;
	private EventHandler instanceDispatcher;
	private HashMap<DiasporaReplicaID,EventHandler> replicaDispatchers;
	private Random oidGenerator;

	/**
     * Randomly generate a new replica id
     * @return
     */
    private DiasporaReplicaID generateDiasporaReplicaID() {
        return new DiasporaReplicaID(oid, oidGenerator.nextInt());
    }

	
	public DiasporaInstanceManager(DiasporaObjectID oid, EventHandler dispatcher) {
		this.oid = oid;
		instanceDispatcher = dispatcher;
		replicaDispatchers = new HashMap<DiasporaReplicaID,EventHandler>();
		oidGenerator = new Random(new Date().getTime());
	}
	
	public EventHandler getInstanceDispatcher() {
		return instanceDispatcher;
	}
	
	public EventHandler getReplicaDispatcher(DiasporaReplicaID rid) throws DiasporaObjectNotFoundException {
		EventHandler dispatcher = replicaDispatchers.get(rid);
    	if (dispatcher == null) {
    		throw new DiasporaObjectNotFoundException("Not a valid kernel object id.");
    	}
    	return dispatcher;
	}
	
	public DiasporaReplicaID addReplica(EventHandler dispatcher) {
		DiasporaReplicaID rid = generateDiasporaReplicaID();
		replicaDispatchers.put(rid, dispatcher);
		return rid;
	}
}
