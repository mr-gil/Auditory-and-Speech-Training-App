package hk.org.deaf.auditoryandspeechtrainingapp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.json.JSONObject;


import hk.com.playmore.syncframework.helper.PMDataSource;
import hk.com.playmore.syncframework.service.PMSyncService;
import hk.org.deaf.auditoryandspeechtrainingapp.interfaces.ShowUserLayout;
import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1aHelpFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1bMenuFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2aHelpFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2bMenuFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level3.L3MenuFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.PassToStage2StateRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.result.ResultMenuFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDataSource;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ContextStorage;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.GetDataFromLocalDatabase;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PMNameValuePair;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ProcessPassToStage2StateRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.RunnableJSONObject;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.UserLoginOrLogoutProcess;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragmentActivity extends FullScreenFragmentActivity implements ShowUserLayout{
	public static String pathPh, pathTh, pathKh, pathS, pathTsh,
				pathNg, pathN;
	public static String pathPhVsP, pathPhVsTh, pathThVsT, pathThVsKh, 
				pathKhVsK, pathSVsTs, pathTshVsTs, pathNVsM, pathNgVsN;
	
	private View currentSelectedBtn;
	private ImageButton loginBtn, logoutBtn;
	private ImageView showUsernameIcon, usernameInputIndicator,
						showEmailIcon, emailInputIndicator;
	private TextView usernameShow, emailShow;
	private EditText usernameInput, emailInput;
	//private SyncResultReceiver mSyncResultReceiver = new SyncResultReceiver(new Handler(), this);
	private BtnListener btnListener;
	private UserLoginOrLogoutProcess userLoginOrLogoutProcess;
	private Dialog loginNameAlertDialog, loginPasswordAlertDialog;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.main_page_revised);
		setContentLayout();
		initInputAlertDialog();
		processUserLoggedInOrOut();
		//startDownloadService();
		ContextStorage.getInstance().setContext(getApplicationContext());
		ContextStorage.getInstance().createNeededDir();
		
	}
	
	private void processUserLoggedInOrOut(){
		userLoginOrLogoutProcess = new UserLoginOrLogoutProcess(this, this);
		userLoginOrLogoutProcess.showUserPage();
	}
	
	private void setContentLayout() {
		showUsernameIcon = (ImageView) findViewById(R.id.user_logged_in_icon);
		usernameInputIndicator = (ImageView) findViewById(R.id.indicate_user_to_login_username);
		usernameShow = (TextView) findViewById(R.id.username_show);
		emailShow = (TextView) findViewById(R.id.email_show);
		showEmailIcon = (ImageView) findViewById(R.id.show_email_icon);
		emailInputIndicator = (ImageView) findViewById(R.id.input_email_indicator);
		
		TextView usernameShow = (TextView) findViewById(R.id.username_show);
		TextView emailShow = (TextView) findViewById(R.id.email_show);
		usernameInput = (EditText) findViewById(R.id.username_input);
		emailInput = (EditText) findViewById(R.id.email_input);
		Display display = getWindowManager().getDefaultDisplay();
		int SCREEN_HEIGHT = display.getHeight();
		int sizeInput = (int) (SCREEN_HEIGHT * 0.3/6.7);
		usernameShow.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeInput);
		usernameInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeInput);
		emailShow.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeInput);
		emailInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeInput);
		
		btnListener = new BtnListener();
		
		ImageButton level1aBtn = (ImageButton) findViewById(R.id.main_page_level1a_sel_item);
		level1aBtn.setOnClickListener(btnListener);

		ImageButton level1bBtn = (ImageButton) findViewById(R.id.main_page_level1b_sel_item);
		level1bBtn.setOnClickListener(btnListener);

		ImageButton level2aBtn = (ImageButton) findViewById(R.id.main_page_level2a_sel_item);
		level2aBtn.setOnClickListener(btnListener);

		ImageButton level2bBtn = (ImageButton) findViewById(R.id.main_page_level2b_sel_item);
		level2bBtn.setOnClickListener(btnListener);

		ImageButton level3Btn = (ImageButton) findViewById(R.id.main_page_level3_sel_item);
		level3Btn.setOnClickListener(btnListener);

		ImageButton nextBtn = (ImageButton) findViewById(R.id.main_page_next_btn);
		nextBtn.setOnClickListener(btnListener);
		
		ImageButton resultBtn = (ImageButton) findViewById(R.id.main_page_result_btn);
		resultBtn.setOnClickListener(btnListener);
		
		ImageButton intructionBtn = (ImageButton) findViewById(R.id.main_page_instruction_btn);
		intructionBtn.setOnClickListener(btnListener);
		
		ImageButton aboutUsBtn = (ImageButton) findViewById(R.id.main_page_about_btn);
		aboutUsBtn.setOnClickListener(btnListener);
		
		loginBtn = (ImageButton) findViewById(R.id.main_page_login_btn);
		loginBtn.setOnClickListener(btnListener);
		
		logoutBtn = (ImageButton) findViewById(R.id.main_page_logout_btn);
		logoutBtn.setOnClickListener(btnListener);
		
		
				
	}
	private void initInputAlertDialog(){
		loginNameAlertDialog = new Dialog(this);
		loginNameAlertDialog.setContentView(R.layout.main_page_name_input_alert_dialog);
		Button okBtn = (Button) loginNameAlertDialog
				.findViewById(R.id.main_page_login_alert_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				loginNameAlertDialog.dismiss();
				
			}
		});
		loginPasswordAlertDialog = new Dialog(this);
		loginPasswordAlertDialog.setContentView(R.layout.main_page_password_input_alert_dialog);
		Button passwordOkBtn = (Button) loginPasswordAlertDialog
				.findViewById(R.id.main_page_login_password_alert_dialog_ok_btn);
		passwordOkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				loginPasswordAlertDialog.dismiss();
				
			}
		});
	}
	
	private boolean getStateOfPassStageOne(){
		UserLoginOrLogoutProcess login = new UserLoginOrLogoutProcess(this, null);
		GetDataFromLocalDatabase<PassToStage2StateRecord> process 
		 	= new GetDataFromLocalDatabase<PassToStage2StateRecord>(this, PassToStage2StateRecord.class);
		ProcessPassToStage2StateRecord pass = new ProcessPassToStage2StateRecord(process, login.getUserId());
		pass.getPassToStage2StateRecord(login.getUserId());
		return pass.getPassToStage2StateRecord(login.getUserId());
	}
	class BtnListener implements OnClickListener {
		private final boolean TESTMODE = false;
		private String usernameString, emailString;
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v.getId() == R.id.main_page_next_btn) {
				if (userLoginOrLogoutProcess.isUserLoggedIn()){
					if (currentSelectedBtn != null){
						if (currentSelectedBtn.getId() == R.id.main_page_level1a_sel_item) {
							Intent intent = new Intent(MainFragmentActivity.this,
									L1aHelpFragementActivity.class);
							startActivity(intent);
						}else if ((currentSelectedBtn.getId() == R.id.main_page_level2a_sel_item) && getStateOfPassStageOne()) {
							Intent intent = new Intent(MainFragmentActivity.this,
									L2aHelpFragementActivity.class);
							startActivity(intent);
						}else if (currentSelectedBtn.getId() == R.id.main_page_level1b_sel_item) {
							Intent intent = new Intent(MainFragmentActivity.this,
									L1bMenuFragementActivity.class);
							startActivity(intent);
						}else if ((currentSelectedBtn.getId() == R.id.main_page_level2b_sel_item) && getStateOfPassStageOne()){
							Intent intent = new Intent(MainFragmentActivity.this,
									L2bMenuFragementActivity.class);
							startActivity(intent);
						}else if ((currentSelectedBtn.getId() == R.id.main_page_level3_sel_item) && getStateOfPassStageOne()){
							Intent intent = new Intent(MainFragmentActivity.this,
									L3MenuFragementActivity.class);
							startActivity(intent);
						}
					}
				}
			} else if (v.getId() == R.id.main_page_result_btn) {
				if (userLoginOrLogoutProcess.isUserLoggedIn()){
					Intent intent = new Intent(MainFragmentActivity.this,
							ResultMenuFragementActivity.class);
					startActivity(intent);
				}
			} else if (v.getId() == R.id.main_page_instruction_btn) {
				Intent intent = new Intent(MainFragmentActivity.this,
						ManualFragmentActivity.class);
				startActivity(intent);
			}else if (v.getId() == R.id.main_page_about_btn) {
				Intent intent = new Intent(MainFragmentActivity.this,
						AboutUsFragmentActivity.class);
				startActivity(intent);
			}else if (v.getId() == R.id.main_page_login_btn){
				NameValuePair username, email;
				
				usernameString = usernameInput.getText().toString();
				emailString = emailInput.getText().toString();
				ArrayList<NameValuePair> usernameNemailPair = new ArrayList<NameValuePair>();
				if (TESTMODE){
					username = new PMNameValuePair("name", "abc");
					email = new PMNameValuePair("email", "test@gmail.com");
					usernameNemailPair.add(username);
					usernameNemailPair.add(email);
				}else{
					Pattern p1 = Pattern.compile("^[A-Za-z]+([0-9]*[A-Za-z]*_* *)*([0-9]*[A-Za-z]*_*)+$");
					Matcher m1 = p1.matcher(usernameString);
					Pattern p2 = Pattern.compile("^[a-z]+([0-9]*[a-z]*\\.*_*)*@[a-z]+([0-9]*[a-z]*\\.*)*\\.[a-z]+$");
					//Pattern p2 = Pattern.compile("^[a-z]+$");
					Matcher m2 = p2.matcher(emailString);
					if(m1.find()){
						if(m2.find()){
							username = new PMNameValuePair("name", usernameString);
							email = new PMNameValuePair("email", emailString);
							usernameNemailPair.add(username);
							usernameNemailPair.add(email);
						} else {
							loginPasswordAlertDialog.show();
							return;
						}
					}else{
						loginNameAlertDialog.show();
						return;
					}
				}
				
				AppDataSource appDataSourceForLogin = AppDataSource.getInstance() ;
				//appDataSourceForLogin.post(MainFragmentActivity.this, MainFragmentActivity.this, "user", usernameNemailPair, null, null, PMDataSource.TERMINATE_ON_PAUSE);
				appDataSourceForLogin.post(MainFragmentActivity.this, MainFragmentActivity.this, "/user", usernameNemailPair, new RunnableJSONObject() {
					
					@Override
					public void run(JSONObject object) {
						// TODO Auto-generated method stub
						if (TESTMODE){
							userLoginOrLogoutProcess.inputUserInfo(object.optInt("id"), "abc", "test@gmail.com");
							
						}else{
							userLoginOrLogoutProcess.inputUserInfo(object.optInt("id"), usernameString, emailString);
						}
						userLoginOrLogoutProcess.showUserPage();
					}
				}, null, PMDataSource.TERMINATE_ON_PAUSE);
			}else if (v.getId() == R.id.main_page_logout_btn) {
				userLoginOrLogoutProcess.removeUserRecords();
				userLoginOrLogoutProcess.showUserPage();
			}
			else{
			
				v.setSelected(true);
				if (currentSelectedBtn != null && currentSelectedBtn.getId() != v.getId()) {
					currentSelectedBtn.setSelected(false);
				}
				currentSelectedBtn = v;
			}
		}

	}

	

	

	@Override
	public void displayUserLoggedInUis() {
		// TODO Auto-generated method stub
		emailInput.setVisibility(View.INVISIBLE);
		emailInputIndicator.setVisibility(View.INVISIBLE);
		showEmailIcon.setVisibility(View.VISIBLE);
		emailShow.setVisibility(View.VISIBLE);
		emailShow.setText(userLoginOrLogoutProcess.getUserEmail());
		usernameShow.setVisibility(View.VISIBLE);
		usernameShow.setText(userLoginOrLogoutProcess.getUsername());
		usernameInput.setVisibility(View.INVISIBLE);
		usernameInputIndicator.setVisibility(View.INVISIBLE);
		showUsernameIcon.setVisibility(View.VISIBLE);
		loginBtn.setVisibility(View.INVISIBLE);
		logoutBtn.setVisibility(View.VISIBLE);
	}
	@Override
	public void displayUserNotYetLoggedInUis() {
		// TODO Auto-generated method stub
		emailInput.setVisibility(View.VISIBLE);
		emailInput.setText("");
		emailInputIndicator.setVisibility(View.VISIBLE);
		showEmailIcon.setVisibility(View.INVISIBLE);
		emailShow.setVisibility(View.INVISIBLE);
		usernameShow.setVisibility(View.INVISIBLE);
		usernameInput.setVisibility(View.VISIBLE);
		usernameInput.setText("");
		usernameInputIndicator.setVisibility(View.VISIBLE);
		showUsernameIcon.setVisibility(View.INVISIBLE);
		loginBtn.setVisibility(View.VISIBLE);
		logoutBtn.setVisibility(View.INVISIBLE);
	}
	
	public class AppDataSourceForLogin extends PMDataSource{

		public AppDataSourceForLogin(Class<?> syncServiceClass) {
			super(syncServiceClass);
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		public void onReceiveResult(int resultCode, Bundle resultData) {
			// TODO Auto-generated method stub
			if (resultCode == PMSyncService.STATUS_FINISHED){
				/*userLoginOrLogoutProcess.inputUserInfo("abc", "test@gmail.com");
				userLoginOrLogoutProcess.showUserPage();*/
			}
		}
	}
	
}
