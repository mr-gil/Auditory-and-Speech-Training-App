package hk.org.deaf.auditoryandspeechtrainingapp;

import hk.com.playmore.syncframework.util.SyncFragmentActivity;
import android.view.Window;
import android.view.WindowManager;

public class FullScreenFragmentActivity extends SyncFragmentActivity {

	protected void setToFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow()
				.setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	
}
