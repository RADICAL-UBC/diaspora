package diaspora.appexamples.wordscramblewithfriends.cloud;

import static diaspora.runtime.Diaspora.new_;
import diaspora.app.AppEntryPoint;
import diaspora.app.AppObjectNotCreatedException;
import diaspora.appexamples.wordscramblewithfriends.app.ScrambleManager;
import diaspora.common.AppObjectStub;

public class WordScrambleWithFriendsStart implements AppEntryPoint {

	@Override
	public AppObjectStub start() throws AppObjectNotCreatedException {
		return (AppObjectStub) new_(ScrambleManager.class);
	}
}
