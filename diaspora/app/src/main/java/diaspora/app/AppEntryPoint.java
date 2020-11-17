package diaspora.app;

import diaspora.common.AppObjectStub;

public interface AppEntryPoint {
	public AppObjectStub start() throws AppObjectNotCreatedException;
}