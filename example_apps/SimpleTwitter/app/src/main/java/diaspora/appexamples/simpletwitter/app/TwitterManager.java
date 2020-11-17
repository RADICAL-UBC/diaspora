package diaspora.appexamples.simpletwitter.app;

import diaspora.app.DiasporaObject;
import static diaspora.runtime.Diaspora.*;

public class TwitterManager implements DiasporaObject {
	private UserManager userManager;
	private TagManager tagManager;
	
	public TwitterManager() {
		tagManager = (TagManager) new_(TagManager.class);
		userManager = (UserManager) new_(UserManager.class, tagManager);
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public TagManager getTagManager() {
		return tagManager;
	}	
}
