package hk.org.deaf.auditoryandspeechtrainingapp.level3;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeEightResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFiveResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFourResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeNineResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeOneRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeOneResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSevenResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSixResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeThreeResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeTwoResult;
import hk.org.deaf.auditoryandspeechtrainingapp.result.L3ResultFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDatabaseHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ChallengeResultProcessing;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.SaveChallengeOneRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.UserLoginOrLogoutProcess;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class L3MenuFragementActivity extends FullScreenFragmentActivity {
	private View currentSelectedBtn;

	// challenge1
	Button challenge1Btn;
	ImageView challenge1Sel;
	ImageView challenge1b;
	ImageView challenge1a;
	
	Button challenge2Btn;
	ImageView challenge2Sel;
	ImageView challenge2b;
	ImageView challenge2a;
	
	Button challenge3Btn;
	ImageView challenge3Sel;
	ImageView challenge3b;
	ImageView challenge3a;
	
	Button challenge4Btn;
	ImageView challenge4Sel;
	ImageView challenge4b;
	ImageView challenge4a;
	
	Button challenge5Btn;
	ImageView challenge5Sel;
	ImageView challenge5b;
	ImageView challenge5a;
	
	Button challenge6Btn;
	ImageView challenge6Sel;
	ImageView challenge6b;
	ImageView challenge6a;
	
	Button challenge7Btn;
	ImageView challenge7Sel;
	ImageView challenge7b;
	ImageView challenge7a;
	
	Button challenge8Btn;
	ImageView challenge8Sel;
	ImageView challenge8b;
	ImageView challenge8a;
	
	Button challenge9Btn;
	ImageView challenge9Sel;
	ImageView challenge9b;
	ImageView challenge9a;
	private boolean challenge1on = false;
	private boolean challenge4on = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.level3_menu_revised);
		setContentLayout();
		setSelectionBtnLayout();
		//testOfInputDataToTestChallengeOneFour();
		//testOfChallengeResult(false);
		checkChallengeResult();
		
		
	}
	private void checkChallengeResult(){
		checkChallengeOneResult();
		checkChallengeTwoResult();
		checkChallengeThreeResult();
		checkChallengeFourResult();
		checkChallengeFiveResult();
		checkChallengeSixResult();
		checkChallengeSevenResult();
		checkChallengeEightResult();
		checkChallengeNineResult();
	}
	
	private void checkChallengeOneResult(){
		ChallengeResultProcessing<ChallengeOneResult> processing = new ChallengeResultProcessing<ChallengeOneResult>(this, ChallengeOneResult.class);
		ChallengeOneResult result = processing.getChallengeResult();
		if (result == null){
			if (checkChallengeOneFourResultCompleteness(1)){
			challenge1on = true;
			challenge1b.setVisibility(View.VISIBLE);
			challenge1a.setVisibility(View.INVISIBLE);
			processing.saveChallengeResultToLocalDatabase(true, result);	
			}
		}else {
			if (result.isResultCompleted()){
				challenge1on = true;
				challenge1b.setVisibility(View.VISIBLE);
				challenge1a.setVisibility(View.INVISIBLE);
			}else{
				if (checkChallengeOneFourResultCompleteness(1)){
					challenge1on = true;
					processing.saveChallengeResultToLocalDatabase(true, result);;
					challenge1b.setVisibility(View.VISIBLE);
					challenge1a.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	
	private void checkChallengeTwoResult(){
		ChallengeResultProcessing<ChallengeTwoResult> processing = new ChallengeResultProcessing<ChallengeTwoResult>(this, ChallengeTwoResult.class);
		ChallengeTwoResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				challenge2b.setVisibility(View.VISIBLE);
				challenge2a.setVisibility(View.INVISIBLE);
			}else{
				challenge2b.setVisibility(View.INVISIBLE);
				challenge2a.setVisibility(View.VISIBLE);
			}
		}else{
			challenge2b.setVisibility(View.INVISIBLE);
			challenge2a.setVisibility(View.VISIBLE);
		}
	}
	
	private void checkChallengeThreeResult(){
		ChallengeResultProcessing<ChallengeThreeResult> processing = new ChallengeResultProcessing<ChallengeThreeResult>(this, ChallengeThreeResult.class);
		ChallengeThreeResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				challenge3b.setVisibility(View.VISIBLE);
				challenge3a.setVisibility(View.INVISIBLE);
			}else{
				challenge3b.setVisibility(View.INVISIBLE);
				challenge3a.setVisibility(View.VISIBLE);
			}
		}else{
			challenge3b.setVisibility(View.INVISIBLE);
			challenge3a.setVisibility(View.VISIBLE);
		}
	}
	
	private void checkChallengeFiveResult(){
		ChallengeResultProcessing<ChallengeFiveResult> processing = new ChallengeResultProcessing<ChallengeFiveResult>(this, ChallengeFiveResult.class);
		ChallengeFiveResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				challenge5b.setVisibility(View.VISIBLE);
				challenge5a.setVisibility(View.INVISIBLE);
			}else{
				challenge5b.setVisibility(View.INVISIBLE);
				challenge5a.setVisibility(View.VISIBLE);
			}
		}else{
			challenge5b.setVisibility(View.INVISIBLE);
			challenge5a.setVisibility(View.VISIBLE);
		}
	}
	
	private void checkChallengeSixResult(){
		ChallengeResultProcessing<ChallengeSixResult> processing = new ChallengeResultProcessing<ChallengeSixResult>(this, ChallengeSixResult.class);
		ChallengeSixResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				challenge6b.setVisibility(View.VISIBLE);
				challenge6a.setVisibility(View.INVISIBLE);
			}else{
				challenge6b.setVisibility(View.INVISIBLE);
				challenge6a.setVisibility(View.VISIBLE);
			}
		}else{
			challenge6b.setVisibility(View.INVISIBLE);
			challenge6a.setVisibility(View.VISIBLE);
		}
	}
	private void checkChallengeSevenResult(){
		ChallengeResultProcessing<ChallengeSevenResult> processing = new ChallengeResultProcessing<ChallengeSevenResult>(this, ChallengeSevenResult.class);
		ChallengeSevenResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				challenge7b.setVisibility(View.VISIBLE);
				challenge7a.setVisibility(View.INVISIBLE);
			}else{
				challenge7b.setVisibility(View.INVISIBLE);
				challenge7a.setVisibility(View.VISIBLE);
			}
		}else{
			challenge7b.setVisibility(View.INVISIBLE);
			challenge7a.setVisibility(View.VISIBLE);
		}
	}
	
	private void checkChallengeEightResult(){
		ChallengeResultProcessing<ChallengeEightResult> processing = new ChallengeResultProcessing<ChallengeEightResult>(this, ChallengeEightResult.class);
		ChallengeEightResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				challenge8b.setVisibility(View.VISIBLE);
				challenge8a.setVisibility(View.INVISIBLE);
			}else{
				challenge8b.setVisibility(View.INVISIBLE);
				challenge8a.setVisibility(View.VISIBLE);
			}
		}else{
			challenge8b.setVisibility(View.INVISIBLE);
			challenge8a.setVisibility(View.VISIBLE);
		}
	}
	
	private void checkChallengeNineResult(){
		ChallengeResultProcessing<ChallengeNineResult> processing = new ChallengeResultProcessing<ChallengeNineResult>(this, ChallengeNineResult.class);
		ChallengeNineResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				challenge9b.setVisibility(View.VISIBLE);
				challenge9a.setVisibility(View.INVISIBLE);
			}else{
				challenge9b.setVisibility(View.INVISIBLE);
				challenge9a.setVisibility(View.VISIBLE);
			}
		}else{
			challenge9b.setVisibility(View.INVISIBLE);
			challenge9a.setVisibility(View.VISIBLE);
		}
	}
	
	
	private void checkChallengeFourResult(){
		ChallengeResultProcessing<ChallengeFourResult> processing = new ChallengeResultProcessing<ChallengeFourResult>(this, ChallengeFourResult.class);
		ChallengeFourResult result = processing.getChallengeResult();
		if (result == null){
			if (checkChallengeOneFourResultCompleteness(4)){
			challenge4on = true;
			challenge4b.setVisibility(View.VISIBLE);
			challenge4a.setVisibility(View.INVISIBLE);
			processing.saveChallengeResultToLocalDatabase(true, result);	
			}
		}else {
			if (result.isResultCompleted()){
				challenge4on = true;
				challenge4b.setVisibility(View.VISIBLE);
				challenge4a.setVisibility(View.INVISIBLE);
			}else{
				if (checkChallengeOneFourResultCompleteness(4)){
					challenge4on = true;
					processing.saveChallengeResultToLocalDatabase(true, result);
					challenge4b.setVisibility(View.VISIBLE);
					challenge4a.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	
	private void setContentLayout() {
		//xmlLayoutConnection(R.drawable.box_menu, R.layout.level3_menu, R.drawable.by_menu);

		ImageButton backBtn = (ImageButton) findViewById(R.id.main_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());
		ImageButton startBtn = (ImageButton) findViewById(R.id.menu_start_btn);
		startBtn.setOnClickListener(new SelectionBtnListener());
		
		 Date date = new Date();
		 int dateNo = date.getDay();
		 Log.d("day=", String.valueOf(dateNo));
	}

	private void setSelectionBtnLayout() {
		SelectionBtnListener selectionBtnListener =new SelectionBtnListener(); 
		challenge1Btn = (Button) findViewById(R.id.challenge1_btn);
		challenge1Btn.setOnClickListener(selectionBtnListener);
		challenge1Sel = (ImageView) findViewById(R.id.challenge1_sel);
		challenge1b = (ImageView) findViewById(R.id.challenge1b);
		challenge1a = (ImageView) findViewById(R.id.challenge1a);
		
		challenge2Btn = (Button) findViewById(R.id.challenge2_btn);
		challenge2Btn.setOnClickListener(selectionBtnListener);
		challenge2Sel = (ImageView) findViewById(R.id.challenge2_sel);
		challenge2b = (ImageView) findViewById(R.id.challenge2b);
		challenge2a = (ImageView) findViewById(R.id.challenge2a);
		
		challenge3Btn = (Button) findViewById(R.id.challenge3_btn);
		challenge3Btn.setOnClickListener(selectionBtnListener);
		challenge3Sel = (ImageView) findViewById(R.id.challenge3_sel);
		challenge3b = (ImageView) findViewById(R.id.challenge3b);
		challenge3a = (ImageView) findViewById(R.id.challenge3a);
		
		challenge4Btn = (Button) findViewById(R.id.challenge4_btn);
		challenge4Btn.setOnClickListener(selectionBtnListener);
		challenge4Sel = (ImageView) findViewById(R.id.challenge4_sel);
		challenge4b = (ImageView) findViewById(R.id.challenge4b);
		challenge4a = (ImageView) findViewById(R.id.challenge4a);
		
		challenge5Btn = (Button) findViewById(R.id.challenge5_btn);
		challenge5Btn.setOnClickListener(selectionBtnListener);
		challenge5Sel = (ImageView) findViewById(R.id.challenge5_sel);
		challenge5b = (ImageView) findViewById(R.id.challenge5b);
		challenge5a = (ImageView) findViewById(R.id.challenge5a);
		
		challenge6Btn = (Button) findViewById(R.id.challenge6_btn);
		challenge6Btn.setOnClickListener(selectionBtnListener);
		challenge6Sel = (ImageView) findViewById(R.id.challenge6_sel);
		challenge6b = (ImageView) findViewById(R.id.challenge6b);
		challenge6a = (ImageView) findViewById(R.id.challenge6a);
		
		challenge7Btn = (Button) findViewById(R.id.challenge7_btn);
		challenge7Btn.setOnClickListener(selectionBtnListener);
		challenge7Sel = (ImageView) findViewById(R.id.challenge7_sel);
		challenge7b = (ImageView) findViewById(R.id.challenge7b);
		challenge7a = (ImageView) findViewById(R.id.challenge7a);
		
		challenge8Btn = (Button) findViewById(R.id.challenge8_btn);
		challenge8Btn.setOnClickListener(selectionBtnListener);
		challenge8Sel = (ImageView) findViewById(R.id.challenge8_sel);
		challenge8b = (ImageView) findViewById(R.id.challenge8b);
		challenge8a = (ImageView) findViewById(R.id.challenge8a);
		
		challenge9Btn = (Button) findViewById(R.id.challenge9_btn);
		challenge9Btn.setOnClickListener(selectionBtnListener);
		challenge9Sel = (ImageView) findViewById(R.id.challenge9_sel);
		challenge9b = (ImageView) findViewById(R.id.challenge9b);
		challenge9a = (ImageView) findViewById(R.id.challenge9a);

	}

	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.main_page_back_btn) {
				finish();
			} else if (v.getId() == R.id.menu_start_btn) {
				
				if (currentSelectedBtn != null) {
					switch(currentSelectedBtn.getId()){
						case R.id.challenge1_btn:
							Intent intent1 = new Intent(L3MenuFragementActivity.this, L3ResultFragementActivity.class);
							startActivity(intent1);
							break;
						case R.id.challenge2_btn:
							Intent intent2 = new Intent(L3MenuFragementActivity.this, L3RandomSingleWordTestFragementActivity.class);
							intent2.putExtra("no_of_pages", 20);
							startActivity(intent2);
							break;
						case R.id.challenge3_btn:
							Intent intent3 = new Intent(L3MenuFragementActivity.this, L3RandomSingleWordTestFragementActivity.class);
							intent3.putExtra("no_of_pages", 40);
							startActivity(intent3);
							break;
						case R.id.challenge4_btn:
							Intent intent4 = new Intent(L3MenuFragementActivity.this, L3ResultFragementActivity.class);
							startActivity(intent4);
							break;
						case R.id.challenge5_btn:
							Intent intent5 = new Intent(L3MenuFragementActivity.this, L3RandomSentenceTestFragementActivity.class);
							intent5.putExtra("no_of_pages", 10);
							startActivity(intent5);
							break;
						case R.id.challenge6_btn:
							Intent intent6 = new Intent(L3MenuFragementActivity.this, L3RandomSentenceTestFragementActivity.class);
							intent6.putExtra("no_of_pages", 20);
							startActivity(intent6);
							break;
						case R.id.challenge7_btn:
							Intent intent7 = new Intent(L3MenuFragementActivity.this, L3ResultFragementActivity.class);
							startActivity(intent7);
							break;
						case R.id.challenge8_btn:
							Intent intent8 = new Intent(L3MenuFragementActivity.this, L3RandomAllTypeTestFragmentActivity.class);
							intent8.putExtra("no_of_pages", 20);
							startActivity(intent8);
							break;
						case R.id.challenge9_btn:
							Intent intent9 = new Intent(L3MenuFragementActivity.this, L3RandomAllTypeTestFragmentActivity.class);
							intent9.putExtra("no_of_pages", 40);
							startActivity(intent9);
							break;
							
					}
					
				}
			} else {

				unSelectBtn();
				setSelectionBtn(v);

			}
		}

		void unSelectBtn() {
			if (currentSelectedBtn != null) {
				int id = currentSelectedBtn.getId();
				switch (id) {
				case R.id.challenge1_btn:
					challenge1Sel.setVisibility(View.INVISIBLE);
					//challenge1b.setVisibility(View.INVISIBLE);
					//challenge1a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge2_btn:
					challenge2Sel.setVisibility(View.INVISIBLE);
					//challenge2b.setVisibility(View.INVISIBLE);
					//challenge2a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge3_btn:
					challenge3Sel.setVisibility(View.INVISIBLE);
					//challenge3b.setVisibility(View.INVISIBLE);
					//challenge3a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge4_btn:
					challenge4Sel.setVisibility(View.INVISIBLE);
					//challenge4b.setVisibility(View.INVISIBLE);
					//challenge4a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge5_btn:
					challenge5Sel.setVisibility(View.INVISIBLE);
					//challenge5b.setVisibility(View.INVISIBLE);
					//challenge5a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge6_btn:
					challenge6Sel.setVisibility(View.INVISIBLE);
					//challenge6b.setVisibility(View.INVISIBLE);
					//challenge6a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge7_btn:
					challenge7Sel.setVisibility(View.INVISIBLE);
					//challenge7b.setVisibility(View.INVISIBLE);
					//challenge7a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge8_btn:
					challenge8Sel.setVisibility(View.INVISIBLE);
					//challenge8b.setVisibility(View.INVISIBLE);
					//challenge8a.setVisibility(View.VISIBLE);
					break;
				case R.id.challenge9_btn:
					challenge9Sel.setVisibility(View.INVISIBLE);
					//challenge9b.setVisibility(View.INVISIBLE);
					//challenge9a.setVisibility(View.VISIBLE);
					break;

				}
			}
		}

		void setSelectionBtn(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.challenge1_btn:
				challenge1Sel.setVisibility(View.VISIBLE);
				//challenge1b.setVisibility(View.INVISIBLE);
				//challenge1a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge2_btn:
				challenge2Sel.setVisibility(View.VISIBLE);
				//challenge2b.setVisibility(View.INVISIBLE);
				//challenge2a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge3_btn:
				challenge3Sel.setVisibility(View.VISIBLE);
				//challenge3b.setVisibility(View.INVISIBLE);
				//challenge3a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge4_btn:
				challenge4Sel.setVisibility(View.VISIBLE);
				//challenge4b.setVisibility(View.INVISIBLE);
				//challenge4a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge5_btn:
				challenge5Sel.setVisibility(View.VISIBLE);
				//challenge5b.setVisibility(View.INVISIBLE);
				//challenge5a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge6_btn:
				challenge6Sel.setVisibility(View.VISIBLE);
				//challenge6b.setVisibility(View.INVISIBLE);
				//challenge6a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge7_btn:
				challenge7Sel.setVisibility(View.VISIBLE);
				//challenge7b.setVisibility(View.INVISIBLE);
				//challenge7a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge8_btn:
				challenge8Sel.setVisibility(View.VISIBLE);
				//challenge8b.setVisibility(View.INVISIBLE);
				//challenge8a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;
			case R.id.challenge9_btn:
				challenge9Sel.setVisibility(View.VISIBLE);
				//challenge9b.setVisibility(View.INVISIBLE);
				//challenge9a.setVisibility(View.VISIBLE);
				currentSelectedBtn = v;
				break;	
			}
		}
	}

	private boolean checkChallengeOneFourResultCompleteness(int challengeNo){
		List<ChallengeOneRecord> records = getChallengeOneRecords();
		if (records != null) {
			if (records.size() > 0){
				int day1 = records.get(0).getDay();
				int counter = 1;
				int continueDaycounter=0;
				int maxloop = 0 ;
				if (challengeNo == 1) maxloop = 7;
				else if (challengeNo == 4) maxloop =10;
				while(records.size()>counter && continueDaycounter < maxloop){
					int day2 = records.get(counter).getDay();
					if (day1 - day2 == 0) {
						day1 = day2;
						counter ++;
						continue;
					}else if (Math.abs(day1 - day2) == 1) {
						day1 = day2;
						continueDaycounter +=1;
						counter ++;
						continue;
					}else if (day1 ==1 && day2 == 7){
						day1 = day2;
						continueDaycounter +=1;
						counter ++;
						continue;
						
					}else {
					
						day1 = day2;
						counter ++;
						continueDaycounter = 0;
						continue;
								
					}
				}
				if (challengeNo == 1) return continueDaycounter == 7;
				else if (challengeNo == 4) return continueDaycounter == 10;
				else return false;
			}
			return false;
		}
		return false;
	}
	
	
	
	private List<ChallengeOneRecord> getChallengeOneRecords(){
		UserLoginOrLogoutProcess userLoginOrLogoutProcess = new UserLoginOrLogoutProcess(this, null);
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(
						L3MenuFragementActivity.this,
						AppDatabaseHelper.class);
		try {
			Dao<ChallengeOneRecord, String> dao = helper
					.getDao(ChallengeOneRecord.class);
			QueryBuilder<ChallengeOneRecord, String> queryBuilder =
					  dao.queryBuilder();
			queryBuilder.orderBy("id", false);
			queryBuilder.where().eq("userId", userLoginOrLogoutProcess.getUserId());
			PreparedQuery<ChallengeOneRecord> preparedQuery = queryBuilder.prepare();
			//return dao.queryForEq("userId", userLoginOrLogoutProcess.getUserId());
			return dao.query(preparedQuery);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private void testOfInputDataToTestChallengeOneFour(){
		SaveChallengeOneRecord saveChallengeOneRecord = new SaveChallengeOneRecord(L3MenuFragementActivity.this);
		
		saveChallengeOneRecord.oneDayDone(5);
		saveChallengeOneRecord.oneDayDone(6);
		saveChallengeOneRecord.oneDayDone(7);
		saveChallengeOneRecord.oneDayDone(1);
		saveChallengeOneRecord.oneDayDone(2);
		saveChallengeOneRecord.oneDayDone(3);
		saveChallengeOneRecord.oneDayDone(4);
		saveChallengeOneRecord.oneDayDone(5);
		saveChallengeOneRecord.oneDayDone(6);
		saveChallengeOneRecord.oneDayDone(7);
		saveChallengeOneRecord.oneDayDone(1);
		saveChallengeOneRecord.oneDayDone(2);
		saveChallengeOneRecord.oneDayDone(3);
		saveChallengeOneRecord.oneDayDone(4);
		saveChallengeOneRecord.todayDone();
	}
	
	private void testOfChallengeResult(boolean result){
		ChallengeResultProcessing<ChallengeOneResult> result1 = new ChallengeResultProcessing<ChallengeOneResult>(this, ChallengeOneResult.class);
		ChallengeOneResult challengeOneResult = new ChallengeOneResult();
		result1.saveChallengeResultToLocalDatabase(result, challengeOneResult);
		
		ChallengeResultProcessing<ChallengeTwoResult> result2 = new ChallengeResultProcessing<ChallengeTwoResult>(this, ChallengeTwoResult.class);
		ChallengeTwoResult challengeTwoResult = new ChallengeTwoResult();
		result2.saveChallengeResultToLocalDatabase(result, challengeTwoResult);
		
		ChallengeResultProcessing<ChallengeThreeResult> result3 = new ChallengeResultProcessing<ChallengeThreeResult>(this, ChallengeThreeResult.class);
		ChallengeThreeResult challengeThreeResult = new ChallengeThreeResult();
		result3.saveChallengeResultToLocalDatabase(result, challengeThreeResult);
		
		ChallengeResultProcessing<ChallengeFourResult> result4 = new ChallengeResultProcessing<ChallengeFourResult>(this, ChallengeFourResult.class);
		ChallengeFourResult challengeFourResult = new ChallengeFourResult();
		result4.saveChallengeResultToLocalDatabase(result, challengeFourResult);
		
		ChallengeResultProcessing<ChallengeFiveResult> result5 = new ChallengeResultProcessing<ChallengeFiveResult>(this, ChallengeFiveResult.class);
		ChallengeFiveResult challengeFiveResult = new ChallengeFiveResult();
		result5.saveChallengeResultToLocalDatabase(result, challengeFiveResult);
		
		ChallengeResultProcessing<ChallengeSixResult> result6 = new ChallengeResultProcessing<ChallengeSixResult>(this, ChallengeSixResult.class);
		ChallengeSixResult challengeSixResult = new ChallengeSixResult();
		result6.saveChallengeResultToLocalDatabase(result, challengeSixResult);
		
		ChallengeResultProcessing<ChallengeSevenResult> result7 = new ChallengeResultProcessing<ChallengeSevenResult>(this, ChallengeSevenResult.class);
		ChallengeSevenResult challengeSevenResult = new ChallengeSevenResult();
		result7.saveChallengeResultToLocalDatabase(result, challengeSevenResult);
		
		ChallengeResultProcessing<ChallengeEightResult> result8 = new ChallengeResultProcessing<ChallengeEightResult>(this, ChallengeEightResult.class);
		ChallengeEightResult challengeEightResult = new ChallengeEightResult();
		result8.saveChallengeResultToLocalDatabase(result, challengeEightResult);
		
		ChallengeResultProcessing<ChallengeNineResult> result9 = new ChallengeResultProcessing<ChallengeNineResult>(this, ChallengeNineResult.class);
		ChallengeNineResult challengeNineResult = new ChallengeNineResult();
		result9.saveChallengeResultToLocalDatabase(result, challengeNineResult);
	}
	

}
