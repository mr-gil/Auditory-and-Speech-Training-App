package hk.org.deaf.auditoryandspeechtrainingapp.result;

import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class L2ResultMenuFragementActivity extends
		FullScreenFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.result_level_two_menu);
		setContentLayout();
		
	}

	private void setContentLayout() {
		// xmlLayoutConnection(R.drawable.table_result,
		// R.layout.result_page_level1n2_menu, R.drawable.by_menu);

		ImageButton backBtn = (ImageButton) findViewById(R.id.result_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());
		
		// result 2 btn
		ImageButton result2PhBtn = (ImageButton) findViewById(R.id.level2a_page_ph_sel_item);
		result2PhBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result2ThBtn = (ImageButton) findViewById(R.id.level2a_page_th_sel_item);
		result2ThBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result2KhBtn = (ImageButton) findViewById(R.id.level2a_page_kh_sel_item);
		result2KhBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result2SBtn = (ImageButton) findViewById(R.id.level2a_page_s_sel_item);
		result2SBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result2TshBtn = (ImageButton) findViewById(R.id.level2a_page_tsh_sel_item);
		result2TshBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result2NgBtn = (ImageButton) findViewById(R.id.level2a_page_ng_sel_item);
		result2NgBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton result2NBtn = (ImageButton) findViewById(R.id.level2a_page_n_sel_item);
		result2NBtn.setOnClickListener(new SelectionBtnListener());

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
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent0.putExtra("conson", 0);
				startActivity(intent0);
				break;
			case R.id.level1a_page_ph_vs_th_sel_item:
				Intent intent1 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent1.putExtra("conson", 1);
				startActivity(intent1);
				break;
			case R.id.level1a_page_th_vs_t_sel_item:
				Intent intent2 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent2.putExtra("conson", 2);
				startActivity(intent2);
				break;
			case R.id.level1a_page_th_vs_kh_sel_item:
				Intent intent3 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent3.putExtra("conson", 3);
				startActivity(intent3);
				break;
			case R.id.level1a_page_kh_vs_k_sel_item:
				Intent intent4 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent4.putExtra("conson", 4);
				startActivity(intent4);
				break;
			case R.id.level1a_page_s_vs_ts_sel_item:
				Intent intent5 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent5.putExtra("conson", 5);
				startActivity(intent5);
				break;
			case R.id.level1a_page_tsh_vs_ts_sel_item:
				Intent intent6 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent6.putExtra("conson", 6);
				startActivity(intent6);
				break;
			case R.id.level1a_page_n_vs_m_sel_item:
				Intent intent7 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent7.putExtra("conson", 7);
				startActivity(intent7);
				break;
			case R.id.level1a_page_ng_vs_n_sel_item:
				Intent intent8 = new Intent(
						L2ResultMenuFragementActivity.this,
						L1ResultFragementActivity.class);
				intent8.putExtra("conson", 8);
				startActivity(intent8);
				break;

			// L2result
			case R.id.level2a_page_ph_sel_item:
				Intent intent20 = new Intent(
						L2ResultMenuFragementActivity.this,
						L2ResultFragementActivity.class);
				intent20.putExtra("conson", 0);
				startActivity(intent20);
				break;
			case R.id.level2a_page_th_sel_item:
				Intent intent21 = new Intent(
						L2ResultMenuFragementActivity.this,
						L2ResultFragementActivity.class);
				intent21.putExtra("conson", 1);
				startActivity(intent21);
				break;
			case R.id.level2a_page_kh_sel_item:
				Intent intent22 = new Intent(
						L2ResultMenuFragementActivity.this,
						L2ResultFragementActivity.class);
				intent22.putExtra("conson", 2);
				startActivity(intent22);
				break;
			case R.id.level2a_page_s_sel_item:
				Intent intent23 = new Intent(
						L2ResultMenuFragementActivity.this,
						L2ResultFragementActivity.class);
				intent23.putExtra("conson", 3);
				startActivity(intent23);
				break;
			case R.id.level2a_page_tsh_sel_item:
				Intent intent24 = new Intent(
						L2ResultMenuFragementActivity.this,
						L2ResultFragementActivity.class);
				intent24.putExtra("conson", 4);
				startActivity(intent24);
				break;
			case R.id.level2a_page_ng_sel_item:
				Intent intent25 = new Intent(
						L2ResultMenuFragementActivity.this,
						L2ResultFragementActivity.class);
				intent25.putExtra("conson", 5);
				startActivity(intent25);
				break;
			case R.id.level2a_page_n_sel_item:
				Intent intent26 = new Intent(
						L2ResultMenuFragementActivity.this,
						L2ResultFragementActivity.class);
				intent26.putExtra("conson", 6);
				startActivity(intent26);
				break;

			}
		}

	}

	

	

}
