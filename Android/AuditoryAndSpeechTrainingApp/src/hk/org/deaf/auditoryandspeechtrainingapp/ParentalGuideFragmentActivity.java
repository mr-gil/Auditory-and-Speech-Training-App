package hk.org.deaf.auditoryandspeechtrainingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ParentalGuideFragmentActivity extends FullScreenFragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setToFullScreen();
		setContentView(R.layout.pg_page_revised);
		setContentLayout();
		setupScrollView();
	}
	
	private void setContentLayout(){
		//xmlLayoutConnection(R.drawable.box_pg, R.layout.pg_content, R.drawable.bg_cover);
		
		ImageButton nextPageBtn = (ImageButton) findViewById(R.id.pg_page_next_btn);
		nextPageBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ParentalGuideFragmentActivity.this, MainFragmentActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void setupScrollView() {

		TextView tv = (TextView) findViewById(R.id.pg_help_content_txt);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/DFT_WW7.TTF");
		tv.setTypeface(face);
		tv.setText(Html.fromHtml(getString(R.string.pg_help_content)));
		
		
	}
}
