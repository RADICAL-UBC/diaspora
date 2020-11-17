package diaspora.appexamples.wordscramblewithfriends.app;

import static diaspora.runtime.Diaspora.new_;
import diaspora.app.DiasporaObject;


public class ScrambleManager implements DiasporaObject {
	private UserManager  userManager;
	private TableManager tableManager;
	
	public ScrambleManager() {
		userManager  = (UserManager) new_(UserManager.class);
		tableManager = (TableManager) new_(TableManager.class, userManager);
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	public TableManager getTableManager() {
		return tableManager;
	}
}
