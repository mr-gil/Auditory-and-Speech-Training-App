package hk.org.deaf.auditoryandspeechtrainingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class L1andL2ResultCommonFunction extends FullScreenFragmentActivity {
	protected int conson;
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
	
	protected void getConsonFromIntent() {
		int conson = getIntent().getIntExtra("conson", 0);
		Log.d("conson", String.valueOf(conson));
		setConson(conson);
	}
	
	protected int getConson() {
		return conson;
	}

	protected void setConson(int conson) {
		this.conson = conson;
	}
}
