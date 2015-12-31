package hk.org.deaf.auditoryandspeechtrainingapp.level2;

import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class L2aMenuFragementActivity extends FullScreenFragmentActivity {
	private View currentSelectedBtn;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.level2a_menu_revised);
        setContentLayout();
        setSelectionBtnLayout();
    }

    private void setContentLayout(){
    	//xmlLayoutConnection(R.drawable.box_menu, R.layout.level2a_menu, R.drawable.by_menu);
		
		ImageButton backBtn = (ImageButton) findViewById(R.id.main_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton startBtn = (ImageButton) findViewById(R.id.menu_start_btn);
		startBtn.setOnClickListener(new SelectionBtnListener());
	}
    
    private void setSelectionBtnLayout(){
    	ImageButton phSelItem = (ImageButton) findViewById(R.id.level2a_page_ph_sel_item);
    	phSelItem.setOnClickListener(new SelectionBtnListener());
    	ImageButton tshSelItem = (ImageButton) findViewById(R.id.level2a_page_tsh_sel_item);
    	tshSelItem.setOnClickListener(new SelectionBtnListener());
    	ImageButton thSelItem = (ImageButton) findViewById(R.id.level2a_page_th_sel_item);
    	thSelItem.setOnClickListener(new SelectionBtnListener());
    	ImageButton ngSelItem = (ImageButton) findViewById(R.id.level2a_page_ng_sel_item);
    	ngSelItem.setOnClickListener(new SelectionBtnListener());
    	ImageButton khSelItem = (ImageButton) findViewById(R.id.level2a_page_kh_sel_item);
    	khSelItem.setOnClickListener(new SelectionBtnListener());
    	ImageButton nSelItem = (ImageButton) findViewById(R.id.level2a_page_n_sel_item);
    	nSelItem.setOnClickListener(new SelectionBtnListener());
    	ImageButton sSelItem = (ImageButton) findViewById(R.id.level2a_page_s_sel_item);
    	sSelItem.setOnClickListener(new SelectionBtnListener());
    	
    }
    class SelectionBtnListener implements OnClickListener {
    	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.main_page_back_btn){
				finish();
			}else if (v.getId() == R.id.menu_start_btn){
				Intent intent = new Intent(L2aMenuFragementActivity.this, L2aTestFragementActivity.class);
				if (currentSelectedBtn != null) {
					switch(currentSelectedBtn.getId()){
						case R.id.level2a_page_ph_sel_item:
							intent.putExtra("conson", 0);
							break;
						case R.id.level2a_page_tsh_sel_item:
							intent.putExtra("conson", 4);
							break;
						case R.id.level2a_page_th_sel_item:
							intent.putExtra("conson", 1);
							break;
						case R.id.level2a_page_ng_sel_item:
							intent.putExtra("conson", 5);
							break;
						case R.id.level2a_page_kh_sel_item:
							intent.putExtra("conson", 2);
							break;
						case R.id.level2a_page_n_sel_item:
							intent.putExtra("conson", 6);
							break;
						case R.id.level2a_page_s_sel_item:
							intent.putExtra("conson", 3);
							break;
					}
					
					startActivity(intent);
				}
				
			}else {
			
				if (v.isSelected()) {
					//v.setSelected(false);
				} else {
					v.setSelected(true);
					if (currentSelectedBtn != null) {
						currentSelectedBtn.setSelected(false);
					}
					currentSelectedBtn = v;
				}
			}
		}
    	
    }
    
    
    
    
}
