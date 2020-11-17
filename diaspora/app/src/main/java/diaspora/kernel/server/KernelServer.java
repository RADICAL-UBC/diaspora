package diaspora.kernel.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import diaspora.common.AppObjectStub;
import diaspora.kernel.common.KernelOID;
import diaspora.kernel.common.KernelObjectMigratingException;
import diaspora.kernel.common.KernelObjectNotFoundException;
import diaspora.kernel.common.KernelRPC;
import diaspora.kernel.common.KernelRPCException;

/** 
 * Interface for the Diaspora Kernel Server
 * 
 * @author iyzhang
 *
 */
public interface KernelServer extends Remote {
	Object makeKernelRPC(KernelRPC rpc) throws RemoteException, KernelObjectNotFoundException, KernelObjectMigratingException, KernelRPCException;
	void copyKernelObject(KernelOID oid, KernelObject object) throws RemoteException, KernelObjectNotFoundException;

	AppObjectStub startApp(String className) throws RemoteException;
}
