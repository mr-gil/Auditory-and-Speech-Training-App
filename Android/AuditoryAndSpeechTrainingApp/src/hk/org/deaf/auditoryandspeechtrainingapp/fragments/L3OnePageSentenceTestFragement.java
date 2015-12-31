package hk.org.deaf.auditoryandspeechtrainingapp.fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level3.L3RandomAllTypeTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.Stage2Conson;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.AuditoryDirectoryHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PlaySoundForPassOrFail;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class L3OnePageSentenceTestFragement extends
		Fragment {

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

	private int conson;
	private ImageButton okBtn;
	private ImageButton nextBtn;
	private String[] inputAnwser = new String[10];
	private boolean[] result = new boolean[10];

	private Bitmap picture;
	private ProgressDialog dialog;

	private TextView counter;
	private boolean readyToOtherPage = false;

	private Thread counterThread;
	private Counter counterObj;
	private MediaPlayer player;
	private Stage2Conson stage2Conson;
	private ViewGroup v;
	private Activity activity;
	private boolean soundExitLoop = false;

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = (ViewGroup) inflater.inflate(R.layout.level2b_test_revised, container, false);
		setContentLayout();
		setSelectionBtnLayout();
		setInputAnswerUILayout();
		inputAnswerInit();
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
	
	private void getOnePageDataAndStartTheGame() {
		displayOnePage();
	}

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
	}
	
	private ImageView imgIndicator;
	private void setContentLayout() {
		ImageButton backBtn = (ImageButton) v.findViewById(R.id.test_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());

		// xmlLayoutConnection(R.drawable.box_menu, R.layout.level1a_test,
		// R.drawable.bg_test);
		Display display = L3OnePageSentenceTestFragement.this.activity.getWindowManager().getDefaultDisplay();
		// int SCREEN_WIDTH = display.getWidth();
		int SCREEN_HEIGHT = display.getHeight();

		counter = (TextView) v.findViewById(R.id.test_level_2b_timer_txt);

		/*TextView consonTxtndicator = (TextView) v.findViewById(R.id.test_level_2b_indicator_txt);
		consonTxtndicator.setText(getConsonString(getConson()));*/
		imgIndicator = (ImageView) v.findViewById(R.id.test_level_2b_indicator);
		imgIndicator.setImageResource(L2aTestFragementActivity.getConsonDrawable(getConson()));
		
		int size = (int) (SCREEN_HEIGHT * 0.5 / 5.5 * 1 / 2);
		counter.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		//consonTxtndicator.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

		
		showPic = (ImageView) v.findViewById(R.id.show_pic);
		frameOfShowPic = (ImageView) v.findViewById(R.id.frame_of_pic);
		indicatorOfRightAnswer = (ImageView) v.findViewById(R.id.indicator_of_right_answer);
		indicatorOfWrongAnswer = (ImageView) v.findViewById(R.id.indicator_of_wrong_answer);

		InputBox1 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt1);
		InputBox2 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt2);
		InputBox3 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt3);
		InputBox4 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt4);
		InputBox5 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt5);
		InputBox6 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt6);
		InputBox7 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt7);
		InputBox8 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt8);
		InputBox9 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt9);
		InputBox10 = (TextView) v.findViewById(R.id.test_level_2a_indicator_txt10);

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
		
		boxGroup1 = (View) v.findViewById(R.id.test_level_2a_allbox1);
		boxGroup2 = (View) v.findViewById(R.id.test_level_2a_allbox2);
		boxGroup3 = (View) v.findViewById(R.id.test_level_2a_allbox3);
		boxGroup4 = (View) v.findViewById(R.id.test_level_2a_allbox4);
		boxGroup5 = (View) v.findViewById(R.id.test_level_2a_allbox5);
		boxGroup6 = (View) v.findViewById(R.id.test_level_2a_allbox6);
		boxGroup7 = (View) v.findViewById(R.id.test_level_2a_allbox7);
		boxGroup8 = (View) v.findViewById(R.id.test_level_2a_allbox8);
		boxGroup9 = (View) v.findViewById(R.id.test_level_2a_allbox9);
		boxGroup10 = (View) v.findViewById(R.id.test_level_2a_allbox10);

	}

	

	private void setInputAnswerUILayout() {
		ClickBtnListener clickBtnListener = new ClickBtnListener();
		normalBtn1 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon1);
		normalBtn2 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon2);
		normalBtn3 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon3);
		normalBtn4 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon4);
		normalBtn5 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon5);
		normalBtn6 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon6);
		normalBtn7 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon7);
		normalBtn8 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon8);
		normalBtn9 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon9);
		normalBtn10 = (ImageButton) v.findViewById(R.id.stage_two_a_item_display_icon10);
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
		falseAnswerBtn1 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer1);
		falseAnswerBtn2 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer2);
		falseAnswerBtn3 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer3);
		falseAnswerBtn4 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer4);
		falseAnswerBtn5 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer5);
		falseAnswerBtn6 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer6);
		falseAnswerBtn7 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer7);
		falseAnswerBtn8 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer8);
		falseAnswerBtn9 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer9);
		falseAnswerBtn10 = (ImageButton) v.findViewById(R.id.stage_two_a_item_false_answer10);
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
		trueAnswerBtn1 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer1);
		trueAnswerBtn2 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer2);
		trueAnswerBtn3 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer3);
		trueAnswerBtn4 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer4);
		trueAnswerBtn5 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer5);
		trueAnswerBtn6 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer6);
		trueAnswerBtn7 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer7);
		trueAnswerBtn8 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer8);
		trueAnswerBtn9 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer9);
		trueAnswerBtn10 = (ImageButton) v.findViewById(R.id.stage_two_a_item_true_answer10);
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
		inputAnswerBtn1 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted1);
		inputAnswerBtn2 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted2);
		inputAnswerBtn3 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted3);
		inputAnswerBtn4 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted4);
		inputAnswerBtn5 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted5);
		inputAnswerBtn6 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted6);
		inputAnswerBtn7 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted7);
		inputAnswerBtn8 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted8);
		inputAnswerBtn9 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted9);
		inputAnswerBtn10 = (ImageButton) v.findViewById(R.id.stage_two_a_item_seleted10);
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
		okBtn = (ImageButton) v.findViewById(R.id.test2a_page_ok_btn);
		nextBtn = (ImageButton) v.findViewById(R.id.test2a_page_next_btn);
		okBtn.setOnClickListener(clickBtnListener);
		nextBtn.setOnClickListener(clickBtnListener);

	}

	private void showProgressDialog() {

		dialog = ProgressDialog.show(L3OnePageSentenceTestFragement.this.activity, null, null, true);

	}

	public void inputAnswerInit() {
		for (int i = 0; i < 10; i++) {
			inputAnwser[i] = "0";
		}
	}

	private void displayOnePage() {

		enableNormalBtn(false);
		okBtn.setEnabled(false);
		inputAnswerInit();
		//showProgressDialog();
		Thread b = new Thread(new LoadBmp());
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
				L3OnePageSentenceTestFragement.this.activity.finish();
			}
		}

	}

	private String getCorrectAnswer(int position) {
		Stage2Conson stage2conson = getOnePageData();
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

	private Stage2Conson getOnePageData() {
		return stage2Conson;

	}
	
	public void setOnePageData(Stage2Conson stage2Conson){
		this.stage2Conson = stage2Conson;
	}
	private class ClickBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.test_page_back_btn:
				L3OnePageSentenceTestFragement.this.activity.finish();
				break;
			case R.id.test2a_page_repeat_btn:
				playsound();
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
				((L3RandomAllTypeTestFragmentActivity) L3OnePageSentenceTestFragement.this.activity).showNextPage();
			}
		}
	}

	private void playsound() {
		final Stage2Conson stage2conson = getOnePageData();
		;
		final String savePath = AuditoryDirectoryHelper
				.ObtainStage2SavePath(stage2conson.getConson());
		L3OnePageSentenceTestFragement.this.activity
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
								savePath + "/" + stage2conson.getSentenceVoicechip());
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
					if(soundExitLoop) break;
				} while (notready);
			}
		});

	}

	private class LoadBmp implements Runnable {
		

		public LoadBmp() {
		
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				Stage2Conson stage2conson = getOnePageData();
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
			ShowUIAfterLoadBmpFinish mShowUIAfterLoadBmpFinish = new ShowUIAfterLoadBmpFinish();
			if (mShowUIAfterLoadBmpFinish == null) Log.d("mShowUIAfterLoadBmpFinish","null");
			//Activity activity = getActivity();
			if (L3OnePageSentenceTestFragement.this.activity == null) Log.d("activity123","null");
			L3OnePageSentenceTestFragement.this.activity.runOnUiThread(mShowUIAfterLoadBmpFinish);
		}

	}

	private View getShowScreen(){
		return (View) v.findViewById(R.id.test2b_show_screen);
	}
	
	private class ShowUIAfterLoadBmpFinish implements Runnable {
		

		public ShowUIAfterLoadBmpFinish() {
			
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//if (consonTxtndicator != null) consonTxtndicator.setText(getConsonString(getConson()));
			if (imgIndicator != null) imgIndicator.setImageResource(L2aTestFragementActivity.getConsonDrawable(getConson()));
			getShowScreen().setVisibility(View.VISIBLE);
			indicatorOfRightAnswer.setVisibility(View.INVISIBLE);
			indicatorOfWrongAnswer.setVisibility(View.INVISIBLE);
			frameOfShowPic.setVisibility(View.VISIBLE);
			// showPic.setImageDrawable(d);
			showPic.setImageBitmap(picture);

			showPic.setVisibility(View.VISIBLE);
			nextBtn.setVisibility(View.INVISIBLE);
			okBtn.setVisibility(View.VISIBLE);

			Stage2Conson stage2conson = getOnePageData();
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
			//dialog.dismiss();
			//dialog = null;

			// play sound
			playsound();

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
					L3OnePageSentenceTestFragement.this.activity
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
			
			//getActivity().runOnUiThread(new TimesUpShowResult());
			L3OnePageSentenceTestFragement.this.activity.runOnUiThread(new TimesUpShowResult());
		}

	}

	private class TimesUpShowResult implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			okBtn.setVisibility(View.INVISIBLE);
			nextBtn.setVisibility(View.VISIBLE);
			enableNormalBtn(false);
			
			//if (counterThread != null) counterThread.interrupt();
			//L2bTestFragementActivity.this.runOnUiThread(new GotoNextPage());
			Stage2Conson stage2conson = getOnePageData();
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

			
			
			((L3RandomAllTypeTestFragmentActivity) L3OnePageSentenceTestFragement.this.activity).setTotalScoreOfTest(getUserInputAnwserResultForOnePage());
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
		Stage2Conson stage2conson = getOnePageData();
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

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (dialog != null) dialog.dismiss();
		stopCountThread();
		soundExitLoop = true;
		if (getPlayer() != null) getPlayer().stop();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (dialog != null) dialog.show();
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if (picture != null) picture.recycle();
	}
	
}
