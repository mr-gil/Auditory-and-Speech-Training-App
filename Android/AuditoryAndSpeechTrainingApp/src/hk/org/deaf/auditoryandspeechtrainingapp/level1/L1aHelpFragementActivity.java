package hk.org.deaf.auditoryandspeechtrainingapp.level1;

import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class L1aHelpFragementActivity extends FullScreenFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level1a_help_revised);
		setContentLayout();
		setupScrollView();
	}

	private void setContentLayout() {
		ImageButton backBtn = (ImageButton) findViewById(R.id.main_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton nextBtn = (ImageButton) findViewById(R.id.main_page_next_btn);
		nextBtn.setOnClickListener(new SelectionBtnListener());
	}

	private void setupScrollView(){
		
		TextView tv = (TextView) findViewById(R.id.test_level_one_help_content_txt);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/DFT_WW7.TTF");
		tv.setTypeface(face);
		
	}
	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.main_page_back_btn) {
				finish();
			} else if (v.getId() == R.id.main_page_next_btn) {
				Intent intent = new Intent(L1aHelpFragementActivity.this,
						L1aMenuFragementActivity.class);
				startActivity(intent);
			}
		}

	}

	

	

}
