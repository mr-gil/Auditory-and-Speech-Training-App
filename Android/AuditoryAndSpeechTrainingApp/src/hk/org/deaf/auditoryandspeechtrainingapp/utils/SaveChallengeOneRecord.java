package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeOneRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDatabaseHelper;

import java.sql.SQLException;
import java.util.Date;

import android.app.Activity;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

public class SaveChallengeOneRecord {
	private Activity act;
	
	public SaveChallengeOneRecord(Activity act){
		this.act = act;
	}
	public void todayDone(){
		Date day = new Date();
		oneDayDone(day.getDay());
	}
	
	public void oneDayDone(int day){
		UserLoginOrLogoutProcess userLoginOrLogoutProcess = new  UserLoginOrLogoutProcess(act, null);
		ChallengeOneRecord challengeOneRecord = new ChallengeOneRecord();
		challengeOneRecord.setUserId(userLoginOrLogoutProcess.getUserId());
		challengeOneRecord.setDay(day);
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(
						act,
						AppDatabaseHelper.class);
		try {
			Dao<ChallengeOneRecord, String> dao = helper
					.getDao(ChallengeOneRecord.class);
			dao.createOrUpdate(challengeOneRecord);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
