package hk.org.deaf.auditoryandspeechtrainingapp.level3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import hk.org.deaf.auditoryandspeechtrainingapp.CommonFunctionsForTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeThreeResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeTwoResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.result.L3ResultFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDataSource;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDatabaseHelper;
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

public class L3RandomSingleWordTestFragementActivity extends
		CommonFunctionsForTestFragmentActivity {
	private int conson;
	private static int currentPageNo;
	private List<StageOneConson> locals0;
	private List<StageOneConson> locals1;
	private List<StageOneConson> locals2;
	private List<StageOneConson> locals3;
	private List<StageOneConson> locals4;
	private List<StageOneConson> locals5;
	private List<StageOneConson> locals6;
	private List<StageOneConson> locals7;
	private List<StageOneConson> locals8;

	private int noOfPages;
	private Bitmap picture1, picture2;
	private SoundPool soundPool1, soundPool2;
	private int soundID1, soundID2;
	private ProgressDialog dialog;
	private TextView counter;
	private Thread counterThread;
	private Counter counterObj;
	private int totalScoreOfTest = 0;
	private LoadBmp loadBmpProcess;
	private int loopCounter;
	private static Random consonRandom;
	private static Random pageNoRandom;
	private static int currentConson;
	private int noOfQuestion;
	private int noOfPageAnswered;
	private static Hashtable<Integer, Boolean[]> usedPages;
	InitSavedPages initSavedPages;
	private int answer;
	private Button inputAnswerBtn1, inputAnswerBtn2;

	public InitSavedPages getInitSavedPages() {
		return initSavedPages;
	}

	public void setInitSavedPages(InitSavedPages initSavedPages) {
		this.initSavedPages = initSavedPages;
	}

	public int getNoOfPageAnswered() {
		return noOfPageAnswered;
	}

	public void setNoOfPageAnswered(int noOfPageAnswered) {
		this.noOfPageAnswered = noOfPageAnswered;
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

	private MediaPlayer player1, player2;

	public List<StageOneConson> getLocals(int conson) {
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
		case 7:
			return locals7;
		case 8:
			return locals8;
		}
		return null;
	}

	public void setLocals(int conson, List<StageOneConson> locals) {
		switch (conson) {
		case 0:
			this.locals0 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 1:
			this.locals1 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 2:
			this.locals2 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 3:
			this.locals3 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 4:
			this.locals4 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 5:
			this.locals5 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 6:
			this.locals6 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 7:
			this.locals7 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 8:
			this.locals8 = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level1b_test_revised);
		ContextStorage.getInstance().setContext(getApplicationContext());
		setContentLayout();
		setUpDownloadProgressDialog();
		setSelectionBtnLayout();
		setNoOfPageAnswered(0);
		setTotalScoreOfTest(0);
		getIsFirstDataFromDataAndSetup();
		setupDownloadDialog();
		showDownloadDialog();
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
			Boolean[] a7 = new Boolean[getLocals(7).size()];
			Boolean[] a8 = new Boolean[getLocals(8).size()];
			initArray(a0);
			initArray(a1);
			initArray(a2);
			initArray(a3);
			initArray(a4);
			initArray(a5);
			initArray(a6);
			initArray(a7);
			initArray(a8);
			usedPages.put(0, a0);
			usedPages.put(1, a1);
			usedPages.put(2, a2);
			usedPages.put(3, a3);
			usedPages.put(4, a4);
			usedPages.put(5, a5);
			usedPages.put(6, a6);
			usedPages.put(7, a7);
			usedPages.put(8, a8);
		}

		private void initArray(Boolean[] x) {
			for (int i = 0; i < x.length; i++) {
				x[i] = false;
			}
		}
	}

	private void showProgressDialog() {
		dialog = new ProgressDialog(this);
		dialog.setButton("Cancel", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		dialog.show();
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	private void getDataFromLocalDatabase(int conson) {
		GetDataFromLocalDatabase<StageOneConson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneConson>(
				L3RandomSingleWordTestFragementActivity.this,
				StageOneConson.class);
		ArrayList<StageOneConson> locals = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		setLocals(conson, locals);
		// setNoOfPages();
		// setCurrentPageNo(0);
		// displayOnePage(0);
	}

	private ArrayList<StageOneConson> getDataFromLocalDatabase2(int conson) {
		GetDataFromLocalDatabase<StageOneConson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneConson>(
				L3RandomSingleWordTestFragementActivity.this,
				StageOneConson.class);
		ArrayList<StageOneConson> locals = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		return locals;
		// setNoOfPages();
		// setCurrentPageNo(0);
		// displayOnePage(0);
	}

	private void displayOnePage(int pageno) {

		enableNormalBtn(false);
		resetPage();
		showProgressDialog();
		setLoadBmpProcess(new LoadBmp(pageno));
		Thread b = new Thread(getLoadBmpProcess());
		b.start();

	}

	private void setOnePage() {
		ArrayList<StageOneConson> locals;
		int pageNo = 0;
		int conson;
		do {
			Random consonRandam = new Random();
			conson = consonRandam.nextInt(9);

			locals = new ArrayList<StageOneConson>(
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

	private class LoadBmp implements Runnable {
		private int pageno;
		private boolean exit = false;

		public LoadBmp(int pageno) {
			this.pageno = pageno;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				if (isExit())
					return;
				StageOneConson stageOneconson = getOnePageData(
						getCurrentConson(), getCurrentPageNo());
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
			L3RandomSingleWordTestFragementActivity.this
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
			imgIndicator.setImageResource(L1aTestFragementActivity
					.getConsonDrawable(getCurrentConson()));

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

			StageOneConson stageOneconson = getOnePageData(getCurrentConson(),
					getCurrentPageNo());
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
		final StageOneConson stageOneConson = getOnePageData(
				getCurrentConson(), pageno);
		final String savePath = AuditoryDirectoryHelper
				.ObtainStageOneSavePath(stageOneConson.getConson());
		L3RandomSingleWordTestFragementActivity.this
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
			setOnePage();
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
					L3RandomSingleWordTestFragementActivity.this
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

				playPassSound();
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

	private void getAllConsonsDataFromLocalDatabase() {
		for (int i = 0; i < 9; i++) {
			getDataFromLocalDatabase(i);
		}
		setNoOfPages();
	}

	private void syncData() {
		AppDataSource appDataSource = AppDataSource.getInstance();
		showProgressDialog();
		appDataSource.getStageOneConson(this, this,
				new AfterSyncDataAction0(0), "0");
	}

	private class AfterSyncDataAction0 implements Runnable {
		private int conson;

		public AfterSyncDataAction0(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction1(1), "1");

		}

	}

	private class AfterSyncDataAction1 implements Runnable {
		private int conson;

		public AfterSyncDataAction1(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);

			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction2(2), "2");
			// getDataFromLocalDatabase(this.conson);

		}

	}

	private class AfterSyncDataAction2 implements Runnable {
		private int conson;

		public AfterSyncDataAction2(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction3(3), "3");
			// getDataFromLocalDatabase(this.conson);

		}

	}

	private class AfterSyncDataAction3 implements Runnable {
		private int conson;

		public AfterSyncDataAction3(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction4(4), "4");
			// getDataFromLocalDatabase(this.conson);

		}

	}

	private class AfterSyncDataAction4 implements Runnable {
		private int conson;

		public AfterSyncDataAction4(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction5(5), "5");
			// getDataFromLocalDatabase(this.conson);

		}

	}

	private class AfterSyncDataAction5 implements Runnable {
		private int conson;

		public AfterSyncDataAction5(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction6(6), "6");
			// getDataFromLocalDatabase(this.conson);

		}

	}

	private class AfterSyncDataAction6 implements Runnable {
		private int conson;

		public AfterSyncDataAction6(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction7(7), "7");
			// getDataFromLocalDatabase(this.conson);

		}

	}

	private class AfterSyncDataAction7 implements Runnable {
		private int conson;

		public AfterSyncDataAction7(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			AppDataSource appDataSource = AppDataSource.getInstance();
			appDataSource.getStageOneConson(
					L3RandomSingleWordTestFragementActivity.this,
					L3RandomSingleWordTestFragementActivity.this,
					new AfterSyncDataAction8(8), "8");
			// getDataFromLocalDatabase(this.conson);

		}

	}

	private class AfterSyncDataAction8 implements Runnable {
		private int conson;

		public AfterSyncDataAction8(int conson) {
			this.conson = conson;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			dialog.dismiss();
			Log.d("download123", "download1123 with conson = " + this.conson);
			L1aTestFragementActivity.setFirstTime(this.conson);
			getDataFromLocalDatabase(this.conson);
			getAllConsonsDataFromLocalDatabase();
			processNoOfQuestionTheGamePlay();
			processOnePage();

		}

	}

	private int getNoOfPageFromIntent() {
		return getIntent().getIntExtra("no_of_pages", 0);
	}

	private void processNoOfQuestionTheGamePlay() {
		int noOfPages = getNoOfPageFromIntent();
		if (noOfPages > getNoOfPages()) {
			setNoOfQuestion(getNoOfPages() / 2);
		} else if (noOfPages == 0) {
			setNoOfQuestion(5);
		} else {
			setNoOfQuestion(noOfPages);
		}
	}

	private ImageView imgIndicator;

	private void setContentLayout() {
		setConsonRandom(new Random());
		setPageNoRandom(new Random());
		ImageButton backBtn = (ImageButton) findViewById(R.id.test_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());

		Display display = getWindowManager().getDefaultDisplay();
		int SCREEN_HEIGHT = display.getHeight();
		int size = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);

		/*
		 * textIndicator = (TextView)
		 * findViewById(R.id.test_level_1b_indicator_txt);
		 * textIndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		 * textIndicator.setText(L1aTestFragementActivity
		 * .getConsonString(getCurrentConson()));
		 */
		imgIndicator = (ImageView) findViewById(R.id.test_level_1b_indicator);
		imgIndicator.setImageResource(L1aTestFragementActivity
				.getConsonDrawable(getConson()));

		int sizeForTimer = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);
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

		setInputAnswerBtn(1,
				(Button) findViewById(R.id.test1_input_answer_btn_1));
		setInputAnswerBtn(2,
				(Button) findViewById(R.id.test1_input_answer_btn_2));
		getInputAnswerBtn(1).setOnClickListener(clickBtnListener);
		getInputAnswerBtn(2).setOnClickListener(clickBtnListener);

		getNextBtn(1).setOnClickListener(clickBtnListener);
		getNextBtn(2).setOnClickListener(clickBtnListener);
	}

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

	private StageOneConson getOnePageData(int conson, int pageNo) {
		List<StageOneConson> locals = getLocals(conson);
		return locals.get(pageNo);

	}

	private class ClickBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			StageOneConson stageOneconson = getOnePageData(getCurrentConson(),
					getCurrentPageNo());
			int answer = stageOneconson.getAnswer();

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
				setNoOfPageAnswered(getNoOfPageAnswered() + 1);
				if (getNoOfPageAnswered() < noOfQuestion) {
					L3RandomSingleWordTestFragementActivity.this
							.runOnUiThread(new GotoNextPage());
				} else {

					if (getTotalScoreOfTest() == noOfQuestion) {
						// if (true){
						if (getNoOfPageFromIntent() == 20) {
							ChallengeResultProcessing<ChallengeTwoResult> processing = new ChallengeResultProcessing<ChallengeTwoResult>(
									L3RandomSingleWordTestFragementActivity.this,
									ChallengeTwoResult.class);
							if (processing.getChallengeResult() != null) {
								processing.saveChallengeResultToLocalDatabase(
										true, processing.getChallengeResult());
							} else {
								processing.saveChallengeResultToLocalDatabase(
										true, new ChallengeTwoResult());
							}
						} else if (getNoOfPageFromIntent() == 40) {
							ChallengeResultProcessing<ChallengeThreeResult> processing = new ChallengeResultProcessing<ChallengeThreeResult>(
									L3RandomSingleWordTestFragementActivity.this,
									ChallengeThreeResult.class);
							if (processing.getChallengeResult() != null) {
								processing.saveChallengeResultToLocalDatabase(
										true, processing.getChallengeResult());
							} else {
								processing.saveChallengeResultToLocalDatabase(
										true, new ChallengeThreeResult());
							}
						}

					}
					Intent intent0 = new Intent(
							L3RandomSingleWordTestFragementActivity.this,
							L3ResultFragementActivity.class);
					startActivity(intent0);
					finish();
				}

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

	public static int getCurrentPageNo() {
		return L3RandomSingleWordTestFragementActivity.currentPageNo;
	}

	public static void setCurrentPageNo(int currentPageNo) {
		L3RandomSingleWordTestFragementActivity.currentPageNo = currentPageNo;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages() {
		this.noOfPages = 0;
		for (int i = 0; i < 9; i++) {
			this.noOfPages += getLocals(i).size();
		}
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

	public int getLoopCounter() {
		return loopCounter;
	}

	public void setLoopCounter(int loopCounter) {
		this.loopCounter = loopCounter;
	}

	public static Random getConsonRandom() {
		return consonRandom;
	}

	public void setConsonRandom(Random consonRandom) {
		L3RandomSingleWordTestFragementActivity.consonRandom = consonRandom;
	}

	public static Random getPageNoRandom() {
		return pageNoRandom;
	}

	public void setPageNoRandom(Random pageNoRandom) {
		L3RandomSingleWordTestFragementActivity.pageNoRandom = pageNoRandom;
	}

	public static int getCurrentConson() {
		return L3RandomSingleWordTestFragementActivity.currentConson;
	}

	public static void setCurrentConson(int currentConson) {
		L3RandomSingleWordTestFragementActivity.currentConson = currentConson;
	}

	public int getNoOfQuestion() {
		return noOfQuestion;
	}

	public void setNoOfQuestion(int noOfQuestion) {
		this.noOfQuestion = noOfQuestion;
	}

	private void processOnePage() {
		setInitSavedPages(new InitSavedPages());
		setOnePage();
		setAnswer();
		displayOnePage(getCurrentPageNo());
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer() {
		Random randomAnswer = new Random();
		int answer = randomAnswer.nextInt(2);
		this.answer = answer;
	}

	public SoundPool getSoundPool2() {
		return soundPool2;
	}

	public void setSoundPool2(SoundPool soundPool2) {
		this.soundPool2 = soundPool2;
	}

	public int getSoundID2() {
		return soundID2;
	}

	public void setSoundID2(int soundID2) {
		this.soundID2 = soundID2;
	}

	private class AfterFinishDownloadAction implements
			DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
			DownloadTrace.getInstance().addOneDownloadsComplete();
			L3RandomSingleWordTestFragementActivity.this
					.runOnUiThread(new ShowDownloadProgress());
			L3RandomSingleWordTestFragementActivity.this
					.runOnUiThread(new DismissDialog());
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			DownloadTrace.getInstance().addOneDownload();
			L3RandomSingleWordTestFragementActivity.this
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
				L1aTestFragementActivity
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
				setNoOfConsonOfDownload(9);
				setCurrentConsonOfNewDownloadMethod(0);
				setDownloadOnlyRemainingFlag(true);
				setDownloadFlap(true);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
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
				setNoOfConsonOfDownload(9);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				setDownloadOnlyRemainingFlag(false);
				setDownloadFlap(true);
				showDownloadProgressDialog();
				bindService();
				syncDataForNewDownloadMethod();

				// getAllConsonsDataFromLocalDatabase();
				// processOnePage();
			}
		});
		if (L1aTestFragementActivity.isFirstTime(0)
				|| L1aTestFragementActivity.isFirstTime(1)
				|| L1aTestFragementActivity.isFirstTime(2)
				|| L1aTestFragementActivity.isFirstTime(3)
				|| L1aTestFragementActivity.isFirstTime(4)
				|| L1aTestFragementActivity.isFirstTime(5)
				|| L1aTestFragementActivity.isFirstTime(6)
				|| L1aTestFragementActivity.isFirstTime(7)
				|| L1aTestFragementActivity.isFirstTime(8)) {
			cancelBtn.setVisibility(View.GONE);
			if ((L1aTestFragementActivity.isFirstTime(0)
					&& L1aTestFragementActivity.isFirstTime(1)
					&& L1aTestFragementActivity.isFirstTime(2)
					&& L1aTestFragementActivity.isFirstTime(3)
					&& L1aTestFragementActivity.isFirstTime(4)
					&& L1aTestFragementActivity.isFirstTime(5)
					&& L1aTestFragementActivity.isFirstTime(6)
					&& L1aTestFragementActivity.isFirstTime(7) && L1aTestFragementActivity
						.isFirstTime(8))
					|| (!L1aTestFragementActivity.isFirstTime(0)
							&& !L1aTestFragementActivity.isFirstTime(1)
							&& !L1aTestFragementActivity.isFirstTime(2)
							&& !L1aTestFragementActivity.isFirstTime(3)
							&& !L1aTestFragementActivity.isFirstTime(4)
							&& !L1aTestFragementActivity.isFirstTime(5)
							&& !L1aTestFragementActivity.isFirstTime(6)
							&& !L1aTestFragementActivity.isFirstTime(7) && !L1aTestFragementActivity
								.isFirstTime(8))) {
				remainingBtn.setVisibility(View.GONE);
			}

		} else {
			cancelBtn.setVisibility(View.VISIBLE);
			remainingBtn.setVisibility(View.GONE);
		}

	}

	private void showDownloadDialog() {
		if (isOnline()) {
			if (L1aTestFragementActivity.isFirstTime(0)
					|| L1aTestFragementActivity.isFirstTime(1)
					|| L1aTestFragementActivity.isFirstTime(2)
					|| L1aTestFragementActivity.isFirstTime(3)
					|| L1aTestFragementActivity.isFirstTime(4)
					|| L1aTestFragementActivity.isFirstTime(5)
					|| L1aTestFragementActivity.isFirstTime(6)
					|| L1aTestFragementActivity.isFirstTime(7)
					|| L1aTestFragementActivity.isFirstTime(8)) {
				downloadDialog.show();
			} else {
				getAllConsonsDataFromLocalDatabase();
				processNoOfQuestionTheGamePlay();
				processOnePage();
			}
		} else {
			if (!(L1aTestFragementActivity.isFirstTime(0)
					|| L1aTestFragementActivity.isFirstTime(1)
					|| L1aTestFragementActivity.isFirstTime(2)
					|| L1aTestFragementActivity.isFirstTime(3)
					|| L1aTestFragementActivity.isFirstTime(4)
					|| L1aTestFragementActivity.isFirstTime(5)
					|| L1aTestFragementActivity.isFirstTime(6)
					|| L1aTestFragementActivity.isFirstTime(7) || L1aTestFragementActivity
						.isFirstTime(8))) {
				getAllConsonsDataFromLocalDatabase();
				processNoOfQuestionTheGamePlay();
				processOnePage();
			}
		}
	}

	@SuppressWarnings("unused")
	private synchronized void syncDataForNewDownloadMethod() {
		if (isDownloadFlap()) {
			if (false) {
			//if (isDownloadOnlyRemainingFlag()) {
				while (L1aTestFragementActivity
						.isFirstTime(getCurrentConsonOfNewDownloadMethod()) == false
						&& (getNoOfConsonOfDownload() > getCurrentConsonOfNewDownloadMethod())) {
					setCurrentConsonOfNewDownloadMethod(getCurrentConsonOfNewDownloadMethod() + 1);
				}
				if (L1aTestFragementActivity
						.isFirstTime(getCurrentConsonOfNewDownloadMethod())
						&& (getNoOfConsonOfDownload() > getCurrentConsonOfNewDownloadMethod())) {
					AppDataSource appDataSource = AppDataSource.getInstance();
					appDataSource.getStageOneConson(this, this, new Runnable() {

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
				appDataSource.getStageOneConson(this, this, new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

					}

				}, String.valueOf(getCurrentConsonOfNewDownloadMethod()));
			}
		}
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

	private void unBindDownloadService() {
		if (DownloadTrace.getInstance().getNoOfDownloads()>0) {
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
