package diaspora.kernel.client;

import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.logging.Logger;

import diaspora.kernel.common.GlobalKernelReferences;
import diaspora.kernel.common.KernelOID;
import diaspora.kernel.common.KernelObjectMigratingException;
import diaspora.kernel.common.KernelObjectNotFoundException;
import diaspora.kernel.common.KernelObjectStub;
import diaspora.kernel.common.KernelRPC;
import diaspora.kernel.common.KernelRPCException;
import diaspora.kernel.server.KernelObject;
import diaspora.kernel.server.KernelServer;
import diaspora.oms.OMSServer;

/** 
 * Client-side object for making Diaspora kernel RPCs.
 * 
 * @author iyzhang
 *
 */
public class KernelClient {
	/** Stub for the OMS */
	private OMSServer oms;
	/** List of hostnames matched to kernel server stubs */
	private Hashtable<InetSocketAddress,KernelServer> servers;
	private Logger logger = Logger.getLogger(KernelClient.class.getName());
	
	/** 
	 * Add a host to the list of hosts that we've contacted
	 * 
	 * @param hostname
	 */
	private KernelServer addHost(InetSocketAddress host) {
		try {
		    Registry registry = LocateRegistry.getRegistry(host.getHostName(), host.getPort());
		    KernelServer server = (KernelServer) registry.lookup("DiasporaKernelServer");
		    servers.put(host, server);
		    return server;
		} catch (Exception e) {
			logger.severe("Could not find Diaspora server on host: " + e.toString());
		    e.printStackTrace();
		}
		return null;
	}
	
	private KernelServer getServer(InetSocketAddress host) {
		KernelServer server = servers.get(host);
		if (server == null) {
			server = addHost(host);
		}
		return server;
	}
	
	public KernelClient(OMSServer oms) {
		this.oms = oms;
		servers = new Hashtable<InetSocketAddress,KernelServer>();
	}
	
	private Object tryMakeKernelRPC(KernelServer server, KernelRPC rpc) throws KernelObjectNotFoundException, Exception {
		Object ret = null;
		try {
			ret = server.makeKernelRPC(rpc);
		} catch (KernelRPCException e) {
			throw e.getException();
		} catch (KernelObjectMigratingException e) {
			Thread.sleep(100);
			throw new KernelObjectNotFoundException("Kernel object was migrating. Try again later.");
		}
		return ret;
	}
	
	private Object lookupAndTryMakeKernelRPC(KernelObjectStub stub, KernelRPC rpc) throws KernelObjectNotFoundException, Exception {
		InetSocketAddress host, oldHost = stub.$__getHostname();
		
		try {
			host = oms.lookupKernelObject(stub.$__getKernelOID());
		} catch (RemoteException e) {
			throw new KernelObjectNotFoundException("Could not find oms.");
		} catch (KernelObjectNotFoundException e) {
			throw new KernelObjectNotFoundException("This object does not exist!");
		}
		
		if (host.equals(oldHost)) {
			throw new Error("Kernel object should be on the server!");
		}
		
		stub.$__updateHostname(host);
		return tryMakeKernelRPC(getServer(host), rpc);
}
	
	/** 
	 * Make an RPC to the kernel server.
	 * 
	 * @param hostname
	 * @param rpc
	 * @return
	 * @throws RemoteException when kernel server cannot be contacted
	 * @throws KernelObjectNotFoundException when kernel server cannot find object
	 */
	public Object makeKernelRPC(KernelObjectStub stub, KernelRPC rpc) throws KernelObjectNotFoundException, Exception {
		InetSocketAddress host = stub.$__getHostname();
		logger.info("Making RPC to " + host.toString() + " RPC: " + rpc.toString());

		// Check whether this object is local.
		KernelServer server;
		if (host.equals(GlobalKernelReferences.nodeServer.getLocalHost())) {
			server = GlobalKernelReferences.nodeServer;
		} else {
			server = getServer(host);
		}
		
		// Call the server
		try {
			return tryMakeKernelRPC(server, rpc);
		} catch (KernelObjectNotFoundException e) {
			return lookupAndTryMakeKernelRPC(stub, rpc);
		}
	}
		
	public void copyObjectToServer(InetSocketAddress host, KernelOID oid, KernelObject object) throws RemoteException, KernelObjectNotFoundException {
		getServer(host).copyKernelObject(oid, object);
	}
}
