package diaspora.kernel.common;

import diaspora.kernel.client.KernelClient;
import diaspora.kernel.server.KernelServer;
import diaspora.kernel.server.KernelServerImpl;

/**
 * Variables visible in the entire address space. They are set at
 * the node (nodeServer) creation:
 * 
 * nodeServer - reference to the Server of the node
 * nodeClient - reference to the Client of the node
 * 
 * The stubs use them as its easier and more transparent than passing
 * them as parameters.
 * 
 * @author aaasz
 *
 */

public class GlobalKernelReferences {
	public static KernelServerImpl nodeServer;
}