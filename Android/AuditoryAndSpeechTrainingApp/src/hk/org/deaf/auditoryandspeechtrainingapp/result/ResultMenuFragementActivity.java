package hk.org.deaf.auditoryandspeechtrainingapp.result;

import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.MainFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.R.drawable;
import hk.org.deaf.auditoryandspeechtrainingapp.R.id;
import hk.org.deaf.auditoryandspeechtrainingapp.R.layout;
import hk.org.deaf.auditoryandspeechtrainingapp.R.menu;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ResultMenuFragementActivity extends FullScreenFragmentActivity {
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.result_menu_revised);
        setContentLayout();
    }

    private void setContentLayout(){
    	ImageButton level1ResultBtn = (ImageButton) findViewById(R.id.result_page_level_1_btn);
		level1ResultBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton level2ResultBtn = (ImageButton) findViewById(R.id.result_page_level_2_btn);
		level2ResultBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton level3ResultBtn = (ImageButton) findViewById(R.id.result_page_level_3_btn);
		level3ResultBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton homeBtn = (ImageButton) findViewById(R.id.result_page_home_btn);
		homeBtn.setOnClickListener(new SelectionBtnListener());
		
		
		
	}
    
    class SelectionBtnListener implements OnClickListener {
    	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
				case R.id.result_page_home_btn:
					Intent intentToHome = new Intent(ResultMenuFragementActivity.this, MainFragmentActivity.class);
					intentToHome.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(intentToHome);
					finish();
					break;
				case R.id.result_page_level_3_btn:
					Intent intentTolevel3Result = new Intent(ResultMenuFragementActivity.this, L3ResultFragementActivity.class);
					startActivity(intentTolevel3Result);
					break;
				case R.id.result_page_level_1_btn:
					Intent intentTolevel1ResultMenu = new Intent(ResultMenuFragementActivity.this, L1ResultMenuFragementActivity.class);
					startActivity(intentTolevel1ResultMenu);
					break;
				case R.id.result_page_level_2_btn:
					Intent intentTolevel2ResultMenu = new Intent(ResultMenuFragementActivity.this, L2ResultMenuFragementActivity.class);
					startActivity(intentTolevel2ResultMenu);
					break;
				
			}
		}
    	
    }
    
    
}
