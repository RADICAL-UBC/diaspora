package diaspora.appexamples.wordscramblewithfriends.app;

import diaspora.app.DiasporaObject;

public class User implements DiasporaObject {
	private UserInfo userInfo;
	//private Table table;	/* If hosting a table */
	private Game game;		/* If playing a game */
	private NotificationService ns;
	
	public User(UserInfo userInfo) {
		this.userInfo = userInfo;
		//this.table = null;
		this.game = null;
		this.ns = null;
	}
	
	public void setNotificationService(NotificationService ns) {
		if (ns == null) {
			throw new IllegalArgumentException();
		}
		this.ns = ns;
	}
	
	public NotificationService getNotificationService() {
		return ns;
	}
	
//	public boolean setTable(Table t) {
//		if (hasTable() || isPlayingGame()) {
//			return false;
//		}
//		table = t;
//		return true;
//	}
//	
//	public boolean setGame(Game g) {
//		if (!hasTable() || isPlayingGame())
//			return false;
//		game = g;
//		table = null;
//		return true;
//	}
//	
//	public void reset() {
//		game = null;
//		table = null;
//	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
//	public boolean hasTable() {
//		return table != null;
//	}
	
	public boolean isPlayingGame() {
		return game != null;
	}
	
	public Game getGame() {
		return game;
	}
	
//	@Override
//	public String toString() {
//		return "@" + userInfo.getUsername() + "\n\ttable is null: " +
//				(table == null) + "\n\tgame is null: " + (game == null);
//	}
}
