package diaspora.appexamples.todolist.cloud;

import diaspora.app.AppEntryPoint;
import diaspora.app.AppObjectNotCreatedException;
import diaspora.appexamples.todolist.app.TodoListManager;
import diaspora.runtime.Diaspora;
import diaspora.common.AppObjectStub;

public class TodoStart implements AppEntryPoint {

	@Override
	public AppObjectStub start() throws AppObjectNotCreatedException {
			return (AppObjectStub) Diaspora.new_(TodoListManager.class);
	}
}
