package diaspora.appexamples.wordscramblewithfriends.device;

import static diaspora.runtime.Diaspora.new_;
import diaspora.app.DiasporaActivity;
import diaspora.app.DiasporaObject;
import diaspora.appexamples.wordscramblewithfriends.app.NotificationService;
import diaspora.appexamples.wordscramblewithfriends.app.ScrambleManager;
import diaspora.appexamples.wordscramblewithfriends.app.TableManager;
import diaspora.appexamples.wordscramblewithfriends.app.User;
import diaspora.appexamples.wordscramblewithfriends.app.UserManager;
import android.app.Activity;

public class ScrambleActivityTwo extends Activity implements DiasporaActivity {
	public void onCreate(DiasporaObject appEntryPoint) {
		ScrambleManager sm = (ScrambleManager) appEntryPoint;
		UserManager um = sm.getUserManager();
		TableManager tm = sm.getTableManager();
		try {
//			User dana = um.addUser("Dana", "Dana");
//			NotificationService ns = (NotificationService) new_(NotificationService.class);
//			dana.setNotificationService(ns);
//			User kie = um.addUser("Kie", "Kie");
//			tm.hostTable("Dana");
//			
//			while (ns.noMessages()) {
//				Thread.sleep(5000);
//			}
//			
//			System.out.println("Dana got the message!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
