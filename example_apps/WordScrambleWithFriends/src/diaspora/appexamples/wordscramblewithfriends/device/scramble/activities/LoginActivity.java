package diaspora.appexamples.wordscramblewithfriends.device.scramble.activities;

import static diaspora.runtime.Diaspora.new_;
import diaspora.app.AndroidDiasporaActivity;
import diaspora.appexamples.wordscramblewithfriends.app.NotificationService;
import diaspora.appexamples.wordscramblewithfriends.app.ScrambleManager;
import diaspora.appexamples.wordscramblewithfriends.app.TableManager;
import diaspora.appexamples.wordscramblewithfriends.app.User;
import diaspora.appexamples.wordscramblewithfriends.app.UserManager;
import diaspora.appexamples.wordscramblewithfriends.device.scramble.R;
import diaspora.appexamples.wordscramblewithfriends.device.scramble.global.StateSingleton;
import diaspora.appexamples.wordscramblewithfriends.device.scramble.util.ScrambleUtil;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AndroidDiasporaActivity implements OnClickListener {
	
	private EditText textUsername;
	private Button buttonLogin;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.login);
		
		textUsername = (EditText) this.findViewById(R.id.input_username);
		buttonLogin = (Button) this.findViewById(R.id.login_button);
		buttonLogin.setEnabled(true);	
		buttonLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
			String username = textUsername.getText().toString();
			if (!username.equals("")) {
				buttonLogin.setEnabled(false);
				new GetUserObjectTask().execute(username);
			}
		} else {
			ScrambleUtil.createToast(getApplicationContext(), getString(R.string.no_connection)).show();
		}
	}
	
	private void startPlayerOptions(Context context) {
		Intent i = new Intent(context, PlayerOptionsActivity.class);
		startActivity(i);
		finish();
	}
	
	private void restartLogin() {
		ScrambleUtil.createToast(getApplicationContext(), getString(R.string.invalid_login)).show();
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
	
	private class GetUserObjectTask extends AsyncTask<String,Void,String> {

		@Override
		protected String doInBackground(String... args) {
			ScrambleManager sm = (ScrambleManager) getAppEntryPoint();
			UserManager um = sm.getUserManager();
			User user = um.getUser(args[0]);
			
			if (user == null)
				return null;
			
			TableManager tm = sm.getTableManager();

			StateSingleton.getInstance().setScrambleManager(sm);
			StateSingleton.getInstance().setTableManager(tm);
			StateSingleton.getInstance().setUserManager(um);
			StateSingleton.getInstance().setUser(user);
			
			// Set up user's notification service
			NotificationService ns = (NotificationService) new_(NotificationService.class);
			StateSingleton.getInstance().setNotificationService(ns);
			StateSingleton.getInstance().getUser().setNotificationService(ns);
			
			return user.getUserInfo().getUsername();
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				startPlayerOptions(getApplicationContext());
			} else {
				restartLogin();
			}
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();

		// null the onclicklistener of the button
		if(buttonLogin != null){
			buttonLogin.setOnClickListener(null);
		}
	}
}
