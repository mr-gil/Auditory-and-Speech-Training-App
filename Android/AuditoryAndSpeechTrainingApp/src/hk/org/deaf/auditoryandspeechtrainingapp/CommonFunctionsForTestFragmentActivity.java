package hk.org.deaf.auditoryandspeechtrainingapp;

import android.os.Bundle;

public class CommonFunctionsForTestFragmentActivity extends FullScreenFragmentActivity {
//	private static final int DELAY = 600000;
//    private int defTimeOut = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setScreenTimeout();
		
	}
	
	private void setScreenTimeout(){
        // Note: OGCIO doesn't want us to modify system settings
//	    defTimeOut = Settings.System.getInt(getContentResolver(), 
//                Settings.System.SCREEN_OFF_TIMEOUT, DELAY);
//		Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, DELAY);
	}
	
	@Override
    protected void onDestroy() 
    {
        super.onDestroy();

        // Note: OGCIO doesn't want us to modify system settings
//        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, defTimeOut);
    }
}
