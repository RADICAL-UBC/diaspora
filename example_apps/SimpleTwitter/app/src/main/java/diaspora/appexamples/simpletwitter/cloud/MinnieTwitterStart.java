package diaspora.appexamples.simpletwitter.cloud;

import diaspora.app.AppEntryPoint;
import diaspora.app.AppObjectNotCreatedException;
import static diaspora.runtime.Diaspora.*;
import diaspora.appexamples.simpletwitter.app.TwitterManager;
import diaspora.common.AppObjectStub;

public class SimpleTwitterStart implements AppEntryPoint {

	@Override
	public AppObjectStub start() throws AppObjectNotCreatedException {
			return (AppObjectStub) new_(TwitterManager.class);
	}
}
