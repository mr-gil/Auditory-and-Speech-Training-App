package hk.org.deaf.auditoryandspeechtrainingapp.level3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import hk.org.deaf.auditoryandspeechtrainingapp.CommonFunctionsForTestFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.fragments.L3OnePageSentenceTestFragement;
import hk.org.deaf.auditoryandspeechtrainingapp.fragments.L3OnePageSingleWordTestFragement;
import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeEightResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeNineResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.Stage2Conson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.result.L3ResultFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDataSource;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ChallengeResultProcessing;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ContextStorage;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadTrace;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.GetDataFromLocalDatabase;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ProcessIsFirstData;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService.DownloadProgressUpdateReceiver;

public class L3RandomAllTypeTestFragmentActivity extends
		CommonFunctionsForTestFragmentActivity {

	private List<StageOneConson> locals0ForSingleWord;
	private List<StageOneConson> locals1ForSingleWord;
	private List<StageOneConson> locals2ForSingleWord;
	private List<StageOneConson> locals3ForSingleWord;
	private List<StageOneConson> locals4ForSingleWord;
	private List<StageOneConson> locals5ForSingleWord;
	private List<StageOneConson> locals6ForSingleWord;
	private List<StageOneConson> locals7ForSingleWord;
	private List<StageOneConson> locals8ForSingleWord;

	private List<Stage2Conson> locals0ForSentence;
	private List<Stage2Conson> locals1ForSentence;
	private List<Stage2Conson> locals2ForSentence;
	private List<Stage2Conson> locals3ForSentence;
	private List<Stage2Conson> locals4ForSentence;
	private List<Stage2Conson> locals5ForSentence;
	private List<Stage2Conson> locals6ForSentence;

	private List<Stage2Conson> locals;
	private int noOfQuestion;
	private int noOfPages;
	private int totalScoreOfTest = 0;
	private int currentConson;
	private int currentPageNo;
	private int noOfPageAnswered;
	// private int debugCounter = 0;
	private int consonOfSentence;
	private int noOfPagesForSingleWord;
	// private ProgressDialog dialog;
	private L3OnePageSentenceTestFragement mL3OnePageSentenceTestFragement;
	private L3OnePageSingleWordTestFragement mL3OnePageSingleWordTestFragement;
	private int choice;
	private static Hashtable<Integer, Boolean[]> usedPagesForSentence,
			usedPagesForSingleWord;
	private InitSavedPagesForSentence initSavedPagesForSentence;
	@SuppressWarnings("unused")
	private InitSavedPagesForSingleWord initSavedPagesForSingleWord;

	public InitSavedPagesForSentence getInitSavedPages() {
		return initSavedPagesForSentence;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setToFullScreen();
		setContentView(R.layout.l3_random_all_type_test_container);
		ContextStorage.getInstance().setContext(getApplicationContext());
		setUpDownloadProgressDialog();
		getIsFirstDataFromDataAndSetupForSingleWordTest();
		getIsFirstDataFromDataAndSetupForSentenceTest();
		setupDownloadDialogForSingleWordTest();
		setupDownloadDialogForSentenceTest();
		showDownloadDialogForSingleWord();
	}

	public List<Stage2Conson> getLocals() {
		return locals;
	}

	public void setLocals(List<Stage2Conson> locals) {
		this.locals = locals;
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	/*
	 * private void showSyncDialog() { if (isOnline()) { final Dialog syncDialog
	 * = new Dialog(this);
	 * syncDialog.setContentView(R.layout.test2a_syncdialog_ui); Button
	 * cancelBtn = (Button) syncDialog
	 * .findViewById(R.id.test2a_sync_dialog_cancel_btn);
	 * cancelBtn.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * syncDialog.dismiss(); getAllConsonsDataFromLocalDatabaseForSentence();
	 * getAllConsonsDataFromLocalDatabaseForSingleWord(); setNoOfPages();
	 * getNoOfQuestionFromIntent(); setNoOfPageAnswered(0); processOnePage();
	 * 
	 * } }); Button okBtn = (Button) syncDialog
	 * .findViewById(R.id.test2a_sync_dialog_ok_btn);
	 * okBtn.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * syncDialog.dismiss(); syncData();
	 * 
	 * // L2aTestFragementActivity.setFirstTime(getConsonOfSentence());
	 * Log.d("isFirstTime", String .valueOf(L2aTestFragementActivity
	 * .isFirstTime(getConsonOfSentence())));
	 * 
	 * } }); if ((L2aTestFragementActivity.isFirstTime(0) ||
	 * L2aTestFragementActivity.isFirstTime(1) ||
	 * L2aTestFragementActivity.isFirstTime(2) ||
	 * L2aTestFragementActivity.isFirstTime(3) ||
	 * L2aTestFragementActivity.isFirstTime(4) ||
	 * L2aTestFragementActivity.isFirstTime(5) || L2aTestFragementActivity
	 * .isFirstTime(6)) || (L1aTestFragementActivity.isFirstTime(0) ||
	 * L1aTestFragementActivity.isFirstTime(1) ||
	 * L1aTestFragementActivity.isFirstTime(2) ||
	 * L1aTestFragementActivity.isFirstTime(3) ||
	 * L1aTestFragementActivity.isFirstTime(4) ||
	 * L1aTestFragementActivity.isFirstTime(5) ||
	 * L1aTestFragementActivity.isFirstTime(6) ||
	 * L1aTestFragementActivity.isFirstTime(7) || L1aTestFragementActivity
	 * .isFirstTime(8))) { cancelBtn.setVisibility(View.GONE); } else {
	 * cancelBtn.setVisibility(View.VISIBLE); } syncDialog.show(); } else { if
	 * (!((L2aTestFragementActivity.isFirstTime(0) ||
	 * L2aTestFragementActivity.isFirstTime(1) ||
	 * L2aTestFragementActivity.isFirstTime(2) ||
	 * L2aTestFragementActivity.isFirstTime(3) ||
	 * L2aTestFragementActivity.isFirstTime(4) ||
	 * L2aTestFragementActivity.isFirstTime(5) || L2aTestFragementActivity
	 * .isFirstTime(6)) || (L1aTestFragementActivity .isFirstTime(0) ||
	 * L1aTestFragementActivity.isFirstTime(1) ||
	 * L1aTestFragementActivity.isFirstTime(2) ||
	 * L1aTestFragementActivity.isFirstTime(3) ||
	 * L1aTestFragementActivity.isFirstTime(4) ||
	 * L1aTestFragementActivity.isFirstTime(5) ||
	 * L1aTestFragementActivity.isFirstTime(6) ||
	 * L1aTestFragementActivity.isFirstTime(7) || L1aTestFragementActivity
	 * .isFirstTime(8)))) { getAllConsonsDataFromLocalDatabaseForSentence();
	 * getAllConsonsDataFromLocalDatabaseForSingleWord(); setNoOfPages();
	 * getNoOfQuestionFromIntent(); setNoOfPageAnswered(0); processOnePage(); }
	 * } }
	 */

	/*
	 * private void syncData() { AppDataSource appDataSource =
	 * AppDataSource.getInstance(); showProgressDialog();
	 * appDataSource.getStageOneConson(this, this, new AfterSyncDataAction0(0),
	 * "0"); }
	 */

	/*
	 * private void showProgressDialog() { dialog = ProgressDialog.show(this,
	 * null, null, true); }
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
	 * getDataFromLocalDatabaseForSentence(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStage2Conson( L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new AfterSyncDataAction1(1),
	 * "1");
	 * 
	 * }
	 * 
	 * }
	 */

	public int getConsonOfSentence() {
		return consonOfSentence;
	}

	public void setConsonOfSentence(int consonOfSentence) {
		this.consonOfSentence = consonOfSentence;
	}

	/*
	 * private class AfterSyncDataAction1 implements Runnable { private int
	 * conson;
	 * 
	 * public AfterSyncDataAction1(int conson) { this.conson = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSentence(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStage2Conson( L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new AfterSyncDataAction2(2),
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
	 * getDataFromLocalDatabaseForSentence(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStage2Conson( L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new AfterSyncDataAction3(3),
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
	 * getDataFromLocalDatabaseForSentence(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStage2Conson( L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new AfterSyncDataAction4(4),
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
	 * getDataFromLocalDatabaseForSentence(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStage2Conson( L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new AfterSyncDataAction5(5),
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
	 * getDataFromLocalDatabaseForSentence(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStage2Conson( L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new AfterSyncDataAction6(6),
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
	 * this.conson); L2aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSentence(this.conson);
	 * getAllConsonsDataFromLocalDatabaseForSentence(); syncDataForSingleWord();
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private void syncDataForSingleWord() { AppDataSource appDataSource =
	 * AppDataSource.getInstance(); showProgressDialog();
	 * appDataSource.getStageOneConson(this, this, new
	 * AfterSyncDataAction0ForSingleWord(0), "0"); }
	 */

	/*
	 * private class AfterSyncDataAction0ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction0ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction1ForSingleWord(1), "1");
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction1ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction1ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * 
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction2ForSingleWord(2), "2"); //
	 * getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction2ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction2ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction3ForSingleWord(3), "3"); //
	 * getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction3ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction3ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction4ForSingleWord(4), "4"); //
	 * getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction4ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction4ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction5ForSingleWord(5), "5"); //
	 * getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction5ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction5ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction6ForSingleWord(6), "6"); //
	 * getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction6ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction6ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction7ForSingleWord(7), "7"); //
	 * getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction7ForSingleWord implements Runnable {
	 * private int conson;
	 * 
	 * public AfterSyncDataAction7ForSingleWord(int conson) { this.conson =
	 * conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * Log.d("download123", "download1123 with conson = " + this.conson);
	 * L1aTestFragementActivity.setFirstTime(this.conson);
	 * getDataFromLocalDatabaseForSingleWord(this.conson); AppDataSource
	 * appDataSource = AppDataSource.getInstance();
	 * appDataSource.getStageOneConson(
	 * L3RandomAllTypeTestFragmentActivity.this,
	 * L3RandomAllTypeTestFragmentActivity.this, new
	 * AfterSyncDataAction8ForSingleWord(8), "8"); //
	 * getDataFromLocalDatabase(this.conson);
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private class AfterSyncDataAction8ForSingleWord implements Runnable {
	 * private int consonForSingleWord;
	 * 
	 * public AfterSyncDataAction8ForSingleWord(int conson) {
	 * this.consonForSingleWord = conson; }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * dialog.dismiss(); Log.d("download123", "download1123 with conson = " +
	 * this.consonForSingleWord);
	 * L1aTestFragementActivity.setFirstTime(this.consonForSingleWord);
	 * getDataFromLocalDatabaseForSingleWord(this.consonForSingleWord);
	 * getAllConsonsDataFromLocalDatabaseForSingleWord();
	 * setNoOfPageAnswered(0); setNoOfPages(); getNoOfQuestionFromIntent();
	 * processOnePage(); }
	 * 
	 * }
	 */

	private void getNoOfQuestionFromIntent() {
		int noOfPages = getIntent().getIntExtra("no_of_pages", 0);
		if (noOfPages > getNoOfPages()) {
			setNoOfQuestion(getNoOfPages() / 2);
			// setNoOfQuestion(4);
		} else if (noOfPages == 0) {
			setNoOfQuestion(5);
		} else {
			setNoOfQuestion(noOfPages);

		}
	}

	private void setRandomChooseTestLevel() {
		Random randomChoice = new Random();
		setChoice(randomChoice.nextInt(2));

	}

	private void processOnePage() {
		initFragment();
		setInitSavedPagesForSingleWord(new InitSavedPagesForSingleWord());
		setInitSavedPagesForSentence(new InitSavedPagesForSentence());
		setRandomChooseTestLevel();
		switch (getChoice()) {
		case 0:
			processOnePageForSingleWord();
			break;
		case 1:
			processOnePageForSentence();
			break;
		}
	}

	private void processOnePageForSingleWord() {
		setOnePageForSingleWord();
		displayOnePageForSingleWord(getCurrentConson(), getCurrentPageNo());
	}

	private void processOnePageForSentence() {
		setOnePageForSentence();
		displayOnePageForSentence(getCurrentConson(), getCurrentPageNo());
	}

	private void initFragment() {
		mL3OnePageSentenceTestFragement = new L3OnePageSentenceTestFragement();
		mL3OnePageSingleWordTestFragement = new L3OnePageSingleWordTestFragement();
	}

	private void displayOnePageForSentence(int conson, int pageNo) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		mL3OnePageSentenceTestFragement
				.setOnePageData(getOnePageDataForSentence(conson, pageNo));
		mL3OnePageSentenceTestFragement.setConson(getCurrentConson());
		ft.replace(R.id.all_type_test_container,
				mL3OnePageSentenceTestFragement);
		ft.commit();
	}

	private void displayOnePageForSingleWord(int conson, int pageNo) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		mL3OnePageSingleWordTestFragement
				.setOnePageData(getOnePageDataForSingleWord(conson, pageNo));
		mL3OnePageSingleWordTestFragement.setConson(getCurrentConson());
		ft.replace(R.id.all_type_test_container,
				mL3OnePageSingleWordTestFragement);
		ft.commit();
	}

	public void showNextPage() {
		setNoOfPageAnswered(getNoOfPageAnswered() + 1);
		if (getNoOfPageAnswered() < getNoOfQuestion()) {
			/*
			 * switch (getChoice()){ case 0: FragmentTransaction ft1 =
			 * getSupportFragmentManager().beginTransaction();
			 * ft1.remove(mL3OnePageSingleWordTestFragement); ft1.commit();
			 * setRandomChooseTestLevel(); switch(getChoice()){ case
			 * 0:showNextPageForSingleWord();break; case
			 * 1:showNextPageForSentence();break; } case 1: FragmentTransaction
			 * ft2 = getSupportFragmentManager().beginTransaction();
			 * ft2.remove(mL3OnePageSentenceTestFragement); ft2.commit();
			 * setRandomChooseTestLevel(); switch(getChoice()){ case
			 * 0:showNextPageForSingleWord();break; case
			 * 1:showNextPageForSentence();break; }
			 * 
			 * }
			 */
			setRandomChooseTestLevel();
			switch (getChoice()) {
			case 0:
				showNextPageForSingleWord();
				break;
			case 1:
				showNextPageForSentence();
				break;
			}
		} else {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			switch (getChoice()) {
			case 0:

				ft.remove(mL3OnePageSingleWordTestFragement);
				ft.commit();
				break;
			case 1:
				ft.remove(mL3OnePageSentenceTestFragement);
				ft.commit();
				break;

			}
			if (getTotalScoreOfTest() == getNoOfQuestion()) {
				if (getNoOfQuestion() == 20) {
					ChallengeResultProcessing<ChallengeEightResult> processing = new ChallengeResultProcessing<ChallengeEightResult>(
							L3RandomAllTypeTestFragmentActivity.this,
							ChallengeEightResult.class);
					if (processing.getChallengeResult() != null) {
						processing.saveChallengeResultToLocalDatabase(true,
								processing.getChallengeResult());
					} else {
						processing.saveChallengeResultToLocalDatabase(true,
								new ChallengeEightResult());
					}
				} else if (getNoOfQuestion() == 40) {
					ChallengeResultProcessing<ChallengeNineResult> processing = new ChallengeResultProcessing<ChallengeNineResult>(
							L3RandomAllTypeTestFragmentActivity.this,
							ChallengeNineResult.class);
					if (processing.getChallengeResult() != null) {
						processing.saveChallengeResultToLocalDatabase(true,
								processing.getChallengeResult());
					} else {
						processing.saveChallengeResultToLocalDatabase(true,
								new ChallengeNineResult());
					}
				}
			}
			Intent intent = new Intent(
					L3RandomAllTypeTestFragmentActivity.this,
					L3ResultFragementActivity.class);
			startActivity(intent);
			finish();
		}

	}

	public void showNextPageForSentence() {
		setOnePageForSentence();
		FragmentTransaction ft2 = getSupportFragmentManager()
				.beginTransaction();
		mL3OnePageSentenceTestFragement = new L3OnePageSentenceTestFragement();
		mL3OnePageSentenceTestFragement
				.setOnePageData(getOnePageDataForSentence(getCurrentConson(),
						getCurrentPageNo()));
		mL3OnePageSentenceTestFragement.setConson(getCurrentConson());
		ft2.replace(R.id.all_type_test_container,
				mL3OnePageSentenceTestFragement);
		ft2.commit();
	}

	public void showNextPageForSingleWord() {
		setOnePageForSingleWord();
		FragmentTransaction ft2 = getSupportFragmentManager()
				.beginTransaction();
		mL3OnePageSingleWordTestFragement = new L3OnePageSingleWordTestFragement();
		mL3OnePageSingleWordTestFragement
				.setOnePageData(getOnePageDataForSingleWord(getCurrentConson(),
						getCurrentPageNo()));
		mL3OnePageSingleWordTestFragement.setConson(getCurrentConson());
		ft2.replace(R.id.all_type_test_container,
				mL3OnePageSingleWordTestFragement);
		ft2.commit();
	}

	private Stage2Conson getOnePageDataForSentence(int conson, int pageNo) {
		List<Stage2Conson> locals = getLocalsForSentence(conson);
		return locals.get(pageNo);

	}

	public void setInitSavedPagesForSentence(
			InitSavedPagesForSentence initSavedPages) {
		this.initSavedPagesForSentence = initSavedPages;
	}

	public void setInitSavedPagesForSingleWord(
			InitSavedPagesForSingleWord initSavedPages) {
		this.initSavedPagesForSingleWord = initSavedPages;
	}

	private class InitSavedPagesForSentence {

		public InitSavedPagesForSentence() {
			usedPagesForSentence = new Hashtable<Integer, Boolean[]>();
			Boolean[] a0 = new Boolean[getLocalsForSentence(0).size()];
			Boolean[] a1 = new Boolean[getLocalsForSentence(1).size()];
			Boolean[] a2 = new Boolean[getLocalsForSentence(2).size()];
			Boolean[] a3 = new Boolean[getLocalsForSentence(3).size()];
			Boolean[] a4 = new Boolean[getLocalsForSentence(4).size()];
			Boolean[] a5 = new Boolean[getLocalsForSentence(5).size()];
			Boolean[] a6 = new Boolean[getLocalsForSentence(6).size()];
			initArray(a0);
			initArray(a1);
			initArray(a2);
			initArray(a3);
			initArray(a4);
			initArray(a5);
			initArray(a6);
			usedPagesForSentence.put(0, a0);
			usedPagesForSentence.put(1, a1);
			usedPagesForSentence.put(2, a2);
			usedPagesForSentence.put(3, a3);
			usedPagesForSentence.put(4, a4);
			usedPagesForSentence.put(5, a5);
			usedPagesForSentence.put(6, a6);
		}

		private void initArray(Boolean[] x) {
			for (int i = 0; i < x.length; i++) {
				x[i] = false;
			}
		}
	}

	public List<Stage2Conson> getLocalsForSentence(int conson) {
		switch (conson) {
		case 0:
			return locals0ForSentence;
		case 1:
			return locals1ForSentence;
		case 2:
			return locals2ForSentence;
		case 3:
			return locals3ForSentence;
		case 4:
			return locals4ForSentence;
		case 5:
			return locals5ForSentence;
		case 6:
			return locals6ForSentence;
		}
		return null;
	}

	public void setLocalsForSentence(int conson, List<Stage2Conson> locals) {
		switch (conson) {
		case 0:
			this.locals0ForSentence = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 1:
			this.locals1ForSentence = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 2:
			this.locals2ForSentence = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 3:
			this.locals3ForSentence = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 4:
			this.locals4ForSentence = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 5:
			this.locals5ForSentence = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;
		case 6:
			this.locals6ForSentence = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
					locals);
			break;

		}
	}

	public void setNoOfPages() {
		this.noOfPages = 0;
		for (int i = 0; i < 7; i++) {
			this.noOfPages += getLocalsForSentence(i).size();
		}
		for (int j = 0; j < 9; j++) {
			this.noOfPages += getLocalsForSingleWord(j).size();
		}
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	private void getAllConsonsDataFromLocalDatabaseForSentence() {
		for (int i = 0; i < 7; i++) {
			getDataFromLocalDatabaseForSentence(i);
		}

	}

	private void getDataFromLocalDatabaseForSentence(int conson) {
		GetDataFromLocalDatabase<Stage2Conson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<Stage2Conson>(
				L3RandomAllTypeTestFragmentActivity.this, Stage2Conson.class);
		ArrayList<Stage2Conson> locals = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		setLocalsForSentence(conson, locals);

	}

	private ArrayList<Stage2Conson> getDataFromLocalDatabaseForSentence2(
			int conson) {
		GetDataFromLocalDatabase<Stage2Conson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<Stage2Conson>(
				L3RandomAllTypeTestFragmentActivity.this, Stage2Conson.class);
		ArrayList<Stage2Conson> locals = (ArrayList<Stage2Conson>) new ArrayList<Stage2Conson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		return locals;

	}

	private void setOnePageForSentence() {
		ArrayList<Stage2Conson> locals;
		int pageNo = 0;
		int conson;
		Random consonRandam = new Random();
		Random pageNoRandom = new Random();
		do {

			conson = consonRandam.nextInt(7);

			locals = new ArrayList<Stage2Conson>(
					getDataFromLocalDatabaseForSentence2(conson));
			do {
				boolean b = true;
				for (int i = 0; i < usedPagesForSentence.get(conson).length; i++) {
					Boolean[] a = usedPagesForSentence.get(conson);
					b = b && a[i];
				}
				if (b)
					break;

				if (locals.size() > 1) {

					pageNo = pageNoRandom.nextInt(locals.size() - 1);

				} else {
					pageNo = 0;
				}

			} while (usedPagesForSentence.get(conson)[pageNo]);
		} while (locals.size() <= 0 || locals == null
				|| usedPagesForSentence.get(conson)[pageNo]);
		usedPagesForSentence.get(conson)[pageNo] = true;
		setCurrentConson(conson);
		setCurrentPageNo(pageNo);

	}

	private void setOnePageForSingleWord() {
		ArrayList<StageOneConson> locals;
		int pageNo = 0;
		int conson;
		do {
			Random consonRandam = new Random();
			conson = consonRandam.nextInt(9);

			locals = new ArrayList<StageOneConson>(
					getDataFromLocalDatabase2ForSingleWord(conson));
			if (locals.size() > 1) {
				Random pageNoRandom = new Random();
				pageNo = pageNoRandom.nextInt(locals.size() - 1);
				// setCurrentPageNo(getPageNoRandom().nextInt(locals.size()-1));
			} else {
				pageNo = 0;
			}
		} while (locals.size() <= 0 || locals == null
				|| usedPagesForSingleWord.get(conson)[pageNo]);
		usedPagesForSingleWord.get(conson)[pageNo] = true;
		setCurrentConson(conson);
		setCurrentPageNo(pageNo);

	}

	public int getCurrentConson() {
		return currentConson;
	}

	public void setCurrentConson(int currentConson) {
		this.currentConson = currentConson;
	}

	public int getNoOfQuestion() {
		return noOfQuestion;
	}

	public void setNoOfQuestion(int noOfQuestion) {
		this.noOfQuestion = noOfQuestion;
	}

	public int getTotalScoreOfTest() {
		return totalScoreOfTest;
	}

	public void setTotalScoreOfTest(int totalScoreOfTest) {
		this.totalScoreOfTest += totalScoreOfTest;
	}

	public int getNoOfPageAnswered() {
		return noOfPageAnswered;
	}

	public void setNoOfPageAnswered(int noOfPageAnswered) {
		this.noOfPageAnswered = noOfPageAnswered;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	private void getAllConsonsDataFromLocalDatabaseForSingleWord() {
		for (int i = 0; i < 9; i++) {
			getDataFromLocalDatabaseForSingleWord(i);
		}

	}

	private void getDataFromLocalDatabaseForSingleWord(int conson) {
		GetDataFromLocalDatabase<StageOneConson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneConson>(
				L3RandomAllTypeTestFragmentActivity.this, StageOneConson.class);
		ArrayList<StageOneConson> locals = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		setLocalsForSingleWord(conson, locals);

	}

	public void setLocalsForSingleWord(int conson, List<StageOneConson> locals) {
		switch (conson) {
		case 0:
			this.locals0ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 1:
			this.locals1ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 2:
			this.locals2ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 3:
			this.locals3ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 4:
			this.locals4ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 5:
			this.locals5ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 6:
			this.locals6ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 7:
			this.locals7ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		case 8:
			this.locals8ForSingleWord = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
					locals);
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public List<StageOneConson> getLocalsForSingleWord(int conson) {
		switch (conson) {
		case 0:
			return locals0ForSingleWord;
		case 1:
			return locals1ForSingleWord;
		case 2:
			return locals2ForSingleWord;
		case 3:
			return locals3ForSingleWord;
		case 4:
			return locals4ForSingleWord;
		case 5:
			return locals5ForSingleWord;
		case 6:
			return locals6ForSingleWord;
		case 7:
			return locals7ForSingleWord;
		case 8:
			return locals8ForSingleWord;
		}
		return null;
	}

	private ArrayList<StageOneConson> getDataFromLocalDatabase2ForSingleWord(
			int conson) {
		GetDataFromLocalDatabase<StageOneConson> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneConson>(
				L3RandomAllTypeTestFragmentActivity.this, StageOneConson.class);
		ArrayList<StageOneConson> locals = (ArrayList<StageOneConson>) new ArrayList<StageOneConson>(
				getDataFromLocalDatabase.getDataFromLocalDatabase(conson));
		return locals;
	}

	private StageOneConson getOnePageDataForSingleWord(int conson, int pageNo) {
		List<StageOneConson> locals = getLocalsForSingleWord(conson);
		return locals.get(pageNo);

	}

	public int getNoOfPagesForSingleWord() {
		return noOfPagesForSingleWord;
	}

	public void setNoOfPagesForSingWord() {
		this.noOfPagesForSingleWord = 0;
		for (int i = 0; i < 9; i++) {
			this.noOfPagesForSingleWord += getLocalsForSingleWord(i).size();
		}
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	private class InitSavedPagesForSingleWord {

		public InitSavedPagesForSingleWord() {
			usedPagesForSingleWord = new Hashtable<Integer, Boolean[]>();
			Boolean[] a0 = new Boolean[getLocalsForSingleWord(0).size()];
			Boolean[] a1 = new Boolean[getLocalsForSingleWord(1).size()];
			Boolean[] a2 = new Boolean[getLocalsForSingleWord(2).size()];
			Boolean[] a3 = new Boolean[getLocalsForSingleWord(3).size()];
			Boolean[] a4 = new Boolean[getLocalsForSingleWord(4).size()];
			Boolean[] a5 = new Boolean[getLocalsForSingleWord(5).size()];
			Boolean[] a6 = new Boolean[getLocalsForSingleWord(6).size()];
			Boolean[] a7 = new Boolean[getLocalsForSingleWord(7).size()];
			Boolean[] a8 = new Boolean[getLocalsForSingleWord(8).size()];
			initArray(a0);
			initArray(a1);
			initArray(a2);
			initArray(a3);
			initArray(a4);
			initArray(a5);
			initArray(a6);
			initArray(a7);
			initArray(a8);
			usedPagesForSingleWord.put(0, a0);
			usedPagesForSingleWord.put(1, a1);
			usedPagesForSingleWord.put(2, a2);
			usedPagesForSingleWord.put(3, a3);
			usedPagesForSingleWord.put(4, a4);
			usedPagesForSingleWord.put(5, a5);
			usedPagesForSingleWord.put(6, a6);
			usedPagesForSingleWord.put(7, a7);
			usedPagesForSingleWord.put(8, a8);
		}

		private void initArray(Boolean[] x) {
			for (int i = 0; i < x.length; i++) {
				x[i] = false;
			}
		}

	}

	private class AfterFinishDownloadActionForSingleWordTest implements
			DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
			DownloadTrace.getInstance().addOneDownloadsComplete();
			L3RandomAllTypeTestFragmentActivity.this
					.runOnUiThread(new ShowDownloadProgressForSingleWordTest());
			L3RandomAllTypeTestFragmentActivity.this
					.runOnUiThread(new DismissDialogForSingleWordTest());
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			DownloadTrace.getInstance().addOneDownload();
			L3RandomAllTypeTestFragmentActivity.this
					.runOnUiThread(new ShowDownloadProgressForSingleWordTest());
		}

	}

	class ShowDownloadProgressForSingleWordTest implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			getDownloadProgressTxt().setText(
					DownloadTrace.getInstance().getNoOfDownloadsComplete()
							+ "/"
							+ DownloadTrace.getInstance().getNoOfDownloads());
		}

	}

	class DismissDialogForSingleWordTest implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (DownloadTrace.getInstance().getNoOfDownloadsComplete() == DownloadTrace
					.getInstance().getNoOfDownloads()) {
				L1aTestFragementActivity
						.setFirstTime(getCurrentConsonOfNewDownloadMethod());
				saveIsFirstDataToDatabaseForSingleWordTest();
				setCurrentConsonOfNewDownloadMethod(getCurrentConsonOfNewDownloadMethod() + 1);
				if (noOfConsonOfDownload > getCurrentConsonOfNewDownloadMethod()) {
					DownloadTrace.getInstance().setNoOfDownloads(0);
					DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
					syncDataForNewDownloadMethodOfSingleWordTest();
				} else {
					downloadProgressDialog.dismiss();
					setupDownloadDialogForSentenceTest();
				}
			}

		}

	}

	private DownloadService downloadServiceForSingleWordTest;

	public ServiceConnection mConnectionForSingleWordTest = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			downloadServiceForSingleWordTest = ((DownloadService.DownloadBinder) binder)
					.getService();
			downloadServiceForSingleWordTest
					.setUpdateReceiver(new AfterFinishDownloadActionForSingleWordTest());
		}

		public void onServiceDisconnected(ComponentName className) {
			downloadServiceForSingleWordTest.removeUpdateReceiver(null);
			downloadServiceForSingleWordTest = null;
		}
	};

	private void bindServiceForSingleWordTest() {

		bindService(new Intent(this, DownloadService.class),
				mConnectionForSingleWordTest, BIND_AUTO_CREATE);

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

	private Dialog downloadDialogForSingWord;

	private void setupDownloadDialogForSingleWordTest() {

		downloadDialogForSingWord = new Dialog(this);
		downloadDialogForSingWord
				.setContentView(R.layout.test3_syncdialog_ui_for_single_word_test);
		Button remainingBtn = (Button) downloadDialogForSingWord
				.findViewById(R.id.test2a_sync_dialog_remaining_btn);
		remainingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				downloadDialogForSingWord.dismiss();
				setNoOfConsonOfDownload(9);
				setCurrentConsonOfNewDownloadMethod(0);
				setDownloadOnlyRemainingFlag(true);
				setDownloadFlap(true);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				showDownloadProgressDialog();
				bindServiceForSingleWordTest();
				syncDataForNewDownloadMethodOfSingleWordTest();
			}
		});
		remainingBtn.setVisibility(View.GONE);
		Button cancelBtn = (Button) downloadDialogForSingWord
				.findViewById(R.id.test2a_sync_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialogForSingWord.dismiss();
				downloadDialogForSentence.show();

			}
		});
		Button okBtn = (Button) downloadDialogForSingWord
				.findViewById(R.id.test2a_sync_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialogForSingWord.dismiss();
				setCurrentConsonOfNewDownloadMethod(0);
				setNoOfConsonOfDownload(9);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				setDownloadOnlyRemainingFlag(false);
				setDownloadFlap(true);
				showDownloadProgressDialog();
				bindServiceForSingleWordTest();
				syncDataForNewDownloadMethodOfSingleWordTest();

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

	private void showDownloadDialogForSingleWord() {
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
				downloadDialogForSingWord.show();
			} else {
				showDownloadDialogForSentence();

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
				showDownloadDialogForSentence();

			}
		}

	}

	private void syncDataForNewDownloadMethodOfSingleWordTest() {
		if (isDownloadFlap()) {
			if (isDownloadOnlyRemainingFlag()) {
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
						setupDownloadDialogForSentenceTest();
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

	private class AfterFinishDownloadActionForSentenceTest implements
			DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
			DownloadTrace.getInstance().addOneDownloadsComplete();
			L3RandomAllTypeTestFragmentActivity.this
					.runOnUiThread(new ShowDownloadProgressForSentenceTest());
			L3RandomAllTypeTestFragmentActivity.this
					.runOnUiThread(new DismissDialogForSentenceTest());
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			DownloadTrace.getInstance().addOneDownload();
			L3RandomAllTypeTestFragmentActivity.this
					.runOnUiThread(new ShowDownloadProgressForSentenceTest());
		}

	}

	class ShowDownloadProgressForSentenceTest implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			getDownloadProgressTxt().setText(
					DownloadTrace.getInstance().getNoOfDownloadsComplete()
							+ "/"
							+ DownloadTrace.getInstance().getNoOfDownloads());
		}

	}

	class DismissDialogForSentenceTest implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (DownloadTrace.getInstance().getNoOfDownloadsComplete() == DownloadTrace
					.getInstance().getNoOfDownloads()) {
				L2aTestFragementActivity
						.setFirstTime(getCurrentConsonOfNewDownloadMethod());
				saveIsFirstDataToDatabaseForSentenceTest();
				setCurrentConsonOfNewDownloadMethod(getCurrentConsonOfNewDownloadMethod() + 1);
				if (noOfConsonOfDownload > getCurrentConsonOfNewDownloadMethod()) {
					DownloadTrace.getInstance().setNoOfDownloads(0);
					DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
					syncDataForNewDownloadMethodForSentenceTest();
				} else {
					downloadProgressDialog.dismiss();
					getAllConsonsDataFromLocalDatabaseForSentence();
					getAllConsonsDataFromLocalDatabaseForSingleWord();
					setNoOfPages();
					getNoOfQuestionFromIntent();
					setNoOfPageAnswered(0);
					processOnePage();
				}
			}

		}

	}

	private DownloadService downloadServiceForSentenceTest;

	public ServiceConnection mConnectionForSentenceTest = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			downloadServiceForSentenceTest = ((DownloadService.DownloadBinder) binder)
					.getService();
			downloadServiceForSentenceTest
					.setUpdateReceiver(new AfterFinishDownloadActionForSentenceTest());
		}

		public void onServiceDisconnected(ComponentName className) {
			downloadServiceForSentenceTest.removeUpdateReceiver(null);
			downloadServiceForSentenceTest = null;
		}
	};

	private void bindServiceForSentenceTest() {

		bindService(new Intent(this, DownloadService.class),
				mConnectionForSentenceTest, BIND_AUTO_CREATE);

	}

	private void syncDataForNewDownloadMethodForSentenceTest() {
		if (isDownloadFlap()) {
			if (isDownloadOnlyRemainingFlag()) {
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
						getAllConsonsDataFromLocalDatabaseForSentence();
						getAllConsonsDataFromLocalDatabaseForSingleWord();
						setNoOfPages();
						getNoOfQuestionFromIntent();
						setNoOfPageAnswered(0);
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

	private Dialog downloadDialogForSentence;

	private void setupDownloadDialogForSentenceTest() {

		downloadDialogForSentence = new Dialog(this);
		downloadDialogForSentence
				.setContentView(R.layout.test3_syncdialog_ui_for_sentence_test);
		Button remainingBtn = (Button) downloadDialogForSentence
				.findViewById(R.id.test2a_sync_dialog_remaining_btn);
		remainingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				downloadDialogForSentence.dismiss();
				setNoOfConsonOfDownload(7);
				setCurrentConsonOfNewDownloadMethod(0);
				setDownloadOnlyRemainingFlag(true);
				setDownloadFlap(true);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				showDownloadProgressDialog();
				bindServiceForSentenceTest();
				syncDataForNewDownloadMethodForSentenceTest();
			}
		});
		remainingBtn.setVisibility(View.GONE);
		Button cancelBtn = (Button) downloadDialogForSentence
				.findViewById(R.id.test2a_sync_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialogForSentence.dismiss();
				getAllConsonsDataFromLocalDatabaseForSentence();
				getAllConsonsDataFromLocalDatabaseForSingleWord();
				setNoOfPages();
				getNoOfQuestionFromIntent();
				setNoOfPageAnswered(0);
				processOnePage();

			}
		});
		Button okBtn = (Button) downloadDialogForSentence
				.findViewById(R.id.test2a_sync_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadDialogForSentence.dismiss();
				setCurrentConsonOfNewDownloadMethod(0);
				setNoOfConsonOfDownload(7);
				DownloadTrace.getInstance().setNoOfDownloads(0);
				DownloadTrace.getInstance().setNoOfDownloadsComplete(0);
				setDownloadOnlyRemainingFlag(false);
				setDownloadFlap(true);
				showDownloadProgressDialog();
				bindServiceForSentenceTest();
				syncDataForNewDownloadMethodForSentenceTest();
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

	private void showDownloadDialogForSentence() {
		if (isOnline()) {
			if (L2aTestFragementActivity.isFirstTime(0)
					|| L2aTestFragementActivity.isFirstTime(1)
					|| L2aTestFragementActivity.isFirstTime(2)
					|| L2aTestFragementActivity.isFirstTime(3)
					|| L2aTestFragementActivity.isFirstTime(4)
					|| L2aTestFragementActivity.isFirstTime(5)
					|| L2aTestFragementActivity.isFirstTime(6)) {
				downloadDialogForSentence.show();
			} else {
				getAllConsonsDataFromLocalDatabaseForSentence();
				getAllConsonsDataFromLocalDatabaseForSingleWord();
				setNoOfPages();
				getNoOfQuestionFromIntent();
				setNoOfPageAnswered(0);
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
				getAllConsonsDataFromLocalDatabaseForSentence();
				getAllConsonsDataFromLocalDatabaseForSingleWord();
				setNoOfPages();
				getNoOfQuestionFromIntent();
				setNoOfPageAnswered(0);
				processOnePage();

			}
		}
	}

	private void saveIsFirstDataToDatabaseForSentenceTest() {
		GetDataFromLocalDatabase<StageTwoIsFirst> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageTwoIsFirst>(
				this, StageTwoIsFirst.class);
		ProcessIsFirstData<StageTwoIsFirst> processIsFirstData = new ProcessIsFirstData<StageTwoIsFirst>(
				getDataFromLocalDatabase);
		processIsFirstData.saveDataToLocalDataBase(new StageTwoIsFirst());
	}

	private void getIsFirstDataFromDataAndSetupForSentenceTest() {
		GetDataFromLocalDatabase<StageTwoIsFirst> getDataFromLocalDatabase1 = new GetDataFromLocalDatabase<StageTwoIsFirst>(
				this, StageTwoIsFirst.class);
		ProcessIsFirstData<StageTwoIsFirst> processIsFirstData1 = new ProcessIsFirstData<StageTwoIsFirst>(
				getDataFromLocalDatabase1);
		processIsFirstData1.getDataFromDataBase();
	}

	private void saveIsFirstDataToDatabaseForSingleWordTest() {
		GetDataFromLocalDatabase<StageOneIsFirst> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneIsFirst>(
				this, StageOneIsFirst.class);
		ProcessIsFirstData<StageOneIsFirst> processIsFirstData = new ProcessIsFirstData<StageOneIsFirst>(
				getDataFromLocalDatabase);
		processIsFirstData.saveDataToLocalDataBase(new StageOneIsFirst());
	}

	private void getIsFirstDataFromDataAndSetupForSingleWordTest() {
		GetDataFromLocalDatabase<StageOneIsFirst> getDataFromLocalDatabase1 = new GetDataFromLocalDatabase<StageOneIsFirst>(
				this, StageOneIsFirst.class);
		ProcessIsFirstData<StageOneIsFirst> processIsFirstData1 = new ProcessIsFirstData<StageOneIsFirst>(
				getDataFromLocalDatabase1);
		processIsFirstData1.getDataFromDataBase();
	}

	private void unBindDownloadService() {
		if (DownloadTrace.getInstance().getNoOfDownloads() > 0) {
			setDownloadFlap(false);
			if (downloadServiceForSentenceTest != null) {
				downloadServiceForSentenceTest.clearDownloads(DownloadTrace
						.getInstance().getNoOfDownloads()
						- DownloadTrace.getInstance()
								.getNoOfDownloadsComplete());
				if (mConnectionForSentenceTest != null) {
					try {
						unbindService(mConnectionForSentenceTest);
						
					}
					catch (IllegalArgumentException e){
						
					}
					
					Intent intentForSentenceTest = new Intent(this, DownloadService.class);
					stopService(intentForSentenceTest);
					
					
				}
				
				
			}

			if (downloadServiceForSingleWordTest != null) {
				downloadServiceForSingleWordTest.clearDownloads(DownloadTrace
						.getInstance().getNoOfDownloads()
						- DownloadTrace.getInstance()
								.getNoOfDownloadsComplete());
				
				if (mConnectionForSingleWordTest != null) {
					try {
						unbindService(mConnectionForSingleWordTest);
					}
					catch (IllegalArgumentException e){
						
					}
					Intent intentForSingleWordTest = new Intent(this, DownloadService.class);
					stopService(intentForSingleWordTest);
					
				}
				
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
			downloadDialogForSingWord.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
