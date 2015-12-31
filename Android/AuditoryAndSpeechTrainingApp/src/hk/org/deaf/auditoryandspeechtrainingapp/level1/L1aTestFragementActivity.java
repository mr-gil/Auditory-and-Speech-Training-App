package hk.org.deaf.auditoryandspeechtrainingapp.level1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hk.org.deaf.auditoryandspeechtrainingapp.CommonFunctionsForTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2aTestFragementActivity.PageSelectionHandler;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDataSource;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.AuditoryDirectoryHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ContextStorage;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadTrace;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.GetDataFromLocalDatabase;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PlaySoundForPassOrFail;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ProcessIsFirstData;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.SaveChallengeOneRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService.DownloadProgressUpdateReceiver;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class L1aTestFragementActivity extends
		CommonFunctionsForTestFragmentActivity {
	// ImageButton prevBtn;
	private ImageButton page1Sel;
	private ImageButton page2Sel;
	private ImageButton page3Sel;
	private ImageButton page4Sel;
	private ImageButton page5Sel;
	private ImageButton page6Sel;
	private ImageButton page7Sel;
	private ImageButton page8Sel;
	private ImageButton page9Sel;
	private ImageButton page10Sel;

	private TextView test;
	private TextView testPagePage1Txt;
	private TextView testPagePage2Txt;
	private TextView testPagePage3Txt;
	private TextView testPagePage4Txt;
	private TextView testPagePage5Txt;
	private TextView testPagePage6Txt;
	private TextView testPagePage7Txt;
	private TextView testPagePage8Txt;
	private TextView testPagePage9Txt;
	private TextView testPagePage10Txt;

	private int selectedPage = 0;
	private int noOfPages = 176;
	private int conson;
	private List<StageOneConson> locals;
	private int currentPageNo;
	private PageHandler pageHandler;
	private Bitmap picture1, picture2;
	private SoundPool soundPool1, soundPool2;
	private int soundID1, soundID2;
	private MediaPlayer player1, player2;
	private ProgressDialog dialog;
	private int answer;
	private Button inputAnswerBtn1, inputAnswerBtn2;
	private TextView downloadProgressTxt;
	private Dialog downloadProgressDialog;

	private void setInputAnswerBtn(int whichBtn, Button btn) {
		switch (whichBtn) {
		case 1:
			inputAnswerBtn1 = btn;
			break;
		case 2:
			inputAnswerBtn2 = btn;
			break;
		}
	}

	private Button getInputAnswerBtn(int whichBtn) {
		switch (whichBtn) {
		case 1:
			return inputAnswerBtn1;
		case 2:
			return inputAnswerBtn2;
		}
		return null;
	}

	public List<StageOneConson> getLocals() {
		return locals;
	}

	public void setLocals(List<StageOneConson> locals) {
		this.locals = locals;
	}

	private static boolean firstTimePhVsP = true;
	private static boolean firstTimePhVsTh = true;
	private static boolean firstTimeThVsT = true;
	private static boolean firstTimeThVsKh = true;
	private static boolean firstTimeKhVsK = true;
	private static boolean firstTimeSVsTs = true;
	private static boolean firstTimeTshVsTs = true;
	private static boolean firstTimeNVsM = true;
	private static boolean firstTimeNgVsN = true;

	public static boolean isFirstTime(int conson) {
		switch (conson) {
		case 0:
			return firstTimePhVsP;
		case 1:
			return firstTimePhVsTh;
		case 2:
			return firstTimeThVsT;
		case 3:
			return firstTimeThVsKh;
		case 4:
			return firstTimeKhVsK;
		case 5:
			return firstTimeSVsTs;
		case 6:
			return firstTimeTshVsTs;
		case 7:
			return firstTimeNVsM;
		case 8:
			return firstTimeNgVsN;

		}
		return true;
	}

	public static void setFirstTime(int conson) {
		switch (conson) {
		case 0:
			firstTimePhVsP = false;
			break;
		case 1:
			firstTimePhVsTh = false;
			break;
		case 2:
			firstTimeThVsT = false;
			break;
		case 3:
			firstTimeThVsKh = false;
			break;
		case 4:
			firstTimeKhVsK = false;
			break;
		case 5:
			firstTimeSVsTs = false;
			break;
		case 6:
			firstTimeTshVsTs = false;
			break;
		case 7:
			firstTimeNVsM = false;
			break;
		case 8:
			firstTimeNgVsN = false;
			break;

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level1a_test_revised);
		getConsonFromIntent();
		ContextStorage.getInstance().setContext(getApplicationContext());
		setMenuLayout();
		setUpDownloadProgressDialog();
		setSelectionBtnLayout();
		setContentLayout();
		getIsFirstDataFromDataAndSetup();
		setupDownloadDialog();
		showDownloadDialog();
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	private ImageButton getMenuBtn() {
		return (ImageButton) findViewById(R.id.test1a_page_menu_btn);
	}

	private void setUpDownloadProgressDialog() {
		downloadProgressDialog = new Dialog(this);
		downloadProgressDialog.setContentView(R.layout.test_download_dialog_ui);
		Button cancelBtn = (Button) downloadProgressDialog
				.findViewById(R.id.test_download_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadProgressDialog.dismiss();
				unBindDownloadService();
				finish();

			}
		});

		setDownloadProgressTxt((TextView) downloadProgressDialog
				.findViewById(R.id.test_show_downloads_txt));

	}

	private void showDownloadProgressDialog() {

		downloadProgressDialog.show();

	}

	private void syncData() {
		AppDataSource appDataSource = AppDataSource.getInstance();
		appDataSource.getStageOneConson(this, this, new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// getDataFromLocalDatabaseAndStartTheGame();

			}

		}, String.valueOf(getConson()));

	}

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
	}

	private void getConsonFromIntent() {
		int conson = getIntent().getIntExtra("conson", 0);
		Log.d("consonVsConson", String.valueOf(conson));
		setConson(conson);
	}

	public static String getConsonString(int conson) {
		switch (conson) {
		case 0:
			return "ph";
		case 1:
			return "ph";
		case 2:
			return "th";
		case 3:
			return "th";
		case 4:
			return "kh";
		case 5:
			return "s";
		case 6:
			return "tsh";
		case 7:
			return "n";
		case 8:
			return "ng";
		}
		return null;
	}

	public static int getConsonDrawable(int conson) {
		switch (conson) {
		case 0:
			return R.drawable.level2a_page_ph_sel_item;
		case 1:
			return R.drawable.level2a_page_ph_sel_item;
		case 2:
			return R.drawable.level2a_page_th_sel_item;
		case 3:
			return R.drawable.level2a_page_th_sel_item;
		case 4:
			return R.drawable.level2a_page_kh_sel_item;
		case 5:
			return R.drawable.level2a_page_s_sel_item;
		case 6:
			return R.drawable.level2a_page_tsh_sel_item;
		case 7:
			return R.drawable.level2a_page_n_sel_item;
		case 8:
			return R.drawable.level2a_page_ng_sel_item;
		}
		return 0;
	}

	private void setContentLayout() {
		Display display = getWindowManager().getDefaultDisplay();
		int SCREEN_HEIGHT = display.getHeight();
		// int size = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);
		int size = (int) (SCREEN_HEIGHT * 0.5 / 5.5);
		TextView textShow1 = (TextView) findViewById(R.id.level1_test_text_show1);
		textShow1.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		TextView textShow2 = (TextView) findViewById(R.id.level1_test_text_show2);
		textShow2.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

		int sizeForIndicator = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);
		/*
		 * TextView textIndicator = (TextView)
		 * findViewById(R.id.test_level_1_indicator_txt);
		 * textIndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX,
		 * sizeForIndicator);
		 * textIndicator.setText(getConsonString(getConson()));
		 */
		ImageView imgIndicator = (ImageView) findViewById(R.id.test_level_1_indicator);
		imgIndicator.setImageResource(getConsonDrawable(getConson()));

		ClickBtnListener clickBtnListener = new ClickBtnListener();
		getNextBtn(1).setOnClickListener(clickBtnListener);
		getNextBtn(2).setOnClickListener(clickBtnListener);
		getNormalBtn(1).setOnClickListener(clickBtnListener);
		getNormalBtn(2).setOnClickListener(clickBtnListener);

		ImageButton repeatPlaySoundBtn = (ImageButton) findViewById(R.id.test2a_page_repeat_btn);
		repeatPlaySoundBtn.setOnClickListener(new ClickBtnListener());

		setInputAnswerBtn(1,
				(Button) findViewById(R.id.test1_input_answer_btn_1));
		setInputAnswerBtn(2,
				(Button) findViewById(R.id.test1_input_answer_btn_2));
		getInputAnswerBtn(1).setOnClickListener(clickBtnListener);
		getInputAnswerBtn(2).setOnClickListener(clickBtnListener);
		getMenuBtn().setOnClickListener(clickBtnListener);

	}

	private PlaySoundForPassOrFail playSoundForPass;

	public void playPassSound() {
		playSoundForPass = new PlaySoundForPassOrFail(this);
		playSoundForPass.loadPassSoundFile();
		playSoundForPass.playPassSound();

	}

	private PlaySoundForPassOrFail playSoundForFail;

	public void playFailSound() {
		playSoundForFail = new PlaySoundForPassOrFail(this);
		playSoundForFail.loadFailSoundFile();
		playSoundForFail.playFailSound();
	}

	private void setMenuLayout() {
		// xmlLayoutConnection(R.drawable.box_menu, R.layout.level1a_test,
		// R.drawable.bg_test);
		Display display = getWindowManager().getDefaultDisplay();
		// int SCREEN_WIDTH = display.getWidth();
		int SCREEN_HEIGHT = display.getHeight();

		test = (TextView) findViewById(R.id.test);
		testPagePage1Txt = (TextView) findViewById(R.id.test_page_page1_txt);
		testPagePage2Txt = (TextView) findViewById(R.id.test_page_page2_txt);
		testPagePage3Txt = (TextView) findViewById(R.id.test_page_page3_txt);
		testPagePage4Txt = (TextView) findViewById(R.id.test_page_page4_txt);
		testPagePage5Txt = (TextView) findViewById(R.id.test_page_page5_txt);
		testPagePage6Txt = (TextView) findViewById(R.id.test_page_page6_txt);
		testPagePage7Txt = (TextView) findViewById(R.id.test_page_page7_txt);
		testPagePage8Txt = (TextView) findViewById(R.id.test_page_page8_txt);
		testPagePage9Txt = (TextView) findViewById(R.id.test_page_page9_txt);
		testPagePage10Txt = (TextView) findViewById(R.id.test_page_page10_txt);

		// int size = (int) (SCREEN_HEIGHT * 0.8/13*1/2);
		int size = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 3);
		test.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage1Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage2Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage3Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage4Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage5Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage6Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage7Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage8Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage9Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		testPagePage10Txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

		test.setText(1 + "/" + noOfPages);
		ImageButton backBtn = (ImageButton) findViewById(R.id.test_page_back_btn);
		backBtn.setOnClickListener(new ClickBtnListener());

	}

	void pageHandling(int pageSelect, int noOfPages) {
		int noOfFullPane;
		noOfFullPane = noOfPages / 10;
		int remaining = noOfPages % 10;

		if (selectedPage > 0) {
			int m = selectedPage % 10;
			if (m > 0) {
				pageSel(selectedPage % 10).setSelected(false);
			} else {
				pageSel(10).setSelected(false);
			}
		}
		if (noOfFullPane > 0 && noOfFullPane * 10 >= pageSelect) {
			int j = pageSelect / 10;
			int k = pageSelect % 10;
			if (k == 0) {
				j--;
			}
			for (int i = 1; i <= 10; i++) {
				if (pageSelect == j * 10 + i) {
					pageSel(i).setSelected(true);
					selectedPage = pageSelect;
				}
				page(i).setText(Integer.toString(j * 10 + i));

			}

		} else if (noOfFullPane * 10 < pageSelect && remaining > 0) {
			int j = pageSelect / 10;
			// int k = pageSelect % 10;
			for (int i = 1; i <= 10; i++) {
				if (pageSelect <= noOfPages) {
					if (pageSelect == j * 10 + i) {
						pageSel(i).setSelected(true);
						selectedPage = pageSelect;
					}
					if (noOfPages >= j * 10 + i) {
						page(i).setText(Integer.toString(j * 10 + i));
					} else {
						page(i).setText("");
					}
				} else {
					pageHandling(selectedPage, noOfPages);
				}
			}
		}

	}

	void itemSelectionHandling(int i) {
		int newItem;
		if (selectedPage % 10 > 0) {
			newItem = selectedPage / 10 * 10 + i;
			pageHandling(newItem, noOfPages);
			if (newItem <= noOfPages) {
				test.setText(newItem + "/" + noOfPages);
				setCurrentPageNo(newItem - 1);
				setAnswer();
				getAfterPageSelectionHandler().afterPageSelectionProcess(
						newItem - 1);
			}
		} else {
			newItem = (selectedPage / 10 - 1) * 10 + i;
			pageHandling(newItem, noOfPages);
			if (newItem <= noOfPages) {
				test.setText(newItem + "/" + noOfPages);
				setCurrentPageNo(newItem - 1);
				setAnswer();
				getAfterPageSelectionHandler().afterPageSelectionProcess(
						newItem - 1);
			}
		}
	}

	TextView page(int pageNo) {
		switch (pageNo) {
		case 1:
			return testPagePage1Txt;

		case 2:
			return testPagePage2Txt;

		case 3:
			return testPagePage3Txt;

		case 4:
			return testPagePage4Txt;

		case 5:
			return testPagePage5Txt;

		case 6:
			return testPagePage6Txt;

		case 7:
			return testPagePage7Txt;

		case 8:
			return testPagePage8Txt;

		case 9:
			return testPagePage9Txt;

		case 10:
			return testPagePage10Txt;

		}
		return null;
	}

	ImageButton pageSel(int pageNo) {
		switch (pageNo) {
		case 1:
			return page1Sel;

		case 2:
			return page2Sel;

		case 3:
			return page3Sel;

		case 4:
			return page4Sel;

		case 5:
			return page5Sel;

		case 6:
			return page6Sel;

		case 7:
			return page7Sel;

		case 8:
			return page8Sel;

		case 9:
			return page9Sel;

		case 10:
			return page10Sel;

		}
		return null;
	}

	private void setSelectionBtnLayout() {
		SelectionBtnListener selectionBtnListener = new SelectionBtnListener();
		Button page1Btn = (Button) findViewById(R.id.test_page_select_page_1_btn);
		page1Btn.setOnClickListener(selectionBtnListener);
		page1Sel = (ImageButton) findViewById(R.id.test_page_select_page_1_sel);

		Button page6Btn = (Button) findViewById(R.id.test_page_select_page_6_btn);
		page6Btn.setOnClickListener(selectionBtnListener);
		page6Sel = (ImageButton) findViewById(R.id.test_page_select_page_6_sel);

		Button page2Btn = (Button) findViewById(R.id.test_page_select_page_2_btn);
		page2Btn.setOnClickListener(selectionBtnListener);
		page2Sel = (ImageButton) findViewById(R.id.test_page_select_page_2_sel);

		Button page7Btn = (Button) findViewById(R.id.test_page_select_page_7_btn);
		page7Btn.setOnClickListener(selectionBtnListener);
		page7Sel = (ImageButton) findViewById(R.id.test_page_select_page_7_sel);

		Button page3Btn = (Button) findViewById(R.id.test_page_select_page_3_btn);
		page3Btn.setOnClickListener(selectionBtnListener);
		page3Sel = (ImageButton) findViewById(R.id.test_page_select_page_3_sel);

		Button page8Btn = (Button) findViewById(R.id.test_page_select_page_8_btn);
		page8Btn.setOnClickListener(selectionBtnListener);
		page8Sel = (ImageButton) findViewById(R.id.test_page_select_page_8_sel);

		Button page4Btn = (Button) findViewById(R.id.test_page_select_page_4_btn);
		page4Btn.setOnClickListener(selectionBtnListener);
		page4Sel = (ImageButton) findViewById(R.id.test_page_select_page_4_sel);

		Button page9Btn = (Button) findViewById(R.id.test_page_select_page_9_btn);
		page9Btn.setOnClickListener(selectionBtnListener);
		page9Sel = (ImageButton) findViewById(R.id.test_page_select_page_9_sel);

		Button page5Btn = (Button) findViewById(R.id.test_page_select_page_5_btn);
		page5Btn.setOnClickListener(selectionBtnListener);
		page5Sel = (ImageButton) findViewById(R.id.test_page_select_page_5_sel);

		Button page10Btn = (Button) findViewById(R.id.test_page_select_page_10_btn);
		page10Btn.setOnClickListener(selectionBtnListener);
		page10Sel = (ImageButton) findViewById(R.id.test_page_select_page_10_sel);

		ImageButton leftBtn = (ImageButton) findViewById(R.id.test_page_left_btn);
		leftBtn.setOnClickListener(selectionBtnListener);
		ImageButton rightBtn = (ImageButton) findViewById(R.id.test_page_right_btn);
		rightBtn.setOnClickListener(selectionBtnListener);
	}

	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.test_page_select_page_1_btn:
				itemSelectionHandling(1);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_2_btn:
				itemSelectionHandling(2);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_3_btn:
				itemSelectionHandling(3);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_4_btn:
				itemSelectionHandling(4);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_5_btn:
				itemSelectionHandling(5);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_6_btn:
				itemSelectionHandling(6);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_7_btn:
				itemSelectionHandling(7);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_8_btn:
				itemSelectionHandling(8);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_9_btn:
				itemSelectionHandling(9);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_select_page_10_btn:
				itemSelectionHandling(10);
				if (getPlayer1() != null)
					getPlayer1().stop();
				if (getPlayer2() != null)
					getPlayer2().stop();
				break;
			case R.id.test_page_right_btn:
				if ((selectedPage / 10 + 1) * 10 < noOfPages) {
					int newPage = (selectedPage / 10 + 1) * 10 + 1;
					pageHandling(newPage, noOfPages);
					test.setText(newPage + "/" + noOfPages);
				}
				break;
			case R.id.test_page_left_btn:
				if ((selectedPage / 10 - 1) * 10 >= 0) {
					int newPage = (selectedPage / 10 - 1) * 10 + 1;
					pageHandling(newPage, noOfPages);
					test.setText(newPage + "/" + noOfPages);
				}
				break;
			}
		}

	}

	private void showFinishMessageDialog() {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		builder.setMessage("這是最後一頁").setTitle("第一階段學習模式");
		builder.setPositiveButton("回到目錄",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						finish();
					}

				});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		});
		// 3. Get the AlertDialog from create()

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	private class ClickBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// StageOneConson stageOneconson =
			// getOnePageData(getCurrentPageNo());
			// int answer = stageOneconson.getAnswer();

			switch (v.getId()) {
			case R.id.test_page_back_btn:
				finish();
				break;
			case R.id.test1a_page_menu_btn:
				finish();
				break;
			case R.id.test_one_next_btn1:
			case R.id.test_one_next_btn2:
				if (noOfPages > (getCurrentPageNo() + 1)) {
					setAnswer();
					setCurrentPageNo(getCurrentPageNo() + 1);
					pageHandling(getCurrentPageNo() + 1, noOfPages);
					test.setText(getCurrentPageNo() + 1 + "/" + noOfPages);
					getAfterPageSelectionHandler().afterPageSelectionProcess(
							getCurrentPageNo());

				} else {
					SaveChallengeOneRecord saveChallengeOneRecord = new SaveChallengeOneRecord(
							L1aTestFragementActivity.this);
					saveChallengeOneRecord.todayDone();
					showFinishMessageDialog();
				}
				break;
			case R.id.stage_one_a_item_display_icon1:
			case R.id.test1_input_answer_btn_1:
				enableNormalBtn(false);
				if (answer == 0) {
					getTrueAnswerBtn(1).setVisibility(View.VISIBLE);
					getFalseAnswerBtn(1).setVisibility(View.INVISIBLE);
					getNormalBtn(1).setVisibility(View.INVISIBLE);

					getTrueAnswerBtn(2).setVisibility(View.INVISIBLE);
					getFalseAnswerBtn(2).setVisibility(View.INVISIBLE);
					getNormalBtn(2).setVisibility(View.VISIBLE);

					getIndicatorOfRightAnswer(1).setVisibility(View.VISIBLE);
					getIndicatorOfWrongAnswer(1).setVisibility(View.INVISIBLE);
					getFrameOfShowPic(1).setVisibility(View.INVISIBLE);

					getIndicatorOfRightAnswer(2).setVisibility(View.INVISIBLE);
					getIndicatorOfWrongAnswer(2).setVisibility(View.INVISIBLE);
					getFrameOfShowPic(2).setVisibility(View.VISIBLE);

					getNextBtn(1).setVisibility(View.VISIBLE);
					getNextBtn(2).setVisibility(View.INVISIBLE);

					playPassSound();
					Log.d("PlaySound", "PlaySound");
				} else {
					getTrueAnswerBtn(1).setVisibility(View.INVISIBLE);
					getFalseAnswerBtn(1).setVisibility(View.INVISIBLE);
					getNormalBtn(1).setVisibility(View.VISIBLE);

					getTrueAnswerBtn(2).setVisibility(View.INVISIBLE);
					getFalseAnswerBtn(2).setVisibility(View.VISIBLE);
					getNormalBtn(2).setVisibility(View.INVISIBLE);

					getIndicatorOfRightAnswer(1).setVisibility(View.INVISIBLE);
					getIndicatorOfWrongAnswer(1).setVisibility(View.INVISIBLE);
					getFrameOfShowPic(1).setVisibility(View.VISIBLE);

					getIndicatorOfRightAnswer(2).setVisibility(View.INVISIBLE);
					getIndicatorOfWrongAnswer(2).setVisibility(View.VISIBLE);
					getFrameOfShowPic(2).setVisibility(View.INVISIBLE);

					getNextBtn(1).setVisibility(View.INVISIBLE);
					getNextBtn(2).setVisibility(View.VISIBLE);

					playFailSound();
					Log.d("PlaySound", "PlaySound");
				}
				break;
			case R.id.stage_one_a_item_display_icon2:
			case R.id.test1_input_answer_btn_2:
				enableNormalBtn(false);
				if (answer == 1) {
					getTrueAnswerBtn(1).setVisibility(View.INVISIBLE);
					getFalseAnswerBtn(1).setVisibility(View.INVISIBLE);
					getNormalBtn(1).setVisibility(View.VISIBLE);

					getTrueAnswerBtn(2).setVisibility(View.VISIBLE);
					getFalseAnswerBtn(2).setVisibility(View.INVISIBLE);
					getNormalBtn(2).setVisibility(View.INVISIBLE);

					getIndicatorOfRightAnswer(1).setVisibility(View.INVISIBLE);
					getIndicatorOfWrongAnswer(1).setVisibility(View.INVISIBLE);
					getFrameOfShowPic(1).setVisibility(View.VISIBLE);

					getIndicatorOfRightAnswer(2).setVisibility(View.VISIBLE);
					getIndicatorOfWrongAnswer(2).setVisibility(View.INVISIBLE);
					getFrameOfShowPic(2).setVisibility(View.INVISIBLE);

					getNextBtn(1).setVisibility(View.INVISIBLE);
					getNextBtn(2).setVisibility(View.VISIBLE);

					playPassSound();
					Log.d("PlaySound", "PlaySound");
				} else {
					getTrueAnswerBtn(1).setVisibility(View.INVISIBLE);
					getFalseAnswerBtn(1).setVisibility(View.VISIBLE);
					getNormalBtn(1).setVisibility(View.INVISIBLE);

					getTrueAnswerBtn(2).setVisibility(View.INVISIBLE);
					getFalseAnswerBtn(2).setVisibility(View.INVISIBLE);
					getNormalBtn(2).setVisibility(View.VISIBLE);

					getIndicatorOfRightAnswer(1).setVisibility(View.INVISIBLE);
					getIndicatorOfWrongAnswer(1).setVisibility(View.VISIBLE);
					getFrameOfShowPic(1).setVisibility(View.INVISIBLE);

					getIndicatorOfRightAnswer(2).setVisibility(View.INVISIBLE);
					getIndicatorOfWrongAnswer(2).setVisibility(View.INVISIBLE);
					getFrameOfShowPic(2).setVisibility(View.VISIBLE);

					getNextBtn(1).setVisibility(View.VISIBLE);
					getNextBtn(2).setVisibility(View.INVISIBLE);

					playFailSound();
					Log.d("PlaySound", "PlaySound");
				}
				break;
			case R.id.test2a_page_repeat_btn:
				getTrueAnswerBtn(1).setVisibility(View.INVISIBLE);
				getFalseAnswerBtn(1).setVisibility(View.INVISIBLE);
				getNormalBtn(1).setVisibility(View.VISIBLE);

				getTrueAnswerBtn(2).setVisibility(View.INVISIBLE);
				getFalseAnswerBtn(2).setVisibility(View.INVISIBLE);
				getNormalBtn(2).setVisibility(View.VISIBLE);

				getIndicatorOfRightAnswer(1).setVisibility(View.INVISIBLE);
				getIndicatorOfWrongAnswer(1).setVisibility(View.INVISIBLE);
				getFrameOfShowPic(1).setVisibility(View.VISIBLE);

				getIndicatorOfRightAnswer(2).setVisibility(View.INVISIBLE);
				getIndicatorOfWrongAnswer(2).setVisibility(View.INVISIBLE);
				getFrameOfShowPic(2).setVisibility(View.VISIBLE);

				getNextBtn(1).setVisibility(View.INVISIBLE);
				getNextBtn(2).setVisibility(View.INVISIBLE);

				playsound(L1aTestFragementActivity.this.currentPageNo);
				break;

			}

		}

	}

	private void getDataFromLocalDatabaseAndStartTheGame() {
		GetDataFromLocalDatabase<StageOneConson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneConson>(
				L1aTestFragementActivity.this, StageOneConson.class);
		ArrayList<StageOneConson> locals = getDataFromLocalDatabase
				.getDataFromLocalDatabase(getConson());
		setLocals(locals);
		L1aTestFragementActivity.this.runOnUiThread(new DisplayText());
	}

	private class DisplayText implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			noOfPages = getLocals().size();
			test.setText(1 + "/" + noOfPages);// draw display
			pageHandling(1, noOfPages);// draw panel
			setCurrentPageNo(0);
			getAfterPageSelectionHandler().afterPageSelectionProcess(0);

		}

	}

	private void resetPage() {
		if (getSoundPool1() != null)
			getSoundPool1().stop(getSoundID1());
		if (getPlayer1() != null)
			getPlayer1().stop();
		if (getPlayer2() != null)
			getPlayer2().stop();
	}

	/*private void showProgressDialog() {
		dialog = ProgressDialog.show(this, null, null, true);
		dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Use either finish() or return() to either close the activity
				// or just the dialog
				finish();
				return;
			}
		});
		dialog.show();
	}*/
	
	private void showProgressDialog(){
		dialog = new ProgressDialog(this);
		dialog.setButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		dialog.show();
	}

	private void displayOnePage(int pageno) {

		enableNormalBtn(false);
		resetPage();
		showProgressDialog();
		Thread b = new Thread(new LoadBmp(pageno));
		b.start();

	}

	private class LoadBmp implements Runnable {
		int pageno;

		public LoadBmp(int pageno) {
			this.pageno = pageno;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				StageOneConson stageOneconson = getOnePageData(pageno);
				String savePath = AuditoryDirectoryHelper
						.ObtainStageOneSavePath(stageOneconson.getConson());
				Log.d("savePathOfTest1a", savePath);
				Log.d("test1aPicture1Name", stageOneconson.getPicture1());
				File dest1 = new File(savePath, stageOneconson.getPicture1());
				FileInputStream fis1;
				picture1 = null;

				File dest2 = new File(savePath, stageOneconson.getPicture2());
				FileInputStream fis2;
				picture2 = null;

				try {
					fis1 = new FileInputStream(dest1);
					picture1 = BitmapFactory.decodeStream(fis1);

					fis2 = new FileInputStream(dest2);
					picture2 = BitmapFactory.decodeStream(fis2);

					if (picture1 != null && picture2 != null)
						break;

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			L1aTestFragementActivity.this
					.runOnUiThread(new ShowUIAfterLoadBmpFinish(pageno));
		}

	}

	private void playsound(int pageno) {
		enableNormalBtn(false);
		final StageOneConson stageOneConson = getOnePageData(pageno);
		final String savePath = AuditoryDirectoryHelper
				.ObtainStageOneSavePath(stageOneConson.getConson());
		L1aTestFragementActivity.this
				.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setSoundPool1(new SoundPool(10, AudioManager.STREAM_MUSIC, 0));
		setSoundID1(getSoundPool1().load(
				savePath + "/" + stageOneConson.getVoicechip1(), 1));
		getSoundPool1().setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				dialog.dismiss();
				setPlayer1(new MediaPlayer());
				getPlayer1().setOnCompletionListener(
						new OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {
								// TODO Auto-generated method stub
								enableNormalBtn(true);

							}
						});
				boolean notready = false;
				do {
					try {
						notready = false;
						if (getAnswer() == 0) {
							getPlayer1().setDataSource(
									savePath + "/"
											+ stageOneConson.getVoicechip1());
						} else {
							getPlayer1().setDataSource(
									savePath + "/"
											+ stageOneConson.getVoicechip2());
						}
						getPlayer1().prepare();
						getPlayer1().start();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						// notready=true;
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						notready = true;
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// notready=true;
						e.printStackTrace();
					}
				} while (notready);
			}
		});
	}

	private ImageView getIndicatorOfRightAnswer(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageView) findViewById(R.id.indicator_of_right_answer_of_test1);
		case 2:
			return (ImageView) findViewById(R.id.indicator_of_right_answer_of_test2);
		}
		return null;
	}

	private ImageView getIndicatorOfWrongAnswer(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageView) findViewById(R.id.indicator_of_wrong_answer_of_test1);
		case 2:
			return (ImageView) findViewById(R.id.indicator_of_wrong_answer_of_test2);
		}
		return null;
	}

	private ImageView getFrameOfShowPic(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageView) findViewById(R.id.frame_of_pic_of_test1);
		case 2:
			return (ImageView) findViewById(R.id.frame_of_pic_of_test2);
		}
		return null;
	}

	private ImageView getShowPic(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageView) findViewById(R.id.test_one_pic1);
		case 2:
			return (ImageView) findViewById(R.id.test_one_pic2);
		}
		return null;
	}

	private ImageButton getNextBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) findViewById(R.id.test_one_next_btn1);
		case 2:
			return (ImageButton) findViewById(R.id.test_one_next_btn2);
		}
		return null;
	}

	private ImageButton getNormalBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) findViewById(R.id.stage_one_a_item_display_icon1);
		case 2:
			return (ImageButton) findViewById(R.id.stage_one_a_item_display_icon2);
		}
		return null;
	}

	private ImageButton getFalseAnswerBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) findViewById(R.id.stage_one_a_item_false_answer1);
		case 2:
			return (ImageButton) findViewById(R.id.stage_one_a_item_false_answer2);
		}
		return null;
	}

	private ImageButton getTrueAnswerBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) findViewById(R.id.stage_one_a_item_true_answer1);
		case 2:
			return (ImageButton) findViewById(R.id.stage_one_a_item_true_answer2);
		}
		return null;
	}

	private TextView getWordTV(int itemNo) {
		switch (itemNo) {
		case 1:
			return (TextView) findViewById(R.id.level1_test_text_show1);
		case 2:
			return (TextView) findViewById(R.id.level1_test_text_show2);
		}
		return null;
	}

	private View getShowScreen() {
		return (View) findViewById(R.id.test1a_show_screen);
	}

	private class ShowUIAfterLoadBmpFinish implements Runnable {
		private int pageno;

		public ShowUIAfterLoadBmpFinish(int pageno) {
			this.pageno = pageno;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			getShowScreen().setVisibility(View.VISIBLE);
			getIndicatorOfRightAnswer(1).setVisibility(View.INVISIBLE);
			getIndicatorOfWrongAnswer(1).setVisibility(View.INVISIBLE);
			getFrameOfShowPic(1).setVisibility(View.VISIBLE);

			getIndicatorOfRightAnswer(2).setVisibility(View.INVISIBLE);
			getIndicatorOfWrongAnswer(2).setVisibility(View.INVISIBLE);
			getFrameOfShowPic(2).setVisibility(View.VISIBLE);

			getShowPic(1).setImageBitmap(picture1);
			getShowPic(2).setImageBitmap(picture2);

			getShowPic(1).setVisibility(View.VISIBLE);
			getShowPic(2).setVisibility(View.VISIBLE);
			getNextBtn(1).setVisibility(View.INVISIBLE);
			getNextBtn(2).setVisibility(View.INVISIBLE);

			StageOneConson stageOneconson = getOnePageData(pageno);
			String word1 = stageOneconson.getWord1();
			String word2 = stageOneconson.getWord2();

			getWordTV(1).setText(word1);
			getWordTV(2).setText(word2);

			getNormalBtn(1).setVisibility(View.VISIBLE);
			getFalseAnswerBtn(1).setVisibility(View.INVISIBLE);
			getTrueAnswerBtn(1).setVisibility(View.INVISIBLE);

			getNormalBtn(2).setVisibility(View.VISIBLE);
			getFalseAnswerBtn(2).setVisibility(View.INVISIBLE);
			getTrueAnswerBtn(2).setVisibility(View.INVISIBLE);

			// dialog.dismiss(); need remove comment finally

			// play sound
			playsound(pageno);

		}

	}

	private class PageHandler implements PageSelectionHandler {

		@Override
		public void afterPageSelectionProcess(int pageno) {
			// TODO Auto-generated method stub
			displayOnePage(pageno);
		}

	}

	private PageSelectionHandler getAfterPageSelectionHandler() {
		if (pageHandler == null)
			pageHandler = new PageHandler();
		return pageHandler;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	private StageOneConson getOnePageData(int pageno) {
		ArrayList<StageOneConson> locals = (ArrayList<StageOneConson>) getLocals();

		return locals.get(pageno);
	}

	public SoundPool getSoundPool1() {
		return soundPool1;
	}

	public void setSoundPool1(SoundPool soundPool1) {
		this.soundPool1 = soundPool1;
	}

	public SoundPool getSoundPool2() {
		return soundPool2;
	}

	public void setSoundPool2(SoundPool soundPool2) {
		this.soundPool2 = soundPool2;
	}

	public int getSoundID1() {
		return soundID1;
	}

	public void setSoundID1(int soundID1) {
		this.soundID1 = soundID1;
	}

	public int getSoundID2() {
		return soundID2;
	}

	public void setSoundID2(int soundID2) {
		this.soundID2 = soundID2;
	}

	public MediaPlayer getPlayer1() {
		return player1;
	}

	public void setPlayer1(MediaPlayer player1) {
		this.player1 = player1;
	}

	public MediaPlayer getPlayer2() {
		return player2;
	}

	public void setPlayer2(MediaPlayer player2) {
		this.player2 = player2;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		resetPage();

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		resetPage();
	}

	private void enableNormalBtn(boolean enable) {
		for (int i = 1; i <= 2; i++) {
			getNormalBtn(i).setEnabled(enable);
			getInputAnswerBtn(i).setEnabled(enable);
			if (enable)
				getInputAnswerBtn(i).setVisibility(View.VISIBLE);
			else
				getInputAnswerBtn(i).setVisibility(View.INVISIBLE);
		}
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer() {
		Random randomAnswer = new Random();
		int answer = randomAnswer.nextInt(2);
		this.answer = answer;
	}

	private class AfterFinishDownloadAction implements
			DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
			DownloadTrace.getInstance().addOneDownloadsComplete();
			L1aTestFragementActivity.this
					.runOnUiThread(new ShowDownloadProgress());
			L1aTestFragementActivity.this.runOnUiThread(new DismissDialog());
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			DownloadTrace.getInstance().addOneDownload();
			L1aTestFragementActivity.this
					.runOnUiThread(new ShowDownloadProgress());
		}

	}

	class ShowDownloadProgress implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			getDownloadProgressTxt().setText(
					DownloadTrace.getInstance().getNoOfDownloadsComplete()
							+ "/"
							+ DownloadTrace.getInstance().getNoOfDownloads());
		}

	}

	class DismissDialog implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (DownloadTrace.getInstance().getNoOfDownloadsComplete() == DownloadTrace
					.getInstance().getNoOfDownloads()) {
				downloadProgressDialog.dismiss();
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				setFirstTime(getConson());
				saveIsFirstDataToDatabase();
				getDataFromLocalDatabaseAndStartTheGame();
			}

		}

	}

	private DownloadService downloadService;

	public ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			downloadService = ((DownloadService.DownloadBinder) binder)
					.getService();
			downloadService.setUpdateReceiver(new AfterFinishDownloadAction());
		}

		public void onServiceDisconnected(ComponentName className) {
			downloadService.clearDownloads(DownloadTrace.getInstance()
					.getNoOfDownloads()
					- DownloadTrace.getInstance().getNoOfDownloadsComplete());
			downloadService.removeUpdateReceiver(null);
			downloadService = null;
			Log.d("service disconnected", "service disconnected");
		}
	};

	private void bindDownloadService() {

		bindService(new Intent(this, DownloadService.class), mConnection,
				BIND_AUTO_CREATE);

	}

	private void unBindDownloadService() {
		if (DownloadTrace.getInstance().getNoOfDownloads() > 0) {
			if (downloadService != null) {
				downloadService.clearDownloads(DownloadTrace.getInstance()
						.getNoOfDownloads()
						- DownloadTrace.getInstance()
								.getNoOfDownloadsComplete());
				if (mConnection != null) {
					try {
						unbindService(mConnection);
						

					} catch (IllegalArgumentException e) {

					}
					Intent intent = new Intent(this, DownloadService.class);
					stopService(intent);
				}
			}
		}
	}

	public TextView getDownloadProgressTxt() {
		return downloadProgressTxt;
	}

	public void setDownloadProgressTxt(TextView downloadProgressTxt) {
		this.downloadProgressTxt = downloadProgressTxt;
	}

	private void saveIsFirstDataToDatabase() {
		GetDataFromLocalDatabase<StageOneIsFirst> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneIsFirst>(
				this, StageOneIsFirst.class);
		ProcessIsFirstData<StageOneIsFirst> processIsFirstData = new ProcessIsFirstData<StageOneIsFirst>(
				getDataFromLocalDatabase);
		processIsFirstData.saveDataToLocalDataBase(new StageOneIsFirst());
	}

	private void getIsFirstDataFromDataAndSetup() {
		GetDataFromLocalDatabase<StageOneIsFirst> getDataFromLocalDatabase1 = new GetDataFromLocalDatabase<StageOneIsFirst>(
				this, StageOneIsFirst.class);
		ProcessIsFirstData<StageOneIsFirst> processIsFirstData1 = new ProcessIsFirstData<StageOneIsFirst>(
				getDataFromLocalDatabase1);
		processIsFirstData1.getDataFromDataBase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.show_download_dialog:
			downloadDialog.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Dialog downloadDialog;

	private void setupDownloadDialog() {
		downloadDialog = new Dialog(this);
		downloadDialog.setContentView(R.layout.test2a_syncdialog_ui);
		Button cancelBtn = (Button) downloadDialog
				.findViewById(R.id.test2a_sync_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialog.dismiss();
				getDataFromLocalDatabaseAndStartTheGame();

			}
		});
		Button okBtn = (Button) downloadDialog
				.findViewById(R.id.test2a_sync_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialog.dismiss();
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				showDownloadProgressDialog();
				bindDownloadService();
				syncData();

			}
		});
		if (isFirstTime(getConson())) {
			cancelBtn.setVisibility(View.GONE);
		} else {
			cancelBtn.setVisibility(View.VISIBLE);
		}
	}

	private void showDownloadDialog() {
		if (isOnline()) {
			if (isFirstTime(getConson()))
				downloadDialog.show();
			else
				getDataFromLocalDatabaseAndStartTheGame();
		} else {
			if (!isFirstTime(getConson()))
				getDataFromLocalDatabaseAndStartTheGame();
		}
	}
}
