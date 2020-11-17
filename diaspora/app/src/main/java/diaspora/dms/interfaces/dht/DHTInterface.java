package diaspora.dms.interfaces.dht;

import java.util.Map;


/**
 * Any Diaspora Object that wants to use the DHTPolicy must implement this interface.
 * @author aaasz
 *
 */

public interface DHTInterface {
	/**
	 * Returns the Map like structure used to index the data
	 * 
	 * @return
	 */
	public Map<DHTKey, ?> dhtGetData();
}
