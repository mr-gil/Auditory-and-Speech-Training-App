package hk.org.deaf.auditoryandspeechtrainingapp.fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level3.L3RandomAllTypeTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.AuditoryDirectoryHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PlaySoundForPassOrFail;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class L3OnePageSingleWordTestFragement extends Fragment {
	private int conson;

	// private int noOfPages;
	private Bitmap picture1, picture2;
	private SoundPool soundPool1, soundPool2;
	private int soundID1, soundID2;
	private ProgressDialog dialog;
	private TextView counter;
	private Thread counterThread;
	private Counter counterObj;
	private int totalScoreOfTest = 0;
	private LoadBmpForSingleWord loadBmpProcess;
	private int loopCounter;
	private static int currentConson;
	private StageOneConson stageOneConson;
	private static Hashtable<Integer, Boolean[]> usedPagesForSingleWord;
	private int answer;
	private ViewGroup v;
	private Activity activity;
	private boolean soundExitLoop;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = (ViewGroup) inflater.inflate(R.layout.level1b_test_revised,
				container, false);
		setContentLayout();
		setSelectionBtnLayout();
		setAnswer();
		getOnePageDataAndStartTheGame();

		return v;

	}

	private PlaySoundForPassOrFail playSoundForPass;
	public void playPassSound(){
		playSoundForPass = new PlaySoundForPassOrFail(getActivity());
		playSoundForPass.loadPassSoundFile();
		playSoundForPass.playPassSound();
		
	}
	private PlaySoundForPassOrFail playSoundForFail;
	public void playFailSound(){
		playSoundForFail = new PlaySoundForPassOrFail(getActivity());
		playSoundForFail.loadFailSoundFile();
		playSoundForFail.playFailSound();
	}
	
	private void showProgressDialog() {
		dialog = ProgressDialog.show(
				L3OnePageSingleWordTestFragement.this.activity, null, null,
				true);
	}

	private void displayOnePage() {

		enableNormalBtn(false);
		resetPage();
		//showProgressDialog();
		setLoadBmpProcess(new LoadBmpForSingleWord());
		Thread b = new Thread(getLoadBmpProcess());
		b.start();

	}

	private void getOnePageDataAndStartTheGame() {
		displayOnePage();
	}

	private class LoadBmpForSingleWord implements Runnable {

		private boolean exit = false;

		public LoadBmpForSingleWord() {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				if (isExit())
					return;
				StageOneConson stageOneconson = getOnePageData();
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
			ShowUIAfterLoadBmpFinishForSingleWord mShowUIAfterLoadBmpFinishForSingleWord = new ShowUIAfterLoadBmpFinishForSingleWord();
			if (L3OnePageSingleWordTestFragement.this.activity == null)
				Log.d("activity456", "null");
			L3OnePageSingleWordTestFragement.this.activity
					.runOnUiThread(mShowUIAfterLoadBmpFinishForSingleWord);
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
			return (ImageView) v
					.findViewById(R.id.indicator_of_right_answer_of_test1);
		case 2:
			return (ImageView) v
					.findViewById(R.id.indicator_of_right_answer_of_test2);
		}
		return null;
	}

	private ImageView getIndicatorOfWrongAnswer(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageView) v
					.findViewById(R.id.indicator_of_wrong_answer_of_test1);
		case 2:
			return (ImageView) v
					.findViewById(R.id.indicator_of_wrong_answer_of_test2);
		}
		return null;
	}

	private ImageView getFrameOfShowPic(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageView) v.findViewById(R.id.frame_of_pic_of_test1);
		case 2:
			return (ImageView) v.findViewById(R.id.frame_of_pic_of_test2);
		}
		return null;
	}

	private ImageView getShowPic(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageView) v.findViewById(R.id.test_one_pic1);
		case 2:
			return (ImageView) v.findViewById(R.id.test_one_pic2);
		}
		return null;
	}

	private ImageButton getNextBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) v.findViewById(R.id.test_one_next_btn1);
		case 2:
			return (ImageButton) v.findViewById(R.id.test_one_next_btn2);
		}
		return null;
	}

	private ImageButton getNormalBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) v
					.findViewById(R.id.stage_one_a_item_display_icon1);
		case 2:
			return (ImageButton) v
					.findViewById(R.id.stage_one_a_item_display_icon2);
		}
		return null;
	}

	private ImageButton getFalseAnswerBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) v
					.findViewById(R.id.stage_one_a_item_false_answer1);
		case 2:
			return (ImageButton) v
					.findViewById(R.id.stage_one_a_item_false_answer2);
		}
		return null;
	}

	private ImageButton getTrueAnswerBtn(int itemNo) {
		switch (itemNo) {
		case 1:
			return (ImageButton) v
					.findViewById(R.id.stage_one_a_item_true_answer1);
		case 2:
			return (ImageButton) v
					.findViewById(R.id.stage_one_a_item_true_answer2);
		}
		return null;
	}

	private TextView getWordTV(int itemNo) {
		switch (itemNo) {
		case 1:
			return (TextView) v.findViewById(R.id.level1_test_text_show1);
		case 2:
			return (TextView) v.findViewById(R.id.level1_test_text_show2);
		}
		return null;
	}

	private View getShowScreen(){
		return (View) v.findViewById(R.id.test1b_show_screen);
	}
	
	private class ShowUIAfterLoadBmpFinishForSingleWord implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*textIndicator.setText(L1aTestFragementActivity
					.getConsonString(getConson()));*/
			if (imgIndicator != null) imgIndicator.setImageResource(L1aTestFragementActivity.getConsonDrawable(getConson()));
			
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

			StageOneConson stageOneconson = getOnePageData();
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

			playsound();

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

	private void playsound() {
		final StageOneConson stageOneConson = getOnePageData();
		final String savePath = AuditoryDirectoryHelper
				.ObtainStageOneSavePath(stageOneConson.getConson());
		L3OnePageSingleWordTestFragement.this.activity
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
					dialog = null;
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
					if (soundExitLoop)
						break;
				} while (notready1);

			}
		});
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
					L3OnePageSingleWordTestFragement.this.activity
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

			if (counterValue <0){
				setTotalScoreOfTest(getUserInputAnwserResultForOnePage());
				L3OnePageSingleWordTestFragement.this.activity.runOnUiThread(new CounterEndDisplayAnswer());
				
			}

		}

	}
	
	private class CounterEndDisplayAnswer implements Runnable{

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
			}else if (answer == 1) {
				
				
				
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
		return 1;
	}

	private ImageView imgIndicator;
	private void setContentLayout() {
		ImageButton backBtn = (ImageButton) v
				.findViewById(R.id.test_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());

		Display display = L3OnePageSingleWordTestFragement.this.activity
				.getWindowManager().getDefaultDisplay();
		int SCREEN_HEIGHT = display.getHeight();
		int size = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1/2);

		/*textIndicator = (TextView) v
				.findViewById(R.id.test_level_1b_indicator_txt);
		textIndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		textIndicator.setText(L1aTestFragementActivity
				.getConsonString(getConson()));*/
		ImageView imgIndicator = (ImageView) v.findViewById(R.id.test_level_1b_indicator);
		imgIndicator.setImageResource(L1aTestFragementActivity.getConsonDrawable(getConson()));

		int sizeForTimer = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);
		TextView timer = (TextView) v
				.findViewById(R.id.test_level_1b_timer_txt);
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
				(Button) v.findViewById(R.id.test1_input_answer_btn_1));
		setInputAnswerBtn(2,
				(Button) v.findViewById(R.id.test1_input_answer_btn_2));
		getInputAnswerBtn(1).setOnClickListener(clickBtnListener);
		getInputAnswerBtn(2).setOnClickListener(clickBtnListener);
	}

	private class ClickBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//StageOneConson stageOneconson = getOnePageData();
			//int answer = stageOneconson.getAnswer();

			switch (v.getId()) {
			case R.id.test_page_back_btn:
				L3OnePageSingleWordTestFragement.this.activity.finish();
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

				if (getAnswer() == 0){
					//setTotalScoreOfTest(getTotalScoreOfTest() + 1);
					((L3RandomAllTypeTestFragmentActivity) L3OnePageSingleWordTestFragement.this.activity).setTotalScoreOfTest(getUserInputAnwserResultForOnePage());
				}
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
					((L3RandomAllTypeTestFragmentActivity) L3OnePageSingleWordTestFragement.this.activity).setTotalScoreOfTest(getUserInputAnwserResultForOnePage());
				if (counterThread != null)
					counterThread.interrupt();
				break;

			case R.id.test_one_next_btn1:
			case R.id.test_one_next_btn2:
				((L3RandomAllTypeTestFragmentActivity) L3OnePageSingleWordTestFragement.this.activity).showNextPage();
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
				L3OnePageSingleWordTestFragement.this.activity.finish();
			}
		}

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

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
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
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		resetPage();
		stopCountThread();
		if (getLoadBmpProcess() != null)
			getLoadBmpProcess().setExit(true);
		if (dialog != null)
			dialog.dismiss();
		if (getPlayer1() != null)
			getPlayer1().stop();
		soundExitLoop = true;
	}

	public LoadBmpForSingleWord getLoadBmpProcess() {
		return loadBmpProcess;
	}

	public void setLoadBmpProcess(LoadBmpForSingleWord loadBmpProcess) {
		this.loadBmpProcess = loadBmpProcess;
	}

	public int getLoopCounter() {
		return loopCounter;
	}

	public void setLoopCounter(int loopCounter) {
		this.loopCounter = loopCounter;
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

	private StageOneConson getOnePageData() {
		return stageOneConson;

	}

	public void setOnePageData(StageOneConson stageOneConson) {
		this.stageOneConson = stageOneConson;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (dialog != null)
			dialog.show();

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
	}
}
