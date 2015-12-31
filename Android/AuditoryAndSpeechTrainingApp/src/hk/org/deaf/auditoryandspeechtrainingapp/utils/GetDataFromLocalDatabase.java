package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class GetDataFromLocalDatabase<T> {
	private Context context;
	private Class<T> cls;

	public GetDataFromLocalDatabase(Context context, Class<T> cls) {
		setContext(context);
		setCls(cls);

	}

	public ArrayList<T> getDataFromLocalDatabase(int conson) {
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(getContext(), AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper.getDao(cls);
			ArrayList<T> localDatas = (ArrayList<T>) new ArrayList<T>(
					dao.queryForEq("conson", conson));
			return localDatas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<T> getLast10ResultRecordFromLocalDatabase(Activity act,
			int conson) {
		UserLoginOrLogoutProcess userLoginOrLogoutProcess = new UserLoginOrLogoutProcess(
				act, null);
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(act, AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper.getDao(cls);
			QueryBuilder<T, String> queryBuilder = dao.queryBuilder();
			queryBuilder.where()
					.eq("user", userLoginOrLogoutProcess.getUserId()).and()
					.eq("conson", conson);
			queryBuilder.orderBy("create_time", true);
			PreparedQuery<T> preparedQuery = queryBuilder.prepare();
			// return dao.queryForEq("userId",
			// userLoginOrLogoutProcess.getUserId());
			ArrayList<T> list = (ArrayList<T>) dao.query(preparedQuery);
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void sentIsFirstDataToLocalDatabase(T data) {
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(getContext(), AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper.getDao(cls);
			dao.createOrUpdate(data);
			Log.d("updated isFirst Data", "Successful updated isFirst Data");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("updated isFirst Data", "Unsuccessful updated isFirst Data");
		}

	}

	public ArrayList<T> getIsFirstDataFromLocalDatabase() {
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(getContext(), AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper.getDao(cls);
			ArrayList<T> localDatas = (ArrayList<T>) new ArrayList<T>(
					dao.queryForEq("id", "1"));
			return localDatas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public T getPassToStage2StateRecord(int user) {
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(getContext(), AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper.getDao(cls);
			ArrayList<T> localDatas = (ArrayList<T>) new ArrayList<T>(
					dao.queryForEq("user", user));
			if (localDatas != null && localDatas.size() > 0){
				return localDatas.get(0);
			}else{
				Log.d("getPassToStage2StateRecord", "empty table");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void saveDataToLocalDatabase(T data) {
		AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager
				.getHelper(getContext(), AppDatabaseHelper.class);
		try {
			Dao<T, String> dao = helper.getDao(cls);
			dao.createOrUpdate(data);
			Log.d("updated Data", "Successful updated isFirst Data");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("updated Data", "Unsuccessful updated isFirst Data");
		}

	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Class<T> getCls() {
		return cls;
	}

	public void setCls(Class<T> cls) {
		this.cls = cls;
	}
}
