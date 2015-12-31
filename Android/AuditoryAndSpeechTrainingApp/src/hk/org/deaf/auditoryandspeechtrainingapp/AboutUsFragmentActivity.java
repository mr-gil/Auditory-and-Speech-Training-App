package hk.org.deaf.auditoryandspeechtrainingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutUsFragmentActivity extends FullScreenFragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setToFullScreen();
		setContentView(R.layout.about_us_page);
		setContentLayout();
		setupScrollView();
	}
	
	private void setContentLayout(){
		ImageButton backPageBtn = (ImageButton) findViewById(R.id.main_page_back_btn);
		backPageBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private void setupScrollView() {

		TextView tv = (TextView) findViewById(R.id.about_us_page_content_txt);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/DFT_WW7.TTF");
		tv.setTypeface(face);
		tv.setText(Html.fromHtml(getString(R.string.about_us_txt)));
		
		
	}
}
