package diaspora.appexamples.todolist.app;

import java.io.Serializable;
import java.util.ArrayList;

import diaspora.app.*;
import diaspora.dms.cache.CacheLeasePolicy;

public class TodoList implements DiasporaObject<CacheLeasePolicy>{
	ArrayList<Object> toDos = new ArrayList<Object>();
	String name = "Todo with Diaspora";

	public TodoList(String name) {
		toDos = new ArrayList<Object>();
		this.name = name;
	}

	public String addToDo(String todo) {
		System.out.println("Inside todo: " + todo);
		toDos.add(todo);
		return "OK!";
	}

	public void completeToDo(String todo) {
	}

	public ArrayList<Object> getHighPriority() {
		return new ArrayList<Object>();
	}
}
