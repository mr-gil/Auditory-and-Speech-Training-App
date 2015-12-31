package hk.org.deaf.auditoryandspeechtrainingapp.result;

import hk.org.deaf.auditoryandspeechtrainingapp.FullScreenFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeEightResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFiveResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFourResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeNineResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeOneResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSevenResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSixResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeThreeResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeTwoResult;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ChallengeResultProcessing;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class L3ResultFragementActivity extends FullScreenFragmentActivity {

	private ImageView chanllengeOneUnfinshed;
	private ImageView chanllengeOnefinshed;
	private ImageView chanllengeTwoUnfinshed;
	private ImageView chanllengeTwofinshed;
	private ImageView chanllengeThreeUnfinshed;
	private ImageView chanllengeThreefinshed;
	private ImageView chanllengeFourUnfinshed;
	private ImageView chanllengeFourfinshed;
	private ImageView chanllengeFiveUnfinshed;
	private ImageView chanllengeFivefinshed;
	private ImageView chanllengeSixUnfinshed;
	private ImageView chanllengeSixfinshed;
	private ImageView chanllengeSevenUnfinshed;
	private ImageView chanllengeSevenfinshed;
	private ImageView chanllengeEightUnfinshed;
	private ImageView chanllengeEightfinshed;
	private ImageView chanllengeNineUnfinshed;
	private ImageView chanllengeNinefinshed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.result_level3_revised);
		setButtonLayout();
		setContentLayout();
		//testOfChallengeResult(false);
		checkChallengeResult();
	}

	private void setButtonLayout() {
		// xmlLayoutConnection(R.drawable.table_result, R.layout.level3_result,
		// R.drawable.by_menu);

		ImageButton backBtn = (ImageButton) findViewById(R.id.result_page_back_btn);
		backBtn.setOnClickListener(new SelectionBtnListener());

	}

	private void setContentLayout() {
		chanllengeOneUnfinshed = (ImageView) findViewById(R.id.challenge_one_unfinish);
		chanllengeOnefinshed = (ImageView) findViewById(R.id.challenge_one_finish);
		chanllengeTwoUnfinshed = (ImageView) findViewById(R.id.challenge_two_unfinish);
		chanllengeTwofinshed = (ImageView) findViewById(R.id.challenge_two_finish);
		chanllengeThreeUnfinshed = (ImageView) findViewById(R.id.challenge_three_unfinish);
		chanllengeThreefinshed = (ImageView) findViewById(R.id.challenge_three_finish);
		chanllengeFourUnfinshed = (ImageView) findViewById(R.id.challenge_four_unfinish);
		chanllengeFourfinshed = (ImageView) findViewById(R.id.challenge_four_finish);
		chanllengeFiveUnfinshed = (ImageView) findViewById(R.id.challenge_five_unfinish);
		chanllengeFivefinshed = (ImageView) findViewById(R.id.challenge_five_finish);
		chanllengeSixUnfinshed = (ImageView) findViewById(R.id.challenge_six_unfinish);
		chanllengeSixfinshed = (ImageView) findViewById(R.id.challenge_six_finish);
		chanllengeSevenUnfinshed = (ImageView) findViewById(R.id.challenge_seven_unfinish);
		chanllengeSevenfinshed = (ImageView) findViewById(R.id.challenge_seven_finish);
		chanllengeEightUnfinshed = (ImageView) findViewById(R.id.challenge_eight_unfinish);
		chanllengeEightfinshed = (ImageView) findViewById(R.id.challenge_eight_finish);
		chanllengeNineUnfinshed = (ImageView) findViewById(R.id.challenge_nine_unfinish);
		chanllengeNinefinshed = (ImageView) findViewById(R.id.challenge_nine_finish);
	}

	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

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
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeOneUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeOnefinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeOneUnfinshed.setVisibility(View.VISIBLE);
				chanllengeOnefinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeOneUnfinshed.setVisibility(View.VISIBLE);
			chanllengeOnefinshed.setVisibility(View.INVISIBLE);
		}
	}
	
	private void checkChallengeTwoResult(){
		ChallengeResultProcessing<ChallengeTwoResult> processing = new ChallengeResultProcessing<ChallengeTwoResult>(this, ChallengeTwoResult.class);
		ChallengeTwoResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeTwoUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeTwofinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeTwoUnfinshed.setVisibility(View.VISIBLE);
				chanllengeTwofinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeTwoUnfinshed.setVisibility(View.VISIBLE);
			chanllengeTwofinshed.setVisibility(View.INVISIBLE);
		}
	}
	
	private void checkChallengeThreeResult(){
		ChallengeResultProcessing<ChallengeThreeResult> processing = new ChallengeResultProcessing<ChallengeThreeResult>(this, ChallengeThreeResult.class);
		ChallengeThreeResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeThreeUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeThreefinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeThreeUnfinshed.setVisibility(View.VISIBLE);
				chanllengeThreefinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeThreeUnfinshed.setVisibility(View.VISIBLE);
			chanllengeThreefinshed.setVisibility(View.INVISIBLE);
		}
	}
	
	private void checkChallengeFourResult(){
		ChallengeResultProcessing<ChallengeFourResult> processing = new ChallengeResultProcessing<ChallengeFourResult>(this, ChallengeFourResult.class);
		ChallengeFourResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeFourUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeFourfinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeFourUnfinshed.setVisibility(View.VISIBLE);
				chanllengeFourfinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeFourUnfinshed.setVisibility(View.VISIBLE);
			chanllengeFourfinshed.setVisibility(View.INVISIBLE);
		}
	}
	
	private void checkChallengeFiveResult(){
		ChallengeResultProcessing<ChallengeFiveResult> processing = new ChallengeResultProcessing<ChallengeFiveResult>(this, ChallengeFiveResult.class);
		ChallengeFiveResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeFiveUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeFivefinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeFiveUnfinshed.setVisibility(View.VISIBLE);
				chanllengeFivefinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeFiveUnfinshed.setVisibility(View.VISIBLE);
			chanllengeFivefinshed.setVisibility(View.INVISIBLE);
		}
	}
	
	private void checkChallengeSixResult(){
		ChallengeResultProcessing<ChallengeSixResult> processing = new ChallengeResultProcessing<ChallengeSixResult>(this, ChallengeSixResult.class);
		ChallengeSixResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeSixUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeSixfinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeSixUnfinshed.setVisibility(View.VISIBLE);
				chanllengeSixfinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeSixUnfinshed.setVisibility(View.VISIBLE);
			chanllengeSixfinshed.setVisibility(View.INVISIBLE);
		}
	}
	private void checkChallengeSevenResult(){
		ChallengeResultProcessing<ChallengeSevenResult> processing = new ChallengeResultProcessing<ChallengeSevenResult>(this, ChallengeSevenResult.class);
		ChallengeSevenResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeSevenUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeSevenfinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeSevenUnfinshed.setVisibility(View.VISIBLE);
				chanllengeSevenfinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeSevenUnfinshed.setVisibility(View.VISIBLE);
			chanllengeSevenfinshed.setVisibility(View.INVISIBLE);
		}
	}
	
	private void checkChallengeEightResult(){
		ChallengeResultProcessing<ChallengeEightResult> processing = new ChallengeResultProcessing<ChallengeEightResult>(this, ChallengeEightResult.class);
		ChallengeEightResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeEightUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeEightfinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeEightUnfinshed.setVisibility(View.VISIBLE);
				chanllengeEightfinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeEightUnfinshed.setVisibility(View.VISIBLE);
			chanllengeEightfinshed.setVisibility(View.INVISIBLE);
		}
	}
	
	private void checkChallengeNineResult(){
		ChallengeResultProcessing<ChallengeNineResult> processing = new ChallengeResultProcessing<ChallengeNineResult>(this, ChallengeNineResult.class);
		ChallengeNineResult result = processing.getChallengeResult();
		if (result != null){
			if (result.isResultCompleted()){
				chanllengeNineUnfinshed.setVisibility(View.INVISIBLE);
				chanllengeNinefinshed.setVisibility(View.VISIBLE);
			}else{
				chanllengeNineUnfinshed.setVisibility(View.VISIBLE);
				chanllengeNinefinshed.setVisibility(View.INVISIBLE);
			}
		}else{
			chanllengeNineUnfinshed.setVisibility(View.VISIBLE);
			chanllengeNinefinshed.setVisibility(View.INVISIBLE);
		}
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
