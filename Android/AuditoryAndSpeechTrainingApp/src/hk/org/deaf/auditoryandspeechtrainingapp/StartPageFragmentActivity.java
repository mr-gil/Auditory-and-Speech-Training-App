package hk.org.deaf.auditoryandspeechtrainingapp;

import hk.org.deaf.auditoryandspeechtrainingapp.utils.PlaySoundForPassOrFail;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class StartPageFragmentActivity extends FullScreenFragmentActivity {
	PlaySoundForPassOrFail playSoundForPassOrFail;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setToFullScreen();
		setContentView(R.layout.start_page_revised);
		setContentLayout();		
	}
	
	private void setContentLayout(){
		playSoundForPassOrFail = new PlaySoundForPassOrFail(this);
		playSoundForPassOrFail.loadOpenningSoundFile();
		playSoundForPassOrFail.playOpeningSound();
		
		ImageButton startBtn = (ImageButton) findViewById(R.id.cover_page_start_btn);
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playSoundForPassOrFail.stopOpeningSound();
				Intent intent = new Intent(StartPageFragmentActivity.this, ParentalGuideFragmentActivity.class);
				startActivity(intent);
			}
		});
		
		

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		playSoundForPassOrFail.stopOpeningSound();
	}
}
