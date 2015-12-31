package hk.org.deaf.auditoryandspeechtrainingapp.result;

import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class L1ResultMenuFragementActivity extends
		FullScreenFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.result_level_one_menu);
		setContentLayout();
		
	}

	private void setContentLayout() {
		// xmlLayoutConnection(R.drawable.table_result,
		// R.layout.result_page_level1n2_menu, R.drawable.by_menu);

		ImageButton backBtn = (ImageButton) findViewById(R.id.result_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());
		
		ImageButton result1PhVsPBtn = (ImageButton) findViewById(R.id.level1a_page_ph_vs_p_sel_item);
		result1PhVsPBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result1PhVsThBtn = (ImageButton) findViewById(R.id.level1a_page_ph_vs_th_sel_item);
		result1PhVsThBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result1ThVsTBtn = (ImageButton) findViewById(R.id.level1a_page_th_vs_t_sel_item);
		result1ThVsTBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result1ThVsKhBtn = (ImageButton) findViewById(R.id.level1a_page_th_vs_kh_sel_item);
		result1ThVsKhBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result1KhVsKBtn = (ImageButton) findViewById(R.id.level1a_page_kh_vs_k_sel_item);
		result1KhVsKBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result1SVsTsBtn = (ImageButton) findViewById(R.id.level1a_page_s_vs_ts_sel_item);
		result1SVsTsBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton resultTshVsTsBtn = (ImageButton) findViewById(R.id.level1a_page_tsh_vs_ts_sel_item);
		resultTshVsTsBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result1NVsMBtn = (ImageButton) findViewById(R.id.level1a_page_n_vs_m_sel_item);
		result1NVsMBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result1NgVsNBtn = (ImageButton) findViewById(R.id.level1a_page_ng_vs_n_sel_item);
		result1NgVsNBtn.setOnClickListener(new SelectionBtnListener());
		
	}

	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.result_page_back_btn:
				finish();
				break;
			case R.id.level1a_page_ph_vs_p_sel_item:
				Intent intent0 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent0.putExtra("conson", 0);
				startActivity(intent0);
				break;
			case R.id.level1a_page_ph_vs_th_sel_item:
				Intent intent1 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent1.putExtra("conson", 1);
				startActivity(intent1);
				break;
			case R.id.level1a_page_th_vs_t_sel_item:
				Intent intent2 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent2.putExtra("conson", 2);
				startActivity(intent2);
				break;
			case R.id.level1a_page_th_vs_kh_sel_item:
				Intent intent3 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent3.putExtra("conson", 3);
				startActivity(intent3);
				break;
			case R.id.level1a_page_kh_vs_k_sel_item:
				Intent intent4 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent4.putExtra("conson", 4);
				startActivity(intent4);
				break;
			case R.id.level1a_page_s_vs_ts_sel_item:
				Intent intent5 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent5.putExtra("conson", 5);
				startActivity(intent5);
				break;
			case R.id.level1a_page_tsh_vs_ts_sel_item:
				Intent intent6 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent6.putExtra("conson", 6);
				startActivity(intent6);
				break;
			case R.id.level1a_page_n_vs_m_sel_item:
				Intent intent7 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent7.putExtra("conson", 7);
				startActivity(intent7);
				break;
			case R.id.level1a_page_ng_vs_n_sel_item:
				Intent intent8 = new Intent(
						L1ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent8.putExtra("conson", 8);
				startActivity(intent8);
				break;

			

			}
		}

	}

	
	
}
