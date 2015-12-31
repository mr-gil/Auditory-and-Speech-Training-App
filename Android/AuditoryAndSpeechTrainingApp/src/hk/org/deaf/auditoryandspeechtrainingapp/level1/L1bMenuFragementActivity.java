package hk.org.deaf.auditoryandspeechtrainingapp.level1;

import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class L1bMenuFragementActivity extends FullScreenFragmentActivity {
	private View currentSelectedBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level1b_menu_revised);
		setContentLayout();
		setSelectionBtnLayout();
	}

	private void setContentLayout() {
		// xmlLayoutConnection(R.drawable.box_menu, R.layout.level1b_menu,
		// R.drawable.by_menu);

		ImageButton backBtn = (ImageButton) findViewById(R.id.main_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton startBtn = (ImageButton) findViewById(R.id.menu_start_btn);
		startBtn.setOnClickListener(new SelectionBtnListener());
	}

	private void setSelectionBtnLayout() {
		ImageButton phVspSelItem = (ImageButton) findViewById(R.id.level1a_page_ph_vs_p_sel_item);
		phVspSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton khVskSelItem = (ImageButton) findViewById(R.id.level1a_page_kh_vs_k_sel_item);
		khVskSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton phVsthSelItem = (ImageButton) findViewById(R.id.level1a_page_ph_vs_th_sel_item);
		phVsthSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton sVstsSelItem = (ImageButton) findViewById(R.id.level1a_page_s_vs_ts_sel_item);
		sVstsSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton thVstSelItem = (ImageButton) findViewById(R.id.level1a_page_th_vs_t_sel_item);
		thVstSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton tshVstsSelItem = (ImageButton) findViewById(R.id.level1a_page_tsh_vs_ts_sel_item);
		tshVstsSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton thVskhSelItem = (ImageButton) findViewById(R.id.level1a_page_th_vs_kh_sel_item);
		thVskhSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton nVsmSelItem = (ImageButton) findViewById(R.id.level1a_page_n_vs_m_sel_item);
		nVsmSelItem.setOnClickListener(new SelectionBtnListener());
		ImageButton ngVsnSelItem = (ImageButton) findViewById(R.id.level1a_page_ng_vs_n_sel_item);
		ngVsnSelItem.setOnClickListener(new SelectionBtnListener());
	}

	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.main_page_back_btn) {
				finish();
			} else if (v.getId() == R.id.menu_start_btn) {
				Intent intent = new Intent(L1bMenuFragementActivity.this,
						L1bTestFragementActivity.class);
				if (currentSelectedBtn != null) {
					switch (currentSelectedBtn.getId()) {
					case R.id.level1a_page_ph_vs_p_sel_item:
						intent.putExtra("conson", 0);
						break;
					case R.id.level1a_page_kh_vs_k_sel_item:
						intent.putExtra("conson", 4);
						break;
					case R.id.level1a_page_ph_vs_th_sel_item:
						intent.putExtra("conson", 1);
						break;
					case R.id.level1a_page_s_vs_ts_sel_item:
						intent.putExtra("conson", 5);
						break;
					case R.id.level1a_page_th_vs_t_sel_item:
						intent.putExtra("conson", 2);
						break;
					case R.id.level1a_page_tsh_vs_ts_sel_item:
						intent.putExtra("conson", 6);
						break;
					case R.id.level1a_page_th_vs_kh_sel_item:
						intent.putExtra("conson", 3);
						break;
					case R.id.level1a_page_n_vs_m_sel_item:
						intent.putExtra("conson", 7);
						break;
					case R.id.level1a_page_ng_vs_n_sel_item:
						intent.putExtra("conson", 8);
						break;
					case R.id.level2a_page_s_sel_item:
						intent.putExtra("conson", 9);
						break;
					}

					startActivity(intent);
				}

			} else {

				v.setSelected(true);
				if (currentSelectedBtn != null
						&& currentSelectedBtn.getId() != v.getId()) {
					currentSelectedBtn.setSelected(false);
				}
				currentSelectedBtn = v;

			}
		}

	}

	

}
