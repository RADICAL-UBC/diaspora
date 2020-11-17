package diaspora.appexamples.todolist.app;

import java.util.Hashtable;
import java.util.Map;

import diaspora.app.DiasporaObject;
import static diaspora.runtime.Diaspora.*;
import diaspora.dms.dht.DHTPolicy;
import diaspora.dms.interfaces.dht.DHTInterface;
import diaspora.dms.interfaces.dht.DHTKey;

public class TodoListManager implements DiasporaObject<DHTPolicy>, DHTInterface {
    Map<DHTKey, TodoList> todoLists = new Hashtable<DHTKey, TodoList>();

	public TodoList newTodoList(String name) {
		TodoList t = todoLists.get(new DHTKey(name));
		if (t == null) {
			t = (TodoList) new_(TodoList.class, name);
			todoLists.put(new DHTKey(name), t);
		}
		System.out.println("Created new list");
		System.out.println("This managers lists" + todoLists.toString());
		return t;
	}

	@Override
	public Map<DHTKey, ?> dhtGetData() {
		// TODO Auto-generated method stub
		return todoLists;
	}
}
