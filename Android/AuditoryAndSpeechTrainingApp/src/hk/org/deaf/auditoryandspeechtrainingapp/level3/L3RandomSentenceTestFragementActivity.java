package hk.org.deaf.auditoryandspeechtrainingapp.level3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import hk.org.deaf.auditoryandspeechtrainingapp.CommonFunctionsForTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFiveResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSixResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.Stage2Conson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.result.L3ResultFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDataSource;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.AuditoryDirectoryHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ChallengeResultProcessing;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ContextStorage;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadTrace;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.GetDataFromLocalDatabase;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PlaySoundForPassOrFail;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ProcessIsFirstData;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService.DownloadProgressUpdateReceiver;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
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

public class L3RandomSentenceTestFragementActivity extends
		CommonFunctionsForTestFragmentActivity {

	private ImageView showPic;
	private ImageView frameOfShowPic;
	private ImageView indicatorOfRightAnswer;
	private ImageView indicatorOfWrongAnswer;

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

	private List<Stage2Conson> locals0;
	private List<Stage2Conson> locals1;
	private List<Stage2Conson> locals2;
	private List<Stage2Conson> locals3;
	private List<Stage2Conson> locals4;
	private List<Stage2Conson> locals5;
	private List<Stage2Conson> locals6;

	private int conson;
	private ImageButton okBtn;
	private ImageButton nextBtn;
	private String[] inputAnwser = new String[10];
	private boolean[] result = new boolean[10];
	private int currentPageNo;
	private List<Stage2Conson> locals;
	private Bitmap picture;
	private ProgressDialog dialog;
	private int noOfPages;
	private TextView counter;
	private boolean readyToOtherPage = false;
	private int totalScoreOfTest = 0;
	private Thread counterThread;
	private Counter counterObj;
	private MediaPlayer player;
	private int noOfQuestion;
	private int currentConson;
	private static Hashtable<Integer, Boolean[]> usedPages;
	private InitSavedPages initSavedPages;

	public InitSavedPages getInitSavedPages() {
		return initSavedPages;
	}

	private int noOfPageAnswered;
	private int debugCounter = 0;

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

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level2b_test_revised);
		setContentLayout();
		ContextStorage.getInstance().setContext(getApplicationContext());
		setSelectionBtnLayout();
		setNoOfPageAnswered(0);
		setUpDownloadProgressDialog();
		setInputAnswerUILayout();
		// setCurrentPageNo(0);
		inputAnswerInit();
		getIsFirstDataFromDataAndSetup();
		setupDownloadDialog();
		showDownloadDialog();

	}

	/*
	 * private void getConsonFromIntent() { int conson =
	 * getIntent().getIntExtra("conson", 0); Log.d("conson",
	 * String.valueOf(conson)); setConson(conson); }
	 */

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
	}

	private ImageView imgIndicator;

	private void setContentLayout() {
		ImageButton backBtn = (ImageButton) findViewById(R.id.test_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());

		// xmlLayoutConnection(R.drawable.box_menu, R.layout.level1a_test,
		// R.drawable.bg_test);
		Display display = getWindowManager().getDefaultDisplay();
		// int SCREEN_WIDTH = display.getWidth();
		int SCREEN_HEIGHT = display.getHeight();

		counter = (TextView) findViewById(R.id.test_level_2b_timer_txt);

		/*
		 * consonTxtndicator = (TextView)
		 * findViewById(R.id.test_level_2b_indicator_txt);
		 * consonTxtndicator.setText(getConsonString(getCurrentConson()));
		 */
		imgIndicator = (ImageView) findViewById(R.id.test_level_2b_indicator);
		imgIndicator.setImageResource(L2aTestFragementActivity
				.getConsonDrawable(getCurrentConson()));

		int sizeForCounter = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);
		// int sizeForConsonTxtndicator = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 /
		// 2);
		counter.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForCounter);
		/*
		 * consonTxtndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX,
		 * sizeForConsonTxtndicator);
		 */

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

	public List<Stage2Conson> getLocals() {
		return locals;
	}

	public void setLocals(List<Stage2Conson> locals) {
		this.locals = locals;
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

	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	/*
	 * private void syncData() { AppDataSource appDataSource =
	 * AppDataSource.getInstance(); showProgressDialog();
	 * appDataSource.getStageOneConson(this, this, new AfterSyncDataAction0(0),
	 * "0"); }
	 */

	/*
	 * private class AfterSyncDataAction0 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction0(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabase(this.conson); AppDataSource appDataSource =
	 * AppDataSource.getInstance(); appDataSource.getStage2Conson(
	 * L3RandomSentenceTestFragementActivity.this,
	 * L3RandomSentenceTestFragementActivity.this, new AfterSyncDataAction1(1),
	 * "1");
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction1 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction1(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabase(this.conson); AppDataSource appDataSource =
	 * AppDataSource.getInstance(); appDataSource.getStage2Conson(
	 * L3RandomSentenceTestFragementActivity.this,
	 * L3RandomSentenceTestFragementActivity.this, new AfterSyncDataAction2(2),
	 * "2"); // getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction2 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction2(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabase(this.conson); AppDataSource appDataSource =
	 * AppDataSource.getInstance(); appDataSource.getStage2Conson(
	 * L3RandomSentenceTestFragementActivity.this,
	 * L3RandomSentenceTestFragementActivity.this, new AfterSyncDataAction3(3),
	 * "3"); // getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction3 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction3(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabase(this.conson); AppDataSource appDataSource =
	 * AppDataSource.getInstance(); appDataSource.getStage2Conson(
	 * L3RandomSentenceTestFragementActivity.this,
	 * L3RandomSentenceTestFragementActivity.this, new AfterSyncDataAction4(4),
	 * "4"); // getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction4 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction4(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabase(this.conson); AppDataSource appDataSource =
	 * AppDataSource.getInstance(); appDataSource.getStage2Conson(
	 * L3RandomSentenceTestFragementActivity.this,
	 * L3RandomSentenceTestFragementActivity.this, new AfterSyncDataAction5(5),
	 * "5"); // getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction5 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction5(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabase(this.conson); AppDataSource appDataSource =
	 * AppDataSource.getInstance(); appDataSource.getStage2Conson(
	 * L3RandomSentenceTestFragementActivity.this,
	 * L3RandomSentenceTestFragementActivity.this, new AfterSyncDataAction6(6),
	 * "6"); // getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction6 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction6(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * dialog.dismiss(); Log.d("download123", "download1123 with conson = " +
	 * this.conson); L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabase(this.conson);
	 * getAllConsonsDataFromLocalDatabase(); processNoOfQuestionTheGamePlay();
	 * processOnePage();
	 * 
	 * }
	 * 
	 * }
	 */

	private int getNoOfQuestionFromIntent() {
		return getIntent().getIntExtra("no_of_pages", 0);
	}

	private void processNoOfQuestionTheGamePlay() {
		int noOfPages = getNoOfQuestionFromIntent();
		if (noOfPages > getNoOfPagesOfAllConson()) {
			setNoOfQuestion(getNoOfPagesOfAllConson() / 2);
			// setNoOfQuestion(4);
		} else if (noOfPages == 0) {
			setNoOfQuestion(5);
		} else {
			setNoOfQuestion(noOfPages);
			// setNoOfQuestion(5);
		}
	}

	private void processOnePage() {
		setInitSavedPages(new InitSavedPages());
		setOnePage();
		// setAnswer();
		displayOnePage(getCurrentPageNo());
	}

	public void setInitSavedPages(InitSavedPages initSavedPages) {
		this.initSavedPages = initSavedPages;
	}

	private void showProgressDialog() {
		// dialog = new ProgressDialog(this);
		dialog = ProgressDialog.show(this, null, null, true);

		/*
		 * dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
		 * public void onClick(DialogInterface dialog, int which) { // Use
		 * either finish() or return() to either close the activity or just the
		 * dialog finish(); return; } }); dialog.show();
		 */
	}

	public void inputAnswerInit() {
		for (int i = 0; i < 10; i++) {
			inputAnwser[i] = "0";
		}
	}

	private void displayOnePage(int pageno) {

		enableNormalBtn(false);
		okBtn.setEnabled(false);
		inputAnswerInit();
		// showProgressDialog();
		Thread b = new Thread(new LoadBmp(pageno));
		b.start();

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

	private void setSelectionBtnLayout() {

	}

	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.test_page_back_btn) {
				finish();
			}
		}

	}

	private String getCorrectAnswer(int position) {
		Stage2Conson stage2conson = getOnePageData(getCurrentConson(),
				getCurrentPageNo());
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

	private Stage2Conson getOnePageData(int conson, int pageNo) {
		List<Stage2Conson> locals = getLocals(conson);
		return locals.get(pageNo);

	}

	/*
	 * private Stage2Conson getOnePageData(int pageno) { ArrayList<Stage2Conson>
	 * locals = (ArrayList<Stage2Conson>) getLocals();
	 * 
	 * return locals.get(pageno); }
	 */

	private class ClickBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.test_page_back_btn:
				finish();
				break;
			case R.id.test2a_page_repeat_btn:
				playsound(L3RandomSentenceTestFragementActivity.this.currentPageNo);
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
				if (counterThread != null)
					counterThread.interrupt();
				// L2bTestFragementActivity.this.runOnUiThread(new
				// GotoNextPage());
				nextBtn.setEnabled(true);
				break;
			case R.id.test2a_page_next_btn:
				nextBtn.setEnabled(false);
				setNoOfPageAnswered(getNoOfPageAnswered() + 1);

				if (getNoOfPageAnswered() < noOfQuestion) {

					Log.d("goto next page", "goto next page and count = "
							+ debugCounter);
					debugCounter++;
					L3RandomSentenceTestFragementActivity.this
							.runOnUiThread(new GotoNextPage());
				} else {
					Log.d("the test is finished", "the test is finished");
					// send score to the remote server

					if (getNoOfQuestionFromIntent() == 10) {
						if (getTotalScoreOfTest() == noOfQuestion) {
							ChallengeResultProcessing<ChallengeFiveResult> processing = new ChallengeResultProcessing<ChallengeFiveResult>(
									L3RandomSentenceTestFragementActivity.this,
									ChallengeFiveResult.class);
							if (processing.getChallengeResult() != null) {
								processing.saveChallengeResultToLocalDatabase(
										true, processing.getChallengeResult());
							} else {
								processing.saveChallengeResultToLocalDatabase(
										true, new ChallengeFiveResult());
							}
							// challenge2 is complete
						}
					} else if (getNoOfQuestionFromIntent() == 20) {
						if (getTotalScoreOfTest() == noOfQuestion) {
							ChallengeResultProcessing<ChallengeSixResult> processing = new ChallengeResultProcessing<ChallengeSixResult>(
									L3RandomSentenceTestFragementActivity.this,
									ChallengeSixResult.class);
							if (processing.getChallengeResult() != null) {
								processing.saveChallengeResultToLocalDatabase(
										true, processing.getChallengeResult());
							} else {
								processing.saveChallengeResultToLocalDatabase(
										true, new ChallengeSixResult());
							}
							// challenge2 is complete
						}
					} /*
					 * else { if (getTotalScoreOfTest() == noOfQuestion) {
					 * ChallengeFiveResult challengeFiveResult = new
					 * ChallengeFiveResult();
					 * challengeFiveResult.setResultCompleted(true);
					 * challengeFiveResult.setId("1"); AppDatabaseHelper helper
					 * = (AppDatabaseHelper) OpenHelperManager .getHelper(
					 * L3RandomSentenceTestFragementActivity.this,
					 * AppDatabaseHelper.class); try { Dao<ChallengeFiveResult,
					 * String> dao = helper .getDao(ChallengeFiveResult.class);
					 * dao.createOrUpdate(challengeFiveResult);
					 * 
					 * } catch (SQLException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); } // challenge2 is complete }
					 * }
					 */

					Intent intent0 = new Intent(
							L3RandomSentenceTestFragementActivity.this,
							L3ResultFragementActivity.class);
					// intent0.putExtra("conson", getConson());
					startActivity(intent0);
					finish();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void playsound(int pageno) {
		final Stage2Conson stage2conson = getOnePageData(getCurrentConson(),
				getCurrentPageNo());
		;
		final String savePath = AuditoryDirectoryHelper
				.ObtainStage2SavePath(stage2conson.getConson());
		L3RandomSentenceTestFragementActivity.this
				.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		@SuppressWarnings("unused")
		final int soundID = soundPool.load(
				savePath + "/" + stage2conson.getSentenceVoicechip(), 1);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				setPlayer(new MediaPlayer());
				getPlayer().setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						startCounter(counter);
						enableNormalBtn(true);
						okBtn.setEnabled(true);
					}
				});
				boolean notready = false;
				do {
					try {

						notready = false;
						getPlayer().setDataSource(
								savePath + "/"
										+ stage2conson.getSentenceVoicechip());
						getPlayer().prepare();
						getPlayer().start();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						notready = true;
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} while (notready);
			}
		});

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
				Stage2Conson stage2conson = getOnePageData(getCurrentConson(),
						getCurrentPageNo());
				;
				String savePath = AuditoryDirectoryHelper
						.ObtainStage2SavePath(stage2conson.getConson());

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
					// e.printStackTrace();
				}
			}
			L3RandomSentenceTestFragementActivity.this
					.runOnUiThread(new ShowUIAfterLoadBmpFinish(pageno));
		}

	}

	private View getShowScreen() {
		return (View) findViewById(R.id.test2b_show_screen);
	}

	private class ShowUIAfterLoadBmpFinish implements Runnable {
		private int pageno;

		public ShowUIAfterLoadBmpFinish(int pageno) {
			this.pageno = pageno;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			imgIndicator.setImageResource(L2aTestFragementActivity
					.getConsonDrawable(getCurrentConson()));

			getShowScreen().setVisibility(View.VISIBLE);
			indicatorOfRightAnswer.setVisibility(View.INVISIBLE);
			indicatorOfWrongAnswer.setVisibility(View.INVISIBLE);
			frameOfShowPic.setVisibility(View.VISIBLE);
			// showPic.setImageDrawable(d);
			showPic.setImageBitmap(picture);

			showPic.setVisibility(View.VISIBLE);
			nextBtn.setVisibility(View.INVISIBLE);
			okBtn.setVisibility(View.VISIBLE);

			Stage2Conson stage2conson = getOnePageData(getCurrentConson(),
					getCurrentPageNo());
			;
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

	/*
	 * private void getDataFromLocalDatabaseAndStartTheGame(){
	 * GetDataFromLocalDatabase<Stage2Conson> getDataFromLocalDatabase = new
	 * GetDataFromLocalDatabase<Stage2Conson>(
	 * L3RandomSentenceTestFragementActivity.this, Stage2Conson.class);
	 * ArrayList<Stage2Conson> locals = getDataFromLocalDatabase
	 * .getDataFromLocalDatabase(getConson()); setLocals(locals);
	 * setNoOfPages(); setCurrentPageNo(0); displayOnePage(0); }
	 */

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	private void startCounter(TextView tv) {
		setCounterObj(new Counter(tv));
		counterThread = new Thread(getCounterObj());
		counterThread.start();
	}

	public boolean isReadyToOtherPage() {
		return readyToOtherPage;
	}

	public void setReadyToOtherPage(boolean readyToOtherPage) {
		this.readyToOtherPage = readyToOtherPage;
	}

	public class Counter implements Runnable {
		TextView tv;
		private boolean exit = false;

		Counter(TextView tv) {
			this.tv = tv;
		}

		public boolean isExit() {
			return exit;
		}

		public void setExit(boolean exit) {
			this.exit = exit;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			int counterValue = 3;
			while (true) {
				try {
					if (counterValue == -1)
						break;
					L3RandomSentenceTestFragementActivity.this
							.runOnUiThread(new CounterDisplay(this.tv, String
									.valueOf(counterValue)));
					Log.d("counter=", String.valueOf(counterValue));
					Thread.sleep(1000);
					counterValue -= 1;

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if (this.isExit()) {
						return;
					} else {
						break;
					}
				}
			}
			L3RandomSentenceTestFragementActivity.this
					.runOnUiThread(new TimesUpShowResult());

		}

	}

	private class TimesUpShowResult implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			okBtn.setVisibility(View.INVISIBLE);
			nextBtn.setVisibility(View.VISIBLE);
			nextBtn.setEnabled(true);
			enableNormalBtn(false);

			// if (counterThread != null) counterThread.interrupt();
			// L2bTestFragementActivity.this.runOnUiThread(new GotoNextPage());
			Stage2Conson stage2conson = getOnePageData(getConson(),
					getCurrentPageNo());
			int sentenceLength = stage2conson.getSentence().length();
			for (int i = 0; i < sentenceLength; i++) {
				if (getCorrectAnswer(i + 1) != null) {
					Log.d("getCorrectAnswer", getCorrectAnswer(i + 1));
					if (inputAnwser[i].equals(getCorrectAnswer(i + 1))) {
						result[i] = true;

					} else {
						result[i] = false;
						getInputAnswerBtn(i + 1).setVisibility(View.INVISIBLE);
						getFalseAnswerBtn(i + 1).setVisibility(View.VISIBLE);
						getNormalBtn(i + 1).setVisibility(View.INVISIBLE);
						getTrueAnswerBtn(i + 1).setVisibility(View.INVISIBLE);
					}
					if (getCorrectAnswer(i + 1).equals("1")) {
						getInputAnswerBtn(i + 1).setVisibility(View.INVISIBLE);
						getFalseAnswerBtn(i + 1).setVisibility(View.INVISIBLE);
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

			setTotalScoreOfTest(getUserInputAnwserResultForOnePage());
		}

	}

	public int getNoOfPageAnswered() {
		return noOfPageAnswered;
	}

	public void setNoOfPageAnswered(int noOfPageAnswered) {
		this.noOfPageAnswered = noOfPageAnswered;
	}

	private class CounterDisplay implements Runnable {
		TextView tv;
		String counterValue;

		CounterDisplay(TextView tv, String counterValue) {
			this.tv = tv;
			this.counterValue = counterValue;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			tv.setText(this.counterValue);

		}

	}

	private class GotoNextPage implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			counter.setText("3");
			setOnePage();
			displayOnePage(getCurrentPageNo());
		}

	}

	private int getUserInputAnwserResultForOnePage() {
		Stage2Conson stage2conson = getOnePageData(getCurrentConson(),
				getCurrentPageNo());
		;
		int sentenceLength = stage2conson.getSentence().length();
		for (int i = 0; i < sentenceLength; i++) {
			if (getCorrectAnswer(i + 1) != null) {
				Log.d("getCorrectAnswer", getCorrectAnswer(i + 1));
				if (inputAnwser[i].equals(getCorrectAnswer(i + 1))) {
					result[i] = true;

				} else {
					result[i] = false;

				}

			}
		}
		boolean allcorrect = true;

		for (int i = 0; i < sentenceLength; i++) {
			allcorrect &= result[i];

		}

		if (allcorrect) {
			return 1;
		} else {
			return 0;
		}

	}

	public int getTotalScoreOfTest() {
		return totalScoreOfTest;
	}

	public void setTotalScoreOfTest(int totalScoreOfTest) {
		this.totalScoreOfTest += totalScoreOfTest;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		stopCountThread();
		if (getPlayer() != null)
			getPlayer().stop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		stopCountThread();
		if (getPlayer() != null)
			getPlayer().stop();
	}
	
	private void stopCountThread() {
		if (getCounterObj() != null)
			getCounterObj().setExit(true);
		if (counterThread != null)
			counterThread.interrupt();
	}

	public Counter getCounterObj() {
		return counterObj;
	}

	public void setCounterObj(Counter counterObj) {
		this.counterObj = counterObj;
	}

	private void enableNormalBtn(boolean enable) {
		for (int i = 1; i <= 10; i++) {
			getNormalBtn(i).setEnabled(enable);
		}
	}

	public int getNoOfQuestion() {
		return noOfQuestion;
	}

	public void setNoOfQuestion(int noOfQuestion) {
		this.noOfQuestion = noOfQuestion;
	}

	/*
	 * private void setOnePage() { ArrayList<Stage2Conson> locals; int pageNo =
	 * 0; int conson; Random consonRandam = new Random(); Random pageNoRandom =
	 * new Random(); do {
	 * 
	 * conson = consonRandam.nextInt(7);
	 * 
	 * locals = new ArrayList<Stage2Conson>( getDataFromLocalDatabase2(conson));
	 * do{ boolean b=true; for (int i=0; i<usedPages.get(conson).length;i++){
	 * Boolean[] a = usedPages.get(conson); b = b && a[i]; } if(b) break;
	 * 
	 * if (locals.size() > 1) {
	 * 
	 * pageNo = pageNoRandom.nextInt(locals.size() - 1); //
	 * setCurrentPageNo(getPageNoRandom().nextInt(locals.size()-1)); } else {
	 * pageNo = 0; }
	 * 
	 * 
	 * } while (usedPages.get(conson)[pageNo]); } while (locals.size() <= 0 ||
	 * locals == null || usedPages.get(conson)[pageNo]);
	 * usedPages.get(conson)[pageNo] = true; setCurrentConson(conson);
	 * setCurrentPageNo(pageNo);
	 * 
	 * }
	 */

	private void setOnePage() {
		ArrayList<Stage2Conson> locals;
		int pageNo = 0;
		int conson;
		do {
			Random consonRandam = new Random();
			conson = consonRandam.nextInt(9);

			locals = new ArrayList<Stage2Conson>(
					getDataFromLocalDatabase2(conson));
			if (locals.size() > 1) {
				Random pageNoRandom = new Random();
				pageNo = pageNoRandom.nextInt(locals.size() - 1);
				// setCurrentPageNo(getPageNoRandom().nextInt(locals.size()-1));
			} else {
				pageNo = 0;
			}
		} while (locals.size() <= 0 || locals == null
				|| usedPages.get(conson)[pageNo]);
		usedPages.get(conson)[pageNo] = true;
		setCurrentConson(conson);
		setCurrentPageNo(pageNo);

	}

	public int getCurrentConson() {
		return currentConson;
	}

	public void setCurrentConson(int currentConson) {
		this.currentConson = currentConson;
	}

	private ArrayList<Stage2Conson> getDataFromLocalDatabase2(int conson) {
		GetDataFromLocalDatabase<Stage2Conson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<Stage2Conson>(
				L3RandomSentenceTestFragementActivity.this, Stage2Conson.class);
		ArrayList<Stage2Conson> locals = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		return locals;
		// setNoOfPages();
		// setCurrentPageNo(0);
		// displayOnePage(0);
	}

	private class InitSavedPages {

		public InitSavedPages() {
			usedPages = new Hashtable<Integer, Boolean[]>();
			Boolean[] a0 = new Boolean[getLocals(0).size()];
			Boolean[] a1 = new Boolean[getLocals(1).size()];
			Boolean[] a2 = new Boolean[getLocals(2).size()];
			Boolean[] a3 = new Boolean[getLocals(3).size()];
			Boolean[] a4 = new Boolean[getLocals(4).size()];
			Boolean[] a5 = new Boolean[getLocals(5).size()];
			Boolean[] a6 = new Boolean[getLocals(6).size()];
			initArray(a0);
			initArray(a1);
			initArray(a2);
			initArray(a3);
			initArray(a4);
			initArray(a5);
			initArray(a6);
			usedPages.put(0, a0);
			usedPages.put(1, a1);
			usedPages.put(2, a2);
			usedPages.put(3, a3);
			usedPages.put(4, a4);
			usedPages.put(5, a5);
			usedPages.put(6, a6);
		}

		private void initArray(Boolean[] x) {
			for (int i = 0; i < x.length; i++) {
				x[i] = false;
			}
		}
	}

	public List<Stage2Conson> getLocals(int conson) {
		switch (conson) {
		case 0:
			return locals0;
		case 1:
			return locals1;
		case 2:
			return locals2;
		case 3:
			return locals3;
		case 4:
			return locals4;
		case 5:
			return locals5;
		case 6:
			return locals6;
		}
		return null;
	}

	public void setLocals(int conson, List<Stage2Conson> locals) {
		switch (conson) {
		case 0:
			this.locals0 = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 1:
			this.locals1 = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 2:
			this.locals2 = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 3:
			this.locals3 = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 4:
			this.locals4 = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 5:
			this.locals5 = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 6:
			this.locals6 = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;

		}
	}

	private void getDataFromLocalDatabase(int conson) {
		GetDataFromLocalDatabase<Stage2Conson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<Stage2Conson>(
				L3RandomSentenceTestFragementActivity.this, Stage2Conson.class);
		ArrayList<Stage2Conson> locals = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		setLocals(conson, locals);
		// setNoOfPages();
		// setCurrentPageNo(0);
		// displayOnePage(0);
	}

	private void getAllConsonsDataFromLocalDatabase() {
		for (int i = 0; i < 7; i++) {
			getDataFromLocalDatabase(i);
		}
		setNoOfPages();
		Log.d("noOfPages = ", String.valueOf(getNoOfPagesOfAllConson()));
	}

	public void setNoOfPages() {
		this.noOfPages = 0;
		for (int i = 0; i < 7; i++) {
			this.noOfPages += getLocals(i).size();
		}
	}

	public int getNoOfPagesOfAllConson() {
		return noOfPages;
	}

	private class AfterFinishDownloadAction implements
			DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
			DownloadTrace.getInstance().addOneDownloadsComplete();
			L3RandomSentenceTestFragementActivity.this
					.runOnUiThread(new ShowDownloadProgress());
			L3RandomSentenceTestFragementActivity.this
					.runOnUiThread(new DismissDialog());
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			DownloadTrace.getInstance().addOneDownload();
			L3RandomSentenceTestFragementActivity.this
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
				L2aTestFragementActivity
						.setFirstTime(getCurrentConsonOfNewDownloadMethod());
				saveIsFirstDataToDatabase();
				setCurrentConsonOfNewDownloadMethod(getCurrentConsonOfNewDownloadMethod() + 1);
				if (noOfConsonOfDownload > getCurrentConsonOfNewDownloadMethod()) {
					DownloadTrace.getInstance().setNoOfDownloads(0);
					DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
					syncDataForNewDownloadMethod();
				} else {
					downloadProgressDialog.dismiss();
					getAllConsonsDataFromLocalDatabase();
					processNoOfQuestionTheGamePlay();
					processOnePage();
				}
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

	public TextView getDownloadProgressTxt() {
		return downloadProgressTxt;
	}

	public void setDownloadProgressTxt(TextView downloadProgressTxt) {
		this.downloadProgressTxt = downloadProgressTxt;
	}

	private TextView downloadProgressTxt;
	private Dialog downloadProgressDialog;
	private int currentConsonOfNewDownloadMethod;

	public int getNoOfConsonOfDownload() {
		return noOfConsonOfDownload;
	}

	public void setNoOfConsonOfDownload(int noOfConsonOfDownload) {
		this.noOfConsonOfDownload = noOfConsonOfDownload;
	}

	private int noOfConsonOfDownload;

	private void showDownloadProgressDialog() {

		downloadProgressDialog.show();

	}

	public int getCurrentConsonOfNewDownloadMethod() {
		return currentConsonOfNewDownloadMethod;
	}

	public void setCurrentConsonOfNewDownloadMethod(
			int currentConsonOfNewDownloadMethod) {
		this.currentConsonOfNewDownloadMethod = currentConsonOfNewDownloadMethod;
	}

	private boolean downloadOnlyRemainingFlag;

	public boolean isDownloadOnlyRemainingFlag() {
		return downloadOnlyRemainingFlag;
	}

	public void setDownloadOnlyRemainingFlag(boolean downloadOnlyRemainingFlag) {
		this.downloadOnlyRemainingFlag = downloadOnlyRemainingFlag;
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

	@SuppressWarnings("unused")
	private void syncDataForNewDownloadMethod() {
		if (isDownloadFlap()) {
			if (false){
			//if (isDownloadOnlyRemainingFlag()) {
				while (L2aTestFragementActivity
						.isFirstTime(getCurrentConsonOfNewDownloadMethod()) == false
						&& (getNoOfConsonOfDownload() > getCurrentConsonOfNewDownloadMethod())) {
					setCurrentConsonOfNewDownloadMethod(getCurrentConsonOfNewDownloadMethod() + 1);
				}
				if (L2aTestFragementActivity
						.isFirstTime(getCurrentConsonOfNewDownloadMethod())
						&& (getNoOfConsonOfDownload() > getCurrentConsonOfNewDownloadMethod())) {
					AppDataSource appDataSource = AppDataSource.getInstance();
					appDataSource.getStage2Conson(this, this, new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

						}

					}, String.valueOf(getCurrentConsonOfNewDownloadMethod()));
				} else {
					if (getNoOfConsonOfDownload() == getCurrentConsonOfNewDownloadMethod()) {
						downloadProgressDialog.dismiss();
						getAllConsonsDataFromLocalDatabase();
						processNoOfQuestionTheGamePlay();
						processOnePage();
					}

				}
			} else {
				AppDataSource appDataSource = AppDataSource.getInstance();
				appDataSource.getStage2Conson(this, this, new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

					}

				}, String.valueOf(getCurrentConsonOfNewDownloadMethod()));
			}
		}
	}

	private Dialog downloadDialog;

	private void setupDownloadDialog() {

		downloadDialog = new Dialog(this);
		downloadDialog.setContentView(R.layout.test3_syncdialog_ui);
		Button remainingBtn = (Button) downloadDialog
				.findViewById(R.id.test2a_sync_dialog_remaining_btn);
		remainingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				downloadDialog.dismiss();
				setNoOfConsonOfDownload(7);
				setCurrentConsonOfNewDownloadMethod(0);
				setDownloadOnlyRemainingFlag(true);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				setDownloadFlap(true);
				showDownloadProgressDialog();
				bindService();
				syncDataForNewDownloadMethod();
			}
		});
		remainingBtn.setVisibility(View.GONE);
		Button cancelBtn = (Button) downloadDialog
				.findViewById(R.id.test2a_sync_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialog.dismiss();
				getAllConsonsDataFromLocalDatabase();
				processNoOfQuestionTheGamePlay();
				processOnePage();

			}
		});
		Button okBtn = (Button) downloadDialog
				.findViewById(R.id.test2a_sync_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialog.dismiss();
				setCurrentConsonOfNewDownloadMethod(0);
				setNoOfConsonOfDownload(7);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				setDownloadOnlyRemainingFlag(false);
				showDownloadProgressDialog();
				bindService();
				syncDataForNewDownloadMethod();
			}
		});
		if (L2aTestFragementActivity.isFirstTime(0)
				|| L2aTestFragementActivity.isFirstTime(1)
				|| L2aTestFragementActivity.isFirstTime(2)
				|| L2aTestFragementActivity.isFirstTime(3)
				|| L2aTestFragementActivity.isFirstTime(4)
				|| L2aTestFragementActivity.isFirstTime(5)
				|| L2aTestFragementActivity.isFirstTime(6)) {
			cancelBtn.setVisibility(View.GONE);
			if ((L2aTestFragementActivity.isFirstTime(0)
					&& L2aTestFragementActivity.isFirstTime(1)
					&& L2aTestFragementActivity.isFirstTime(2)
					&& L2aTestFragementActivity.isFirstTime(3)
					&& L2aTestFragementActivity.isFirstTime(4)
					&& L2aTestFragementActivity.isFirstTime(5) && L2aTestFragementActivity
						.isFirstTime(6))
					|| (!L2aTestFragementActivity.isFirstTime(0)
							&& !L2aTestFragementActivity.isFirstTime(1)
							&& !L2aTestFragementActivity.isFirstTime(2)
							&& !L2aTestFragementActivity.isFirstTime(3)
							&& !L2aTestFragementActivity.isFirstTime(4)
							&& !L2aTestFragementActivity.isFirstTime(5) && !L2aTestFragementActivity
								.isFirstTime(6))) {
				remainingBtn.setVisibility(View.GONE);
			}

		} else {
			cancelBtn.setVisibility(View.VISIBLE);
			remainingBtn.setVisibility(View.GONE);
		}

	}

	private void showDownloadDialog() {
		if (isOnline()) {
			if (L2aTestFragementActivity.isFirstTime(0)
					|| L2aTestFragementActivity.isFirstTime(1)
					|| L2aTestFragementActivity.isFirstTime(2)
					|| L2aTestFragementActivity.isFirstTime(3)
					|| L2aTestFragementActivity.isFirstTime(4)
					|| L2aTestFragementActivity.isFirstTime(5)
					|| L2aTestFragementActivity.isFirstTime(6)) {
				downloadDialog.show();
			} else {
				getAllConsonsDataFromLocalDatabase();
				processNoOfQuestionTheGamePlay();
				processOnePage();
			}
		} else {
			if (!(L2aTestFragementActivity.isFirstTime(0)
					|| L2aTestFragementActivity.isFirstTime(1)
					|| L2aTestFragementActivity.isFirstTime(2)
					|| L2aTestFragementActivity.isFirstTime(3)
					|| L2aTestFragementActivity.isFirstTime(4)
					|| L2aTestFragementActivity.isFirstTime(5) || L2aTestFragementActivity
						.isFirstTime(6))) {
				getAllConsonsDataFromLocalDatabase();
				processNoOfQuestionTheGamePlay();
				processOnePage();
			}
		}
	}

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
			setDownloadFlap(false);
			if (downloadService != null)
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

	public boolean isDownloadFlap() {
		return downloadFlap;
	}

	public void setDownloadFlap(boolean downloadFlap) {
		this.downloadFlap = downloadFlap;
	}

	private boolean downloadFlap = true;

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
