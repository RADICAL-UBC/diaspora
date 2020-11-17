package diaspora.oms;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import diaspora.common.DiasporaObjectID;
import diaspora.common.DiasporaObjectNotFoundException;
import diaspora.common.DiasporaReplicaID;
import diaspora.kernel.common.KernelObjectNotFoundException;
import diaspora.runtime.EventHandler;

public class DiasporaObjectManager {
    private HashMap<DiasporaObjectID,DiasporaInstanceManager> diasporaObjects;
    private Random oidGenerator;

    /**
     * Randomly generate a new kernel object id
     * @return
     */
    private DiasporaObjectID generateDiasporaObjectID() {
        return new DiasporaObjectID(oidGenerator.nextInt());
    }

    public DiasporaObjectManager() {
        diasporaObjects = new HashMap<DiasporaObjectID,DiasporaInstanceManager>();
        oidGenerator = new Random(new Date().getTime());
    }

    /**
     * Register a new kernel object
     * @param host
     * @return
     */
    public DiasporaObjectID add(EventHandler dispatcher) {
        DiasporaObjectID oid = generateDiasporaObjectID();
        DiasporaInstanceManager instance = new DiasporaInstanceManager(oid, dispatcher);
        diasporaObjects.put(oid, instance);
        return oid;
    }
    
    public DiasporaReplicaID add(DiasporaObjectID oid, EventHandler dispatcher) throws DiasporaObjectNotFoundException {
    	DiasporaInstanceManager instance = diasporaObjects.get(oid);
    	if (instance == null) {
    		throw new DiasporaObjectNotFoundException("Not a valid Diaspora object id.");
    	}
    	return instance.addReplica(dispatcher);
    }
        
    /**
     * Get the event handler for a Diaspora instance
     * @param oid
     * @return
     * @throws KernelObjectNotFoundException
     */
    public EventHandler get(DiasporaObjectID oid) throws DiasporaObjectNotFoundException {
    	DiasporaInstanceManager instance = diasporaObjects.get(oid);
    	if (instance == null) {
    		throw new DiasporaObjectNotFoundException("Not a valid Diaspora object id.");
    	}
    	return instance.getInstanceDispatcher();
    }
    
    public EventHandler get(DiasporaReplicaID rid) throws DiasporaObjectNotFoundException {
    	DiasporaInstanceManager instance = diasporaObjects.get(rid.getOID());
    	return instance.getReplicaDispatcher(rid);
    }
}