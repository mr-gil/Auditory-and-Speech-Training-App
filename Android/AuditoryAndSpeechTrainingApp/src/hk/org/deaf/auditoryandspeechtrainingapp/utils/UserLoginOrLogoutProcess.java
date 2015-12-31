package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.org.deaf.auditoryandspeechtrainingapp.interfaces.ShowUserLayout;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserLoginOrLogoutProcess {
	private boolean userLoggedIn = false;
	private ShowUserLayout showUserLayout;
	private String username, userEmail;
	private int userId;
	private Activity act;
	
	public UserLoginOrLogoutProcess(Activity act, ShowUserLayout sul){
		this.showUserLayout = sul;
		this.act = act;
	}
	
	public boolean checkWhetherUserLoggedIn(){
		
		getUsername();
		if (username != null){
			getUserEmail();
			if (userEmail != null){
				userLoggedIn = true;
			}else{
				userLoggedIn = false;
			}
		}else{
			userLoggedIn = false;
		}
		return userLoggedIn;
	}
	
	public String getUsername() {
		if(username == null){
			SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(act.getApplicationContext()); 
			username = prefs.getString("username", null);
		}
		return username;
	}

	public String getUserEmail() {
		if (userEmail == null){
			SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(act.getApplicationContext());
			userEmail = prefs.getString("user_email", null);
		}
		return userEmail;
	}

	public int getUserId(){
		if (userId == 0){
			SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(act.getApplicationContext());
			userId = prefs.getInt("user_id", 0);
		}
		return userId;
	}
	public void inputUserInfo(int userId, String username, String email){
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(act.getApplicationContext()).edit();
		editor.putInt("user_id", userId);
		 editor.putString("username", username);
		 editor.putString("user_email", email);
		 editor.commit();
	}
	
	public boolean isUserLoggedIn() {
		return checkWhetherUserLoggedIn();
	}
	
	public void showUserPage(){
		if (isUserLoggedIn()){
			if (showUserLayout != null) this.showUserLayout.displayUserLoggedInUis();
		}else{
			if (showUserLayout != null) this.showUserLayout.displayUserNotYetLoggedInUis();
		}
	}
	
	// = logout
	public void removeUserRecords(){
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(act.getApplicationContext()).edit();
		editor.putInt("user_id", 0);
		 editor.putString("username", null);
		 editor.putString("user_email", null);
		 editor.commit();
		 username = null;
		 userEmail = null;
				 
	}
}

