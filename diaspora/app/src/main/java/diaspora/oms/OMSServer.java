package diaspora.oms;

import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import diaspora.dms.DiasporaPolicy.DiasporaGroupPolicy;
import diaspora.dms.DiasporaPolicy.DiasporaServerPolicy;
import diaspora.runtime.EventHandler;
import diaspora.common.AppObjectStub;
import diaspora.common.DiasporaObjectID;
import diaspora.common.DiasporaObjectNotFoundException;
import diaspora.common.DiasporaReplicaID;
import diaspora.kernel.common.KernelOID;
import diaspora.kernel.common.KernelObjectNotFoundException;

public interface OMSServer extends Remote {
       KernelOID registerKernelObject(InetSocketAddress host) throws RemoteException;
       void registerKernelObject(KernelOID oid, InetSocketAddress host) throws RemoteException, KernelObjectNotFoundException;
       InetSocketAddress lookupKernelObject(KernelOID oid) throws RemoteException, KernelObjectNotFoundException;
       
       ArrayList<InetSocketAddress> getServers() throws NumberFormatException, RemoteException, NotBoundException;
       ArrayList<String> getRegions() throws RemoteException;
       InetSocketAddress getServerInRegion(String region) throws RemoteException;
       
       void registerKernelServer(InetSocketAddress host) throws RemoteException, NotBoundException;
       
       DiasporaObjectID registerDiasporaObject(EventHandler dispatcher) throws RemoteException;
       DiasporaReplicaID registerDiasporaReplica(DiasporaObjectID oid, EventHandler dispatcher) throws RemoteException, DiasporaObjectNotFoundException;
       EventHandler getDiasporaObjectDispatcher(DiasporaObjectID oid) throws RemoteException, DiasporaObjectNotFoundException;
       EventHandler getDiasporaReplicaDispatcher(DiasporaReplicaID rid) throws RemoteException, DiasporaObjectNotFoundException;
       
       /* Called by the client */
       public AppObjectStub getAppEntryPoint() throws RemoteException;
}

