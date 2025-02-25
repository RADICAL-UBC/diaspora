package diaspora.appexamples.wordscramblewithfriends.app;

import java.util.LinkedList;
import java.util.Queue;

import diaspora.app.DiasporaObject;
import diaspora.appexamples.wordscramblewithfriends.device.scramble.global.ScrambleConstants;
import diaspora.appexamples.wordscramblewithfriends.device.scramble.util.ScrambleMessage;

public class NotificationService implements DiasporaObject {
	private final Queue<ScrambleMessage> messageQueue = new LinkedList<ScrambleMessage>();
	
	public NotificationService() {
		super();
	}

	public void notify(ScrambleMessage message) {
		if (message == null) {
			throw new IllegalArgumentException("Message cannot be null");
		}
		messageQueue.add(message);
	}
	
	public ScrambleMessage pollMessage() {
		return messageQueue.poll();
	}
	
	public boolean noMessages() {
		return messageQueue.isEmpty();
	}
	
	public void clearMessages() {
		messageQueue.clear();
	}
	
	public int getNumMessages() {
		return messageQueue.size();
	}
}

