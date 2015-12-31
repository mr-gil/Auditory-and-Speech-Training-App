package hk.org.deaf.auditoryandspeechtrainingapp.level1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;

import hk.org.deaf.auditoryandspeechtrainingapp.CommonFunctionsForTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.model.PassToStage2StateRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.result.L1ResultFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDataSource;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.AuditoryDirectoryHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ContextStorage;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadTrace;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.GetDataFromLocalDatabase;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PMNameValuePair;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PlaySoundForPassOrFail;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ProcessIsFirstData;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ProcessPassToStage2StateRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService.DownloadProgressUpdateReceiver;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.UserLoginOrLogoutProcess;
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

public class L1bTestFragementActivity extends
		CommonFunctionsForTestFragmentActivity {
	private int conson;
	private int currentPageNo;
	private List<StageOneConson> locals;
	private int noOfPages;
	private Bitmap picture1, picture2;
	private SoundPool soundPool1;
	private int soundID1;
	private ProgressDialog dialog;
	private TextView counter;
	private Thread counterThread;
	private Counter counterObj;
	private int totalScoreOfTest = 0;
	private LoadBmp loadBmpProcess;
	private int answer;
	private Button inputAnswerBtn1, inputAnswerBtn2;

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

	public int getAnswer() {
		return answer;
	}

	public void setAnswer() {
		Random randomAnswer = new Random();
		int answer = randomAnswer.nextInt(2);
		this.answer = answer;
		Log.d("The Answer is ", String.valueOf(this.answer));
	}

	public int getTotalScoreOfTest() {
		return totalScoreOfTest;
	}

	public void setTotalScoreOfTest(int totalScoreOfTest) {
		this.totalScoreOfTest = totalScoreOfTest;
	}

	public Thread getCounterThread() {
		return counterThread;
	}

	public void setCounterThread(Thread counterThread) {
		this.counterThread = counterThread;
	}

	public TextView getCounter() {
		return counter;
	}

	public void setCounter(TextView counter) {
		this.counter = counter;
	}

	public SoundPool getSoundPool1() {
		return soundPool1;
	}

	public void setSoundPool1(SoundPool soundPool1) {
		this.soundPool1 = soundPool1;
	}

	public int getSoundID1() {
		return soundID1;
	}

	public void setSoundID1(int soundID1) {
		this.soundID1 = soundID1;
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

	private MediaPlayer player1, player2;

	public List<StageOneConson> getLocals() {
		return locals;
	}

	public void setLocals(List<StageOneConson> locals) {
		this.locals = locals;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level1b_test_revised);
		getConsonFromIntent();
		ContextStorage.getInstance().setContext(getApplicationContext());
		setCurrentPageNo(0);
		setUpDownloadProgressDialog();
		setContentLayout();
		setSelectionBtnLayout();
		getIsFirstDataFromDataAndSetup();
		setupDownloadDialog();
		showDownloadDialog();
	}

	@SuppressWarnings("unused")
	private void showProgressDialog() {
		dialog = ProgressDialog.show(this, null, null, true);
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	private void getDataFromLocalDatabaseAndStartTheGame() {
		GetDataFromLocalDatabase<StageOneConson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneConson>(
				L1bTestFragementActivity.this, StageOneConson.class);
		ArrayList<StageOneConson> locals = getDataFromLocalDatabase
				.getDataFromLocalDatabase(getConson());
		setLocals(locals);
		setNoOfPages(getLocals().size());
		setCurrentPageNo(0);
		setAnswer();
		displayOnePage(0);
	}

	private void displayOnePage(int pageno) {

		enableNormalBtn(false);
		resetPage();
		// showProgressDialog();
		setLoadBmpProcess(new LoadBmp(pageno));
		Thread b = new Thread(getLoadBmpProcess());
		b.start();

	}

	private class LoadBmp implements Runnable {
		private int pageno;
		private boolean exit = false;

		public LoadBmp(int pageno) {
			this.pageno = pageno;
		}

		private StageOneConson getOnePageData(int pageno) {
			ArrayList<StageOneConson> locals = (ArrayList<StageOneConson>) getLocals();

			return locals.get(pageno);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				if (isExit())
					return;
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
			L1bTestFragementActivity.this
					.runOnUiThread(new ShowUIAfterLoadBmpFinish(pageno));
		}

		public boolean isExit() {
			return exit;
		}

		public void setExit(boolean exit) {
			this.exit = exit;
		}

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
		return (View) findViewById(R.id.test1b_show_screen);
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

	private void resetPage() {
		if (getSoundPool1() != null)
			getSoundPool1().stop(getSoundID1());
		if (getPlayer1() != null)
			getPlayer1().stop();
		if (getPlayer2() != null)
			getPlayer2().stop();
	}

	private void playsound(int pageno) {
		final StageOneConson stageOneConson = getOnePageData(pageno);
		final String savePath = AuditoryDirectoryHelper
				.ObtainStageOneSavePath(stageOneConson.getConson());
		L1bTestFragementActivity.this
				.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setSoundPool1(new SoundPool(10, AudioManager.STREAM_MUSIC, 0));
		setSoundID1(getSoundPool1().load(
				savePath + "/" + stageOneConson.getVoicechip1(), 1));
		getSoundPool1().setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				if (dialog != null) {
					dialog.dismiss();
				}
				setPlayer1(new MediaPlayer());
				getPlayer1().setOnCompletionListener(
						new OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {
								// TODO Auto-generated method stub
								startCounter(getCounter());
								enableNormalBtn(true);

								/*
								 * setPlayer2(new MediaPlayer()); try {
								 * 
								 * getPlayer2().setOnCompletionListener( new
								 * OnCompletionListener() {
								 * 
								 * @Override public void onCompletion(
								 * MediaPlayer mp) { // TODO Auto-generated //
								 * method stub startCounter(getCounter());
								 * enableNormalBtn(true); } });
								 * 
								 * getPlayer2().setDataSource( savePath + "/" +
								 * stageOneConson .getVoicechip2());
								 * getPlayer2().prepare(); getPlayer2().start();
								 * 
								 * } catch (IllegalArgumentException e) { //
								 * TODO Auto-generated catch block
								 * e.printStackTrace(); } catch
								 * (IllegalStateException e) { // TODO
								 * Auto-generated catch block
								 * e.printStackTrace(); } catch (IOException e)
								 * { // TODO Auto-generated catch block
								 * e.printStackTrace(); }
								 */
							}
						});
				boolean notready1 = false;
				do {
					try {
						notready1 = false;
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
						notready1 = true;
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						notready1 = true;
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						notready1 = true;
					}
				} while (notready1);

			}
		});
	}

	private class GotoNextPage implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			getTrueAnswerBtn(1).setVisibility(View.INVISIBLE);
			getFalseAnswerBtn(1).setVisibility(View.INVISIBLE);
			getNormalBtn(1).setVisibility(View.VISIBLE);

			getTrueAnswerBtn(2).setVisibility(View.INVISIBLE);
			getFalseAnswerBtn(2).setVisibility(View.INVISIBLE);
			getNormalBtn(2).setVisibility(View.VISIBLE);

			counter.setText("3");
			setAnswer();
			displayOnePage(getCurrentPageNo());
		}

	}

	private void startCounter(TextView tv) {
		setCounterObj(new Counter(tv));
		counterThread = new Thread(getCounterObj());
		counterThread.start();
	}

	public class Counter implements Runnable {
		TextView tv;
		private boolean exit = false;
		private int counterValue;

		Counter(TextView tv) {
			this.tv = tv;
			this.counterValue = 3;
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

			counterValue = 3;
			while (true) {
				try {
					if (counterValue == -1)
						break;
					L1bTestFragementActivity.this
							.runOnUiThread(new CounterDisplay(this.tv, String
									.valueOf(counterValue)));
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

			if (counterValue < 0) {
				setTotalScoreOfTest(getUserInputAnwserResultForOnePage());
				runOnUiThread(new CounterEndDisplayAnswer());

			}

		}

	}

	private class CounterEndDisplayAnswer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			enableNormalBtn(false);
			if (answer == 0) {
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

			} else if (answer == 1) {

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
			}
		}

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

	private int getUserInputAnwserResultForOnePage() {
		return getTotalScoreOfTest();
	}

	private StageOneConson getOnePageData(int pageno) {
		ArrayList<StageOneConson> locals = (ArrayList<StageOneConson>) getLocals();

		return locals.get(pageno);
	}

	/*
	 * private void showSyncDialog() { if (isOnline()) { final Dialog
	 * downloadDialog = new Dialog(this);
	 * downloadDialog.setContentView(R.layout.test2a_syncdialog_ui); Button
	 * cancelBtn = (Button) downloadDialog
	 * .findViewById(R.id.test2a_sync_dialog_cancel_btn);
	 * cancelBtn.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * downloadDialog.dismiss(); getDataFromLocalDatabaseAndStartTheGame();
	 * 
	 * } }); Button okBtn = (Button) downloadDialog
	 * .findViewById(R.id.test2a_sync_dialog_ok_btn);
	 * okBtn.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * downloadDialog.dismiss();
	 * DownloadTrace.getInstance().setNoOfDownloads(0);
	 * DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
	 * showDownloadDialog(); bindService(); syncData(); } }); if
	 * (L1aTestFragementActivity.isFirstTime(getConson())) {
	 * cancelBtn.setVisibility(View.GONE); } else {
	 * cancelBtn.setVisibility(View.VISIBLE); } downloadDialog.show(); } else {
	 * if (!L1aTestFragementActivity.isFirstTime(getConson()))
	 * getDataFromLocalDatabaseAndStartTheGame(); } }
	 */

	private void syncData() {
		AppDataSource appDataSource = AppDataSource.getInstance();
		appDataSource.getStageOneConson(this, this, new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}

		}, String.valueOf(getConson()));

	}

	private void getConsonFromIntent() {
		int conson = getIntent().getIntExtra("conson", 0);
		Log.d("conson", String.valueOf(conson));
		setConson(conson);
	}

	private void setContentLayout() {
		ImageButton backBtn = (ImageButton) findViewById(R.id.test_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());

		Display display = getWindowManager().getDefaultDisplay();
		int SCREEN_HEIGHT = display.getHeight();
		int size = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);

		/*
		 * TextView textIndicator = (TextView)
		 * findViewById(R.id.test_level_1b_indicator_txt);
		 * textIndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		 * textIndicator.setText(L1aTestFragementActivity
		 * .getConsonString(getConson()));
		 */
		ImageView imgIndicator = (ImageView) findViewById(R.id.test_level_1b_indicator);
		imgIndicator.setImageResource(L1aTestFragementActivity
				.getConsonDrawable(getConson()));

		int sizeForTimer = (int) (SCREEN_HEIGHT * 0.5 / 5.5);
		TextView timer = (TextView) findViewById(R.id.test_level_1b_timer_txt);
		timer.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeForTimer);
		setCounter(timer);

		int size2 = (int) (SCREEN_HEIGHT * 0.5 / 5.5);
		getWordTV(1).setTextSize(TypedValue.COMPLEX_UNIT_PX, size2);
		getWordTV(2).setTextSize(TypedValue.COMPLEX_UNIT_PX, size2);

		ClickBtnListener clickBtnListener = new ClickBtnListener();
		getNormalBtn(1).setOnClickListener(clickBtnListener);
		getNormalBtn(2).setOnClickListener(clickBtnListener);
		getNormalBtn(1).setEnabled(false);
		getNormalBtn(2).setEnabled(false);
		getNextBtn(1).setOnClickListener(clickBtnListener);
		getNextBtn(2).setOnClickListener(clickBtnListener);

		setInputAnswerBtn(1,
				(Button) findViewById(R.id.test1_input_answer_btn_1));
		setInputAnswerBtn(2,
				(Button) findViewById(R.id.test1_input_answer_btn_2));
		getInputAnswerBtn(1).setOnClickListener(clickBtnListener);
		getInputAnswerBtn(2).setOnClickListener(clickBtnListener);

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

			case R.id.stage_one_a_item_display_icon1:
			case R.id.test1_input_answer_btn_1:
				enableNormalBtn(false);

				if (getAnswer() == 0) {
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
				}

				if (getAnswer() == 0)
					setTotalScoreOfTest(getTotalScoreOfTest() + 1);
				if (counterThread != null)
					counterThread.interrupt();
				break;
			case R.id.stage_one_a_item_display_icon2:
			case R.id.test1_input_answer_btn_2:
				enableNormalBtn(false);

				if (getAnswer() == 1) {
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
				}
				if (getAnswer() == 1)
					setTotalScoreOfTest(getTotalScoreOfTest() + 1);
				if (counterThread != null)
					counterThread.interrupt();
				break;
			case R.id.test_one_next_btn1:
			case R.id.test_one_next_btn2:
				Log.d("test one next button", "pressed");
				setCurrentPageNo(getCurrentPageNo() + 1);
				if (getCurrentPageNo() < getLocals().size())
					L1bTestFragementActivity.this
							.runOnUiThread(new GotoNextPage());
				else {
					// send score to the remote server
					Date date = new Date();
					NameValuePair requiredParam1 = new PMNameValuePair("time",
							String.valueOf(date.getTime() / 1000));
					NameValuePair requiredParam2 = new PMNameValuePair("score",
							String.valueOf(Math
									.round(((float) getTotalScoreOfTest())
											/ getNoOfPages() * 100)));
					Log.d("TotalScoreOfTest",
							String.valueOf(getTotalScoreOfTest()));
					Log.d("NoOfPage", String.valueOf(getNoOfPages()));
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(requiredParam1);
					params.add(requiredParam2);
					AppDataSource.getInstance().postStage1bScore(
							L1bTestFragementActivity.this,
							L1bTestFragementActivity.this, params, null,
							String.valueOf(getConson()));

					if (Math.round(((float) getTotalScoreOfTest())
							/ getNoOfPages() * 100) >= 80) {
						UserLoginOrLogoutProcess login = new UserLoginOrLogoutProcess(
								L1bTestFragementActivity.this, null);
						GetDataFromLocalDatabase<PassToStage2StateRecord> process = new GetDataFromLocalDatabase<PassToStage2StateRecord>(
								L1bTestFragementActivity.this,
								PassToStage2StateRecord.class);
						ProcessPassToStage2StateRecord pass = new ProcessPassToStage2StateRecord(
								process, login.getUserId());
						pass.saveStateToDatabase(1, getConson());
					}

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent intent0 = new Intent(L1bTestFragementActivity.this,
							L1ResultFragementActivity.class);
					intent0.putExtra("conson", getConson());
					startActivity(intent0);
					finish();
				}
				break;
			}

		}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public Counter getCounterObj() {
		return counterObj;
	}

	public void setCounterObj(Counter counterObj) {
		this.counterObj = counterObj;
	}

	private void stopCountThread() {
		if (getCounterObj() != null)
			getCounterObj().setExit(true);
		if (counterThread != null)
			counterThread.interrupt();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		resetPage();
		stopCountThread();
		if (getLoadBmpProcess() != null)
			getLoadBmpProcess().setExit(true);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		resetPage();
		stopCountThread();
		if (getLoadBmpProcess() != null)
			getLoadBmpProcess().setExit(true);
	}

	public LoadBmp getLoadBmpProcess() {
		return loadBmpProcess;
	}

	public void setLoadBmpProcess(LoadBmp loadBmpProcess) {
		this.loadBmpProcess = loadBmpProcess;
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

	private class AfterFinishDownloadAction implements
			DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
			DownloadTrace.getInstance().addOneDownloadsComplete();
			L1bTestFragementActivity.this
					.runOnUiThread(new ShowDownloadProgress());
			L1bTestFragementActivity.this.runOnUiThread(new DismissDialog());
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			DownloadTrace.getInstance().addOneDownload();
			L1bTestFragementActivity.this
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
				L1aTestFragementActivity.setFirstTime(getConson());
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
				bindService();
				syncData();

			}
		});
		if (L1aTestFragementActivity.isFirstTime(getConson())) {
			cancelBtn.setVisibility(View.GONE);
		} else {
			cancelBtn.setVisibility(View.VISIBLE);
		}
	}

	private void showDownloadDialog() {
		if (isOnline()) {
			if (L1aTestFragementActivity.isFirstTime(getConson()))
				downloadDialog.show();
			else
				getDataFromLocalDatabaseAndStartTheGame();
		} else {
			if (!L1aTestFragementActivity.isFirstTime(getConson()))
				getDataFromLocalDatabaseAndStartTheGame();
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
