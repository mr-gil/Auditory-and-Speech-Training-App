package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.org.deaf.auditoryandspeechtrainingapp.interfaces.ChallengeResultFunctions;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDatabaseHelper;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class ChallengeResultProcessing<T> {
	private Activity act;
	private Class<T> cls;
	public ChallengeResultProcessing(Activity act, Class<T> cls){
		this.act = act;
		this.cls = cls;
	}
	public T getChallengeResult(){
		UserLoginOrLogoutProcess userLoginOrLogoutProcess = new UserLoginOrLogoutProcess(act, null);
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(
						act,
						AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper
					.getDao(cls);
			QueryBuilder<T, String> queryBuilder =
					  dao.queryBuilder();
			//queryBuilder.orderBy("id", false);
			queryBuilder.where().eq("userId", userLoginOrLogoutProcess.getUserId());
			PreparedQuery<T> preparedQuery = queryBuilder.prepare();
			//return dao.queryForEq("userId", userLoginOrLogoutProcess.getUserId());
			List<T> list = dao.query(preparedQuery);
			if (list != null){
				Log.d("ChallengeResult contain", String.valueOf(list.size()));
				if (list.size() > 0){
					return list.get(0);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	public void saveChallengeResultToLocalDatabase(boolean result, ChallengeResultFunctions challengeResult){
		
		UserLoginOrLogoutProcess userLoginOrLogoutProcess = new UserLoginOrLogoutProcess(act, null);
		challengeResult.setResultCompleted(result);
		challengeResult.setUserId(userLoginOrLogoutProcess.getUserId());
		//challengeResult.setId("1");
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(
						act,
						AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper
					.getDao(cls);
			dao.createOrUpdate((T) challengeResult);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
