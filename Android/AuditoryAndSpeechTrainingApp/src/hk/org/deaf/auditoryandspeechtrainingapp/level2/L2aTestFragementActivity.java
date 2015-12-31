package hk.org.deaf.auditoryandspeechtrainingapp.level2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hk.org.deaf.auditoryandspeechtrainingapp.CommonFunctionsForTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.Stage2Conson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoIsFirst;
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

public class L2aTestFragementActivity extends
		CommonFunctionsForTestFragmentActivity {

	public interface PageSelectionHandler {
		void afterPageSelectionProcess(int pageno);
	}

	private ImageView showPic;
	private ImageView frameOfShowPic;
	private ImageView indicatorOfRightAnswer;
	private ImageView indicatorOfWrongAnswer;

	@SuppressWarnings("unused")
	private ImageButton prevBtn;
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

	private TextView InputBox1;
	private TextView InputBox2;
	private TextView InputBox3;
	private TextView InputBox4;
	private TextView InputBox5;
	private TextView InputBox6;
	private TextView InputBox7;
	private TextView InputBox8;
	private TextView InputBox9;
	private TextView InputBox10;

	private View boxGroup1;
	private View boxGroup2;
	private View boxGroup3;
	private View boxGroup4;
	private View boxGroup5;
	private View boxGroup6;
	private View boxGroup7;
	private View boxGroup8;
	private View boxGroup9;
	private View boxGroup10;

	private ImageButton normalBtn1;
	private ImageButton normalBtn2;
	private ImageButton normalBtn3;
	private ImageButton normalBtn4;
	private ImageButton normalBtn5;
	private ImageButton normalBtn6;
	private ImageButton normalBtn7;
	private ImageButton normalBtn8;
	private ImageButton normalBtn9;
	private ImageButton normalBtn10;

	private ImageButton falseAnswerBtn1;
	private ImageButton falseAnswerBtn2;
	private ImageButton falseAnswerBtn3;
	private ImageButton falseAnswerBtn4;
	private ImageButton falseAnswerBtn5;
	private ImageButton falseAnswerBtn6;
	private ImageButton falseAnswerBtn7;
	private ImageButton falseAnswerBtn8;
	private ImageButton falseAnswerBtn9;
	private ImageButton falseAnswerBtn10;

	private ImageButton trueAnswerBtn1;
	private ImageButton trueAnswerBtn2;
	private ImageButton trueAnswerBtn3;
	private ImageButton trueAnswerBtn4;
	private ImageButton trueAnswerBtn5;
	private ImageButton trueAnswerBtn6;
	private ImageButton trueAnswerBtn7;
	private ImageButton trueAnswerBtn8;
	private ImageButton trueAnswerBtn9;
	private ImageButton trueAnswerBtn10;

	private ImageButton inputAnswerBtn1;
	private ImageButton inputAnswerBtn2;
	private ImageButton inputAnswerBtn3;
	private ImageButton inputAnswerBtn4;
	private ImageButton inputAnswerBtn5;
	private ImageButton inputAnswerBtn6;
	private ImageButton inputAnswerBtn7;
	private ImageButton inputAnswerBtn8;
	private ImageButton inputAnswerBtn9;
	private ImageButton inputAnswerBtn10;

	private ImageButton okBtn;
	private ImageButton nextBtn;

	private int selectedPage = 0;
	private int noOfPages = 176;
	private int currentPageNo;
	private String[] inputAnwser = new String[10];
	private boolean[] result = new boolean[10];

	private List<Stage2Conson> locals;
	private int conson;

	private PageHandler pageHandler;
	private boolean picFileReady = false;
	private ProgressDialog dialog;
	private Bitmap picture;
	private MediaPlayer player;

	private static boolean firstTimePh = true;
	private static boolean firstTimeTh = true;
	private static boolean firstTimeKh = true;
	private static boolean firstTimeS = true;
	private static boolean firstTimeTsh = true;
	private static boolean firstTimeNg = true;
	private static boolean firstTimeN = true;
	private int soundID;
	private SoundPool soundPool;

	public SoundPool getSoundPool() {
		return soundPool;
	}

	public void setSoundPool(SoundPool soundPool) {
		this.soundPool = soundPool;
	}

	public int getSoundID() {
		return soundID;
	}

	public void setSoundID(int soundID) {
		this.soundID = soundID;
	}

	public static boolean isFirstTime(int conson) {
		switch (conson) {
		case 0:
			return firstTimePh;
		case 1:
			return firstTimeTh;
		case 2:
			return firstTimeKh;
		case 3:
			return firstTimeS;
		case 4:
			return firstTimeTsh;
		case 5:
			return firstTimeNg;
		case 6:
			return firstTimeN;

		}
		return true;
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

	public static void setFirstTime(int conson) {
		switch (conson) {
		case 0:
			firstTimePh = false;
			break;
		case 1:
			firstTimeTh = false;
			break;
		case 2:
			firstTimeKh = false;
			break;
		case 3:
			firstTimeS = false;
			break;
		case 4:
			firstTimeTsh = false;
			break;
		case 5:
			firstTimeNg = false;
			break;
		case 6:
			firstTimeN = false;
			break;

		}

	}

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
	}

	public void inputAnswerInit() {
		for (int i = 0; i < 10; i++) {
			inputAnwser[i] = "0";
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level2a_test_revised);
		getConsonFromIntent();
		ContextStorage.getInstance().setContext(getApplicationContext());
		setContentLayout();
		setUpDownloadProgressDialog();
		setSelectionBtnLayout();
		setInputAnswerUILayout();
		getIsFirstDataFromDataAndSetup();
		inputAnswerInit();

		setupDownloadDialog();
		showDownloadDialog();

	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	Dialog downloadDialog;

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
				bindService();
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

	private void replaySound() {
		/*
		 * frameOfShowPic.setVisibility(View.VISIBLE);
		 * indicatorOfWrongAnswer.setVisibility(View.INVISIBLE);
		 * indicatorOfRightAnswer.setVisibility(View.INVISIBLE); int i =0;
		 * getNormalBtn(i).setVisibility(View.VISIBLE);
		 * getFalseAnswerBtn(i).setVisibility(View.INVISIBLE);
		 * getTrueAnswerBtn(i).setVisibility(View.INVISIBLE);
		 * getInputAnswerBtn(i).setVisibility(View.INVISIBLE);
		 * 
		 * playsound(getCurrentPageNo());
		 */
		getAfterPageSelectionHandler().afterPageSelectionProcess(
				getCurrentPageNo());

	}

	@SuppressWarnings("unused")
	private void showProgressDialog() {
		dialog = ProgressDialog.show(this, null, null, true);
		/*
		 * dialog.setButton(Dialog.BUTTON_NEGATIVE,"Cancel", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub
		 * L2aTestFragementActivity.this.finish(); }
		 * 
		 * });
		 */
		dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Use either finish() or return() to either close the activity
				// or just the dialog
				finish();
				return;
			}
		});
		dialog.show();
	}

	private void getConsonFromIntent() {
		int conson = getIntent().getIntExtra("conson", 0);
		Log.d("conson", String.valueOf(conson));
		setConson(conson);
	}

	private String getConsonString(int conson) {
		switch (conson) {
		case 0:
			return "ph";
		case 1:
			return "th";
		case 2:
			return "kh";
		case 3:
			return "s";
		case 4:
			return "tsh";
		case 5:
			return "ng";
		case 6:
			return "n";
		}
		return null;
	}

	public static int getConsonDrawable(int conson) {
		switch (conson) {
		case 0:
			return R.drawable.level2a_page_ph_sel_item;
		case 1:
			return R.drawable.level2a_page_th_sel_item;
		case 2:
			return R.drawable.level2a_page_kh_sel_item;
		case 3:
			return R.drawable.level2a_page_s_sel_item;
		case 4:
			return R.drawable.level2a_page_tsh_sel_item;
		case 5:
			return R.drawable.level2a_page_ng_sel_item;
		case 6:
			return R.drawable.level2a_page_n_sel_item;
		}
		return 0;
	}

	private void setContentLayout() {
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

		/*
		 * TextView consonTxtndicator = (TextView)
		 * findViewById(R.id.test_level_2a_indicator_txt);
		 * consonTxtndicator.setText(getConsonString(getConson())); int
		 * sizeForconsonndicator = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);
		 * consonTxtndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX,
		 * sizeForconsonndicator);
		 */
		ImageView imgIndicator = (ImageView) findViewById(R.id.test_level_2a_indicator);
		imgIndicator.setImageResource(getConsonDrawable(getConson()));

		// test.setText(1 + "/" + noOfPages);
		ImageButton backBtn = (ImageButton) findViewById(R.id.test_page_back_btn);
		backBtn.setOnClickListener(new ClickBtnListener());
		ImageButton repeatPlaySoundBtn = (ImageButton) findViewById(R.id.test2a_page_repeat_btn);
		repeatPlaySoundBtn.setOnClickListener(new ClickBtnListener());

		showPic = (ImageView) findViewById(R.id.show_pic);
		frameOfShowPic = (ImageView) findViewById(R.id.frame_of_pic);
		indicatorOfRightAnswer = (ImageView) findViewById(R.id.indicator_of_right_answer);
		indicatorOfWrongAnswer = (ImageView) findViewById(R.id.indicator_of_wrong_answer);

		InputBox1 = (TextView) findViewById(R.id.test_level_2a_indicator_txt1);
		InputBox2 = (TextView) findViewById(R.id.test_level_2a_indicator_txt2);
		InputBox3 = (TextView) findViewById(R.id.test_level_2a_indicator_txt3);
		InputBox4 = (TextView) findViewById(R.id.test_level_2a_indicator_txt4);
		InputBox5 = (TextView) findViewById(R.id.test_level_2a_indicator_txt5);
		InputBox6 = (TextView) findViewById(R.id.test_level_2a_indicator_txt6);
		InputBox7 = (TextView) findViewById(R.id.test_level_2a_indicator_txt7);
		InputBox8 = (TextView) findViewById(R.id.test_level_2a_indicator_txt8);
		InputBox9 = (TextView) findViewById(R.id.test_level_2a_indicator_txt9);
		InputBox10 = (TextView) findViewById(R.id.test_level_2a_indicator_txt10);

		int sizeForInputBox = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);

		InputBox1.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox2.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox3.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox4.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox5.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox6.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox7.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox8.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox9.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);
		InputBox10.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForInputBox);

		boxGroup1 = (View) findViewById(R.id.test_level_2a_allbox1);
		boxGroup2 = (View) findViewById(R.id.test_level_2a_allbox2);
		boxGroup3 = (View) findViewById(R.id.test_level_2a_allbox3);
		boxGroup4 = (View) findViewById(R.id.test_level_2a_allbox4);
		boxGroup5 = (View) findViewById(R.id.test_level_2a_allbox5);
		boxGroup6 = (View) findViewById(R.id.test_level_2a_allbox6);
		boxGroup7 = (View) findViewById(R.id.test_level_2a_allbox7);
		boxGroup8 = (View) findViewById(R.id.test_level_2a_allbox8);
		boxGroup9 = (View) findViewById(R.id.test_level_2a_allbox9);
		boxGroup10 = (View) findViewById(R.id.test_level_2a_allbox10);

	}

	private void setSelectionBtnLayout() {
		Button page1Btn = (Button) findViewById(R.id.test_page_select_page_1_btn);
		page1Btn.setOnClickListener(new SelectionBtnListener());
		page1Sel = (ImageButton) findViewById(R.id.test_page_select_page_1_sel);

		Button page6Btn = (Button) findViewById(R.id.test_page_select_page_6_btn);
		page6Btn.setOnClickListener(new SelectionBtnListener());
		page6Sel = (ImageButton) findViewById(R.id.test_page_select_page_6_sel);

		Button page2Btn = (Button) findViewById(R.id.test_page_select_page_2_btn);
		page2Btn.setOnClickListener(new SelectionBtnListener());
		page2Sel = (ImageButton) findViewById(R.id.test_page_select_page_2_sel);

		Button page7Btn = (Button) findViewById(R.id.test_page_select_page_7_btn);
		page7Btn.setOnClickListener(new SelectionBtnListener());
		page7Sel = (ImageButton) findViewById(R.id.test_page_select_page_7_sel);

		Button page3Btn = (Button) findViewById(R.id.test_page_select_page_3_btn);
		page3Btn.setOnClickListener(new SelectionBtnListener());
		page3Sel = (ImageButton) findViewById(R.id.test_page_select_page_3_sel);

		Button page8Btn = (Button) findViewById(R.id.test_page_select_page_8_btn);
		page8Btn.setOnClickListener(new SelectionBtnListener());
		page8Sel = (ImageButton) findViewById(R.id.test_page_select_page_8_sel);

		Button page4Btn = (Button) findViewById(R.id.test_page_select_page_4_btn);
		page4Btn.setOnClickListener(new SelectionBtnListener());
		page4Sel = (ImageButton) findViewById(R.id.test_page_select_page_4_sel);

		Button page9Btn = (Button) findViewById(R.id.test_page_select_page_9_btn);
		page9Btn.setOnClickListener(new SelectionBtnListener());
		page9Sel = (ImageButton) findViewById(R.id.test_page_select_page_9_sel);

		Button page5Btn = (Button) findViewById(R.id.test_page_select_page_5_btn);
		page5Btn.setOnClickListener(new SelectionBtnListener());
		page5Sel = (ImageButton) findViewById(R.id.test_page_select_page_5_sel);

		Button page10Btn = (Button) findViewById(R.id.test_page_select_page_10_btn);
		page10Btn.setOnClickListener(new SelectionBtnListener());
		page10Sel = (ImageButton) findViewById(R.id.test_page_select_page_10_sel);

		ImageButton leftBtn = (ImageButton) findViewById(R.id.test_page_left_btn);
		leftBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton rightBtn = (ImageButton) findViewById(R.id.test_page_right_btn);
		rightBtn.setOnClickListener(new SelectionBtnListener());
	}

	private void setInputAnswerUILayout() {
		ClickBtnListener clickBtnListener = new ClickBtnListener();
		normalBtn1 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon1);
		normalBtn2 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon2);
		normalBtn3 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon3);
		normalBtn4 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon4);
		normalBtn5 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon5);
		normalBtn6 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon6);
		normalBtn7 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon7);
		normalBtn8 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon8);
		normalBtn9 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon9);
		normalBtn10 = (ImageButton) findViewById(R.id.stage_two_a_item_display_icon10);
		normalBtn1.setOnClickListener(clickBtnListener);
		normalBtn2.setOnClickListener(clickBtnListener);
		normalBtn3.setOnClickListener(clickBtnListener);
		normalBtn4.setOnClickListener(clickBtnListener);
		normalBtn5.setOnClickListener(clickBtnListener);
		normalBtn6.setOnClickListener(clickBtnListener);
		normalBtn7.setOnClickListener(clickBtnListener);
		normalBtn8.setOnClickListener(clickBtnListener);
		normalBtn9.setOnClickListener(clickBtnListener);
		normalBtn10.setOnClickListener(clickBtnListener);
		falseAnswerBtn1 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer1);
		falseAnswerBtn2 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer2);
		falseAnswerBtn3 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer3);
		falseAnswerBtn4 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer4);
		falseAnswerBtn5 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer5);
		falseAnswerBtn6 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer6);
		falseAnswerBtn7 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer7);
		falseAnswerBtn8 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer8);
		falseAnswerBtn9 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer9);
		falseAnswerBtn10 = (ImageButton) findViewById(R.id.stage_two_a_item_false_answer10);
		falseAnswerBtn1.setOnClickListener(clickBtnListener);
		falseAnswerBtn2.setOnClickListener(clickBtnListener);
		falseAnswerBtn3.setOnClickListener(clickBtnListener);
		falseAnswerBtn4.setOnClickListener(clickBtnListener);
		falseAnswerBtn5.setOnClickListener(clickBtnListener);
		falseAnswerBtn6.setOnClickListener(clickBtnListener);
		falseAnswerBtn7.setOnClickListener(clickBtnListener);
		falseAnswerBtn8.setOnClickListener(clickBtnListener);
		falseAnswerBtn9.setOnClickListener(clickBtnListener);
		falseAnswerBtn10.setOnClickListener(clickBtnListener);
		trueAnswerBtn1 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer1);
		trueAnswerBtn2 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer2);
		trueAnswerBtn3 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer3);
		trueAnswerBtn4 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer4);
		trueAnswerBtn5 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer5);
		trueAnswerBtn6 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer6);
		trueAnswerBtn7 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer7);
		trueAnswerBtn8 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer8);
		trueAnswerBtn9 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer9);
		trueAnswerBtn10 = (ImageButton) findViewById(R.id.stage_two_a_item_true_answer10);
		trueAnswerBtn1.setOnClickListener(clickBtnListener);
		trueAnswerBtn2.setOnClickListener(clickBtnListener);
		trueAnswerBtn3.setOnClickListener(clickBtnListener);
		trueAnswerBtn4.setOnClickListener(clickBtnListener);
		trueAnswerBtn5.setOnClickListener(clickBtnListener);
		trueAnswerBtn6.setOnClickListener(clickBtnListener);
		trueAnswerBtn7.setOnClickListener(clickBtnListener);
		trueAnswerBtn8.setOnClickListener(clickBtnListener);
		trueAnswerBtn9.setOnClickListener(clickBtnListener);
		trueAnswerBtn10.setOnClickListener(clickBtnListener);
		inputAnswerBtn1 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted1);
		inputAnswerBtn2 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted2);
		inputAnswerBtn3 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted3);
		inputAnswerBtn4 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted4);
		inputAnswerBtn5 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted5);
		inputAnswerBtn6 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted6);
		inputAnswerBtn7 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted7);
		inputAnswerBtn8 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted8);
		inputAnswerBtn9 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted9);
		inputAnswerBtn10 = (ImageButton) findViewById(R.id.stage_two_a_item_seleted10);
		inputAnswerBtn1.setOnClickListener(clickBtnListener);
		inputAnswerBtn2.setOnClickListener(clickBtnListener);
		inputAnswerBtn3.setOnClickListener(clickBtnListener);
		inputAnswerBtn4.setOnClickListener(clickBtnListener);
		inputAnswerBtn5.setOnClickListener(clickBtnListener);
		inputAnswerBtn6.setOnClickListener(clickBtnListener);
		inputAnswerBtn7.setOnClickListener(clickBtnListener);
		inputAnswerBtn8.setOnClickListener(clickBtnListener);
		inputAnswerBtn9.setOnClickListener(clickBtnListener);
		inputAnswerBtn10.setOnClickListener(clickBtnListener);

		// confirm btn and nextbutton
		okBtn = (ImageButton) findViewById(R.id.test2a_page_ok_btn);
		nextBtn = (ImageButton) findViewById(R.id.test2a_page_next_btn);
		okBtn.setOnClickListener(clickBtnListener);
		nextBtn.setOnClickListener(clickBtnListener);

		getMenuBtn().setOnClickListener(clickBtnListener);

	}

	private ImageButton getMenuBtn() {
		return (ImageButton) findViewById(R.id.test1a_page_menu_btn);
	}

	private void pageHandling(int pageSelect, int noOfPages) {
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

	private void itemSelectionHandling(int i) {
		int newItem;
		if (selectedPage % 10 > 0) {
			newItem = selectedPage / 10 * 10 + i;
			pageHandling(newItem, noOfPages);
			if (newItem <= noOfPages) {
				test.setText(newItem + "/" + noOfPages);
				setCurrentPageNo(newItem - 1);
				getAfterPageSelectionHandler().afterPageSelectionProcess(
						newItem - 1);
			}
		} else {
			newItem = (selectedPage / 10 - 1) * 10 + i;
			pageHandling(newItem, noOfPages);
			if (newItem <= noOfPages) {
				test.setText(newItem + "/" + noOfPages);
				setCurrentPageNo(newItem - 1);
				getAfterPageSelectionHandler().afterPageSelectionProcess(
						newItem - 1);
			}
		}
	}

	private TextView page(int pageNo) {
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

	private ImageButton pageSel(int pageNo) {
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

	private class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.test_page_select_page_1_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(1);
				break;
			case R.id.test_page_select_page_2_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(2);
				break;
			case R.id.test_page_select_page_3_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(3);
				break;
			case R.id.test_page_select_page_4_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(4);
				break;
			case R.id.test_page_select_page_5_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(5);
				break;
			case R.id.test_page_select_page_6_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(6);
				break;
			case R.id.test_page_select_page_7_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(7);
				break;
			case R.id.test_page_select_page_8_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(8);
				break;
			case R.id.test_page_select_page_9_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(9);
				break;
			case R.id.test_page_select_page_10_btn:
				if (getSoundPool() != null)
					getSoundPool().stop(getSoundID());
				if (getPlayer() != null)
					getPlayer().stop();
				itemSelectionHandling(10);
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

	private class ClickBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.test_page_back_btn:
				finish();
				break;
			case R.id.test2a_page_repeat_btn:
				// playsound(L2aTestFragementActivity.this.currentPageNo);
				replaySound();
				break;
			case R.id.test1a_page_menu_btn:
				finish();
				break;
			// for nomral input button click
			case R.id.stage_two_a_item_display_icon1:
				normalBtn1.setVisibility(View.INVISIBLE);
				falseAnswerBtn1.setVisibility(View.INVISIBLE);
				trueAnswerBtn1.setVisibility(View.INVISIBLE);
				inputAnswerBtn1.setVisibility(View.VISIBLE);
				inputAnwser[0] = "1";
				break;
			case R.id.stage_two_a_item_display_icon2:
				normalBtn2.setVisibility(View.INVISIBLE);
				falseAnswerBtn2.setVisibility(View.INVISIBLE);
				trueAnswerBtn2.setVisibility(View.INVISIBLE);
				inputAnswerBtn2.setVisibility(View.VISIBLE);
				inputAnwser[1] = "1";
				break;
			case R.id.stage_two_a_item_display_icon3:
				normalBtn3.setVisibility(View.INVISIBLE);
				falseAnswerBtn3.setVisibility(View.INVISIBLE);
				trueAnswerBtn3.setVisibility(View.INVISIBLE);
				inputAnswerBtn3.setVisibility(View.VISIBLE);
				inputAnwser[2] = "1";
				break;
			case R.id.stage_two_a_item_display_icon4:
				normalBtn4.setVisibility(View.INVISIBLE);
				falseAnswerBtn4.setVisibility(View.INVISIBLE);
				trueAnswerBtn4.setVisibility(View.INVISIBLE);
				inputAnswerBtn4.setVisibility(View.VISIBLE);
				inputAnwser[3] = "1";
				break;
			case R.id.stage_two_a_item_display_icon5:
				normalBtn5.setVisibility(View.INVISIBLE);
				falseAnswerBtn5.setVisibility(View.INVISIBLE);
				trueAnswerBtn5.setVisibility(View.INVISIBLE);
				inputAnswerBtn5.setVisibility(View.VISIBLE);
				inputAnwser[4] = "1";
				break;
			case R.id.stage_two_a_item_display_icon6:
				normalBtn6.setVisibility(View.INVISIBLE);
				falseAnswerBtn6.setVisibility(View.INVISIBLE);
				trueAnswerBtn6.setVisibility(View.INVISIBLE);
				inputAnswerBtn6.setVisibility(View.VISIBLE);
				inputAnwser[5] = "1";
				break;
			case R.id.stage_two_a_item_display_icon7:
				normalBtn7.setVisibility(View.INVISIBLE);
				falseAnswerBtn7.setVisibility(View.INVISIBLE);
				trueAnswerBtn7.setVisibility(View.INVISIBLE);
				inputAnswerBtn7.setVisibility(View.VISIBLE);
				inputAnwser[6] = "1";
				break;
			case R.id.stage_two_a_item_display_icon8:
				normalBtn8.setVisibility(View.INVISIBLE);
				falseAnswerBtn8.setVisibility(View.INVISIBLE);
				trueAnswerBtn8.setVisibility(View.INVISIBLE);
				inputAnswerBtn8.setVisibility(View.VISIBLE);
				inputAnwser[7] = "1";
				break;
			case R.id.stage_two_a_item_display_icon9:
				normalBtn9.setVisibility(View.INVISIBLE);
				falseAnswerBtn9.setVisibility(View.INVISIBLE);
				trueAnswerBtn9.setVisibility(View.INVISIBLE);
				inputAnswerBtn9.setVisibility(View.VISIBLE);
				inputAnwser[8] = "1";
				break;
			case R.id.stage_two_a_item_display_icon10:
				normalBtn10.setVisibility(View.INVISIBLE);
				falseAnswerBtn10.setVisibility(View.INVISIBLE);
				trueAnswerBtn10.setVisibility(View.INVISIBLE);
				inputAnswerBtn10.setVisibility(View.VISIBLE);
				inputAnwser[9] = "1";
				break;

			// for selected input button
			case R.id.stage_two_a_item_seleted1:
				normalBtn1.setVisibility(View.VISIBLE);
				falseAnswerBtn1.setVisibility(View.INVISIBLE);
				trueAnswerBtn1.setVisibility(View.INVISIBLE);
				inputAnswerBtn1.setVisibility(View.INVISIBLE);
				inputAnwser[0] = "0";
				break;
			case R.id.stage_two_a_item_seleted2:
				normalBtn2.setVisibility(View.VISIBLE);
				falseAnswerBtn2.setVisibility(View.INVISIBLE);
				trueAnswerBtn2.setVisibility(View.INVISIBLE);
				inputAnswerBtn2.setVisibility(View.INVISIBLE);
				inputAnwser[1] = "0";
				break;
			case R.id.stage_two_a_item_seleted3:
				normalBtn3.setVisibility(View.VISIBLE);
				falseAnswerBtn3.setVisibility(View.INVISIBLE);
				trueAnswerBtn3.setVisibility(View.INVISIBLE);
				inputAnswerBtn3.setVisibility(View.INVISIBLE);
				inputAnwser[2] = "0";
				break;
			case R.id.stage_two_a_item_seleted4:
				normalBtn4.setVisibility(View.VISIBLE);
				falseAnswerBtn4.setVisibility(View.INVISIBLE);
				trueAnswerBtn4.setVisibility(View.INVISIBLE);
				inputAnswerBtn4.setVisibility(View.INVISIBLE);
				inputAnwser[3] = "0";
				break;
			case R.id.stage_two_a_item_seleted5:
				normalBtn5.setVisibility(View.VISIBLE);
				falseAnswerBtn5.setVisibility(View.INVISIBLE);
				trueAnswerBtn5.setVisibility(View.INVISIBLE);
				inputAnswerBtn5.setVisibility(View.INVISIBLE);
				inputAnwser[4] = "0";
				break;
			case R.id.stage_two_a_item_seleted6:
				normalBtn6.setVisibility(View.VISIBLE);
				falseAnswerBtn6.setVisibility(View.INVISIBLE);
				trueAnswerBtn6.setVisibility(View.INVISIBLE);
				inputAnswerBtn6.setVisibility(View.INVISIBLE);
				inputAnwser[5] = "0";
				break;
			case R.id.stage_two_a_item_seleted7:
				normalBtn7.setVisibility(View.VISIBLE);
				falseAnswerBtn7.setVisibility(View.INVISIBLE);
				trueAnswerBtn7.setVisibility(View.INVISIBLE);
				inputAnswerBtn7.setVisibility(View.INVISIBLE);
				inputAnwser[6] = "0";
				break;
			case R.id.stage_two_a_item_seleted8:
				normalBtn8.setVisibility(View.VISIBLE);
				falseAnswerBtn8.setVisibility(View.INVISIBLE);
				trueAnswerBtn8.setVisibility(View.INVISIBLE);
				inputAnswerBtn8.setVisibility(View.INVISIBLE);
				inputAnwser[7] = "0";
				break;
			case R.id.stage_two_a_item_seleted9:
				normalBtn9.setVisibility(View.VISIBLE);
				falseAnswerBtn9.setVisibility(View.INVISIBLE);
				trueAnswerBtn9.setVisibility(View.INVISIBLE);
				inputAnswerBtn9.setVisibility(View.INVISIBLE);
				inputAnwser[8] = "0";
				break;
			case R.id.stage_two_a_item_seleted10:
				normalBtn10.setVisibility(View.VISIBLE);
				falseAnswerBtn10.setVisibility(View.INVISIBLE);
				trueAnswerBtn10.setVisibility(View.INVISIBLE);
				inputAnswerBtn10.setVisibility(View.INVISIBLE);
				inputAnwser[9] = "0";
				break;

			// next btn and ok btn
			case R.id.test2a_page_ok_btn:
				enableNormalBtn(false);
				Stage2Conson stage2conson = getOnePageData(currentPageNo);
				int sentenceLength = stage2conson.getSentence().length();
				for (int i = 0; i < sentenceLength; i++) {
					if (getCorrectAnswer(i + 1) != null) {
						Log.d("getCorrectAnswer", getCorrectAnswer(i + 1));
						if (inputAnwser[i].equals(getCorrectAnswer(i + 1))) {
							result[i] = true;

						} else {
							result[i] = false;
							getInputAnswerBtn(i + 1).setVisibility(
									View.INVISIBLE);
							getFalseAnswerBtn(i + 1)
									.setVisibility(View.VISIBLE);
							getNormalBtn(i + 1).setVisibility(View.INVISIBLE);
							getTrueAnswerBtn(i + 1).setVisibility(
									View.INVISIBLE);
						}
						if (getCorrectAnswer(i + 1).equals("1")) {
							getInputAnswerBtn(i + 1).setVisibility(
									View.INVISIBLE);
							getFalseAnswerBtn(i + 1).setVisibility(
									View.INVISIBLE);
							getNormalBtn(i + 1).setVisibility(View.INVISIBLE);
							getTrueAnswerBtn(i + 1).setVisibility(View.VISIBLE);
						}
					}
				}
				boolean allcorrect = true;

				for (int i = 0; i < sentenceLength; i++) {
					allcorrect &= result[i];

				}
				if (allcorrect) {
					indicatorOfRightAnswer.setVisibility(View.VISIBLE);
					indicatorOfWrongAnswer.setVisibility(View.INVISIBLE);
					frameOfShowPic.setVisibility(View.INVISIBLE);
					showPic.setVisibility(View.VISIBLE);
					playPassSound();
				} else {
					indicatorOfRightAnswer.setVisibility(View.INVISIBLE);
					indicatorOfWrongAnswer.setVisibility(View.VISIBLE);
					frameOfShowPic.setVisibility(View.INVISIBLE);
					showPic.setVisibility(View.VISIBLE);
					playFailSound();
				}

				okBtn.setVisibility(View.INVISIBLE);
				nextBtn.setVisibility(View.VISIBLE);
				nextBtn.setEnabled(true);

				break;
			case R.id.test2a_page_next_btn:
				nextBtn.setEnabled(false);
				if (noOfPages > (getCurrentPageNo() + 1)) {
					setCurrentPageNo(getCurrentPageNo() + 1);
					pageHandling(getCurrentPageNo() + 1, noOfPages);
					test.setText(getCurrentPageNo() + 1 + "/" + noOfPages);
					getAfterPageSelectionHandler().afterPageSelectionProcess(
							getCurrentPageNo());

				} else {
					SaveChallengeOneRecord saveChallengeOneRecord = new SaveChallengeOneRecord(
							L2aTestFragementActivity.this);
					saveChallengeOneRecord.todayDone();
					showFinishMessageDialog();
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
		builder.setMessage("這是最後一頁").setTitle("第二階段學習模式");
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

	private ImageButton getInputAnswerBtn(int position) {
		switch (position) {
		case 1:
			return inputAnswerBtn1;
		case 2:
			return inputAnswerBtn2;
		case 3:
			return inputAnswerBtn3;
		case 4:
			return inputAnswerBtn4;
		case 5:
			return inputAnswerBtn5;
		case 6:
			return inputAnswerBtn6;
		case 7:
			return inputAnswerBtn7;
		case 8:
			return inputAnswerBtn8;
		case 9:
			return inputAnswerBtn9;
		case 10:
			return inputAnswerBtn10;
		}
		return null;

	}

	private ImageButton getNormalBtn(int position) {
		switch (position) {
		case 1:
			return normalBtn1;
		case 2:
			return normalBtn2;
		case 3:
			return normalBtn3;
		case 4:
			return normalBtn4;
		case 5:
			return normalBtn5;
		case 6:
			return normalBtn6;
		case 7:
			return normalBtn7;
		case 8:
			return normalBtn8;
		case 9:
			return normalBtn9;
		case 10:
			return normalBtn10;
		}
		return null;

	}

	private ImageButton getFalseAnswerBtn(int position) {
		switch (position) {
		case 1:
			return falseAnswerBtn1;
		case 2:
			return falseAnswerBtn2;
		case 3:
			return falseAnswerBtn3;
		case 4:
			return falseAnswerBtn4;
		case 5:
			return falseAnswerBtn5;
		case 6:
			return falseAnswerBtn6;
		case 7:
			return falseAnswerBtn7;
		case 8:
			return falseAnswerBtn8;
		case 9:
			return falseAnswerBtn9;
		case 10:
			return falseAnswerBtn10;
		}
		return null;

	}

	private ImageButton getTrueAnswerBtn(int position) {
		switch (position) {
		case 1:
			return trueAnswerBtn1;
		case 2:
			return trueAnswerBtn2;
		case 3:
			return trueAnswerBtn3;
		case 4:
			return trueAnswerBtn4;
		case 5:
			return trueAnswerBtn5;
		case 6:
			return trueAnswerBtn6;
		case 7:
			return trueAnswerBtn7;
		case 8:
			return trueAnswerBtn8;
		case 9:
			return trueAnswerBtn9;
		case 10:
			return trueAnswerBtn10;
		}
		return null;

	}

	private String getCorrectAnswer(int position) {
		Stage2Conson stage2conson = getOnePageData(currentPageNo);
		switch (position) {
		case 1:
			return stage2conson.getWord1();
		case 2:
			return stage2conson.getWord2();
		case 3:
			return stage2conson.getWord3();
		case 4:
			return stage2conson.getWord4();
		case 5:
			return stage2conson.getWord5();
		case 6:
			return stage2conson.getWord6();
		case 7:
			return stage2conson.getWord7();
		case 8:
			return stage2conson.getWord8();
		case 9:
			return stage2conson.getWord9();
		case 10:
			return stage2conson.getWord10();
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
				Stage2Conson stage2conson = getOnePageData(pageno);
				String savePath = AuditoryDirectoryHelper
						.ObtainStage2SavePath(stage2conson.getConson());

				// Drawable d = Drawable.createFromPath(savePath + "/" +
				// stage2conson.getPicture());
				// Bitmap bmp = BitmapFactory.decodeFile(savePath + "/" +
				// stage2conson.getPicture());

				File dest = new File(savePath, stage2conson.getPicture());
				FileInputStream fis;
				picture = null;
				try {
					fis = new FileInputStream(dest);
					picture = BitmapFactory.decodeStream(fis);
					if (picture != null)
						break;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			L2aTestFragementActivity.this
					.runOnUiThread(new ShowUIAfterLoadBmpFinish(pageno));
		}

	}

	private View getShowScreen() {
		return (View) findViewById(R.id.test2a_show_screen);
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
			indicatorOfRightAnswer.setVisibility(View.INVISIBLE);
			indicatorOfWrongAnswer.setVisibility(View.INVISIBLE);
			frameOfShowPic.setVisibility(View.VISIBLE);
			// showPic.setImageDrawable(d);
			showPic.setImageBitmap(picture);

			showPic.setVisibility(View.VISIBLE);
			nextBtn.setVisibility(View.INVISIBLE);
			okBtn.setVisibility(View.VISIBLE);

			Stage2Conson stage2conson = getOnePageData(pageno);
			String sentence = stage2conson.getSentence();
			for (int i = 0; i < sentence.length(); i++) {
				getSelectionBoxGroup(i + 1).setVisibility(View.VISIBLE);
				char x = sentence.charAt(i);
				TextView y = getTextSelectionBox(i + 1);
				y.setText(String.valueOf(x));
				getNormalBtn(i + 1).setVisibility(View.VISIBLE);
				getFalseAnswerBtn(i + 1).setVisibility(View.INVISIBLE);
				getTrueAnswerBtn(i + 1).setVisibility(View.INVISIBLE);
				getInputAnswerBtn(i + 1).setVisibility(View.INVISIBLE);
			}
			if (sentence.length() < 10) {
				for (int j = sentence.length() + 1; j <= 10; j++) {
					// getTextSelectionBox(j).setText("");
					getSelectionBoxGroup(j).setVisibility(View.INVISIBLE);
				}
			}
			// dialog.dismiss();

			// play sound
			playsound(pageno);

		}

	}

	private void playsound(int pageno) {
		enableNormalBtn(false);
		final Stage2Conson stage2conson = getOnePageData(pageno);
		final String savePath = AuditoryDirectoryHelper
				.ObtainStage2SavePath(stage2conson.getConson());
		L2aTestFragementActivity.this
				.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setSoundPool(new SoundPool(10, AudioManager.STREAM_MUSIC, 0));
		/*
		 * setSoundID(soundPool.load(savePath + "/" +
		 * stage2conson.getVoicechip(), 1));
		 */
		setSoundID(soundPool.load(
				savePath + "/" + stage2conson.getSentenceVoicechip(), 1));
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				setPlayer(new MediaPlayer());
				getPlayer().setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						enableNormalBtn(true);
						okBtn.setEnabled(true);
					}
				});
				boolean notready = false;
				do {
					try {
						notready = false;
						// getPlayer().setDataSource(savePath + "/" +
						// stage2conson.getVoicechip());
						getPlayer().setDataSource(
								savePath + "/"
										+ stage2conson.getSentenceVoicechip());
						getPlayer().prepare();
						getPlayer().start();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						notready = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} while (notready);
			}
		});
	}

	private void displayOnePage(int pageno) {

		enableNormalBtn(false);
		okBtn.setEnabled(false);
		inputAnswerInit();
		// showProgressDialog();
		Thread b = new Thread(new LoadBmp(pageno));
		b.start();

	}

	private Stage2Conson getOnePageData(int pageno) {
		ArrayList<Stage2Conson> locals = (ArrayList<Stage2Conson>) getLocals();

		return locals.get(pageno);
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

	public List<Stage2Conson> getLocals() {
		return locals;
	}

	public void setLocals(List<Stage2Conson> locals) {
		this.locals = locals;
	}

	private void syncData() {
		AppDataSource appDataSource = AppDataSource.getInstance();
		appDataSource.getStage2Conson(this, this, new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// getDataFromLocalDatabaseAndStartTheGame();

			}

		}, String.valueOf(getConson()));

	}

	private void getDataFromLocalDatabaseAndStartTheGame() {
		GetDataFromLocalDatabase<Stage2Conson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<Stage2Conson>(
				L2aTestFragementActivity.this, Stage2Conson.class);
		ArrayList<Stage2Conson> locals = getDataFromLocalDatabase
				.getDataFromLocalDatabase(getConson());
		setLocals(locals);
		L2aTestFragementActivity.this.runOnUiThread(new DisplayText());
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

	private TextView getTextSelectionBox(int position) {
		switch (position) {
		case 1:
			return InputBox1;

		case 2:
			return InputBox2;

		case 3:
			return InputBox3;

		case 4:
			return InputBox4;

		case 5:
			return InputBox5;

		case 6:
			return InputBox6;

		case 7:
			return InputBox7;

		case 8:
			return InputBox8;

		case 9:
			return InputBox9;

		case 10:
			return InputBox10;
		}
		return null;
	}

	private View getSelectionBoxGroup(int position) {
		switch (position) {
		case 1:

			return boxGroup1;

		case 2:
			return boxGroup2;

		case 3:
			return boxGroup3;

		case 4:
			return boxGroup4;

		case 5:
			return boxGroup5;

		case 6:
			return boxGroup6;

		case 7:
			return boxGroup7;

		case 8:
			return boxGroup8;

		case 9:
			return boxGroup9;

		case 10:
			return boxGroup10;
		}
		return null;
	}

	public boolean isPicFileReady() {
		return picFileReady;
	}

	public void setPicFileReady(boolean picFileReady) {
		this.picFileReady = picFileReady;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	private void stopSound() {
		if (getSoundPool() != null)
			getSoundPool().stop(getSoundID());
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		stopSound();
		if (getPlayer() != null)
			getPlayer().stop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopSound();
		if (getPlayer() != null)
			getPlayer().stop();
	}

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

	private void enableNormalBtn(boolean enable) {
		for (int i = 1; i <= 10; i++) {
			getNormalBtn(i).setEnabled(enable);
		}
	}

	private class AfterFinishDownloadAction implements
			DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
			DownloadTrace.getInstance().addOneDownloadsComplete();
			L2aTestFragementActivity.this
					.runOnUiThread(new ShowDownloadProgress());
			L2aTestFragementActivity.this.runOnUiThread(new DismissDialog());
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			DownloadTrace.getInstance().addOneDownload();
			L2aTestFragementActivity.this
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
				L2aTestFragementActivity.setFirstTime(getConson());
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
			downloadService.removeUpdateReceiver(null);
			downloadService = null;
		}
	};

	private void bindService() {

		bindService(new Intent(this, DownloadService.class), mConnection,
				BIND_AUTO_CREATE);

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

	private TextView downloadProgressTxt;

	public TextView getDownloadProgressTxt() {
		return downloadProgressTxt;
	}

	public void setDownloadProgressTxt(TextView downloadProgressTxt) {
		this.downloadProgressTxt = downloadProgressTxt;
	}

	private void showDownloadProgressDialog() {

		downloadProgressDialog.show();

	}

	private Dialog downloadProgressDialog;

	private void saveIsFirstDataToDatabase() {
		GetDataFromLocalDatabase<StageTwoIsFirst> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageTwoIsFirst>(
				this, StageTwoIsFirst.class);
		ProcessIsFirstData<StageTwoIsFirst> processIsFirstData = new ProcessIsFirstData<StageTwoIsFirst>(
				getDataFromLocalDatabase);
		processIsFirstData.saveDataToLocalDataBase(new StageTwoIsFirst());
	}

	private void getIsFirstDataFromDataAndSetup() {
		GetDataFromLocalDatabase<StageTwoIsFirst> getDataFromLocalDatabase1 = new GetDataFromLocalDatabase<StageTwoIsFirst>(
				this, StageTwoIsFirst.class);
		ProcessIsFirstData<StageTwoIsFirst> processIsFirstData1 = new ProcessIsFirstData<StageTwoIsFirst>(
				getDataFromLocalDatabase1);
		processIsFirstData1.getDataFromDataBase();

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
					}
					catch (IllegalArgumentException e){
						
					}
					Intent intent = new Intent(this, DownloadService.class);
					stopService(intent);
					
				}
			}
		}
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
}
