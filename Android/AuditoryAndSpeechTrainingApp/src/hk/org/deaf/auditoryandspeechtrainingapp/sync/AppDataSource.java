package hk.org.deaf.auditoryandspeechtrainingapp.sync;

import hk.com.playmore.syncframework.helper.PMDataSource;
import hk.com.playmore.syncframework.util.DatabaseModel;
import hk.com.playmore.syncframework.util.DatabaseSyncer;
import hk.com.playmore.syncframework.util.RunnableArgument;
import hk.com.playmore.syncframework.util.Syncable;
import hk.org.deaf.auditoryandspeechtrainingapp.model.Stage2Conson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneResultRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoResultRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.RunnableJSONArrary;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.UserLoginOrLogoutProcess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class AppDataSource extends PMDataSource{



	private static AppDataSource _instance;
	
	private AppDataSource() {
		super(AppSyncService.class);
	}

	public static AppDataSource getInstance() {
		if (_instance == null) {
			_instance = new AppDataSource();
		}
		return _instance;
	}
	
	private class TypedSync<T extends DatabaseModel> {
		private TypedSync(Context ctx, Class<T> cls, Class<T[]> arrayCls, String array, String conson) {
			try {
				AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager.getHelper(
						 ctx, AppDatabaseHelper.class);
				DatabaseSyncer<T> syncer = new DatabaseSyncer<T>();
				Dao<T, String> dao = helper.getDao(cls);
				
				Gson gson = new Gson();
				List<T> servers = (List<T>) Arrays.asList(gson.fromJson(
						array, arrayCls));
	
				//List<T> locals = dao.queryForAll();
				List<T> locals = dao.queryForEq("conson", conson);
				syncer.sync(dao, locals, servers);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class TypedSyncForLast10ResultRecords<T extends DatabaseModel> {
		private TypedSyncForLast10ResultRecords(Activity act, Context ctx, Class<T> cls, Class<T[]> arrayCls, String array, String conson) {
			try {
				AppDatabaseHelper helper = (AppDatabaseHelper) OpenHelperManager.getHelper(
						 ctx, AppDatabaseHelper.class);
				DatabaseSyncer<T> syncer = new DatabaseSyncer<T>();
				Dao<T, String> dao = helper.getDao(cls);
				
				Gson gson = new Gson();
				List<T> servers = (List<T>) Arrays.asList(gson.fromJson(
						array, arrayCls));
	
				
				
				UserLoginOrLogoutProcess userLoginOrLogoutProcess = new UserLoginOrLogoutProcess(act, null);
				QueryBuilder<T, String> queryBuilder =
						  dao.queryBuilder();
				//queryBuilder.orderBy("create_time", true);
				queryBuilder.where().eq("user", userLoginOrLogoutProcess.getUserId()).and().eq("conson", conson);
				PreparedQuery<T> preparedQuery = queryBuilder.prepare();
				//return dao.queryForEq("userId", userLoginOrLogoutProcess.getUserId());
				List<T> locals = (List<T>) dao.query(preparedQuery);
				
				syncer.sync(dao, locals, servers);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getStageOneConson(final Context ctx, Syncable syn, final Runnable done, final String conson) {
		//NameValuePair requiredParam1 = new PMNameValuePair("X-APP-USER-ID", "1");
		//NameValuePair requiredParam2 = new PMNameValuePair("X-APP-USER-DPI", "2");
		//ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(requiredParam1);
		//params.add(requiredParam2);
		get(ctx, syn, "/question/stage1/conson/"+ conson, new RunnableJSONArrary() {
			
			@Override
			public void run(JSONArray jsonArray) {
				// TODO Auto-generated method stub
				
				//after successfull download data
				new TypedSync<StageOneConson>((Context) ctx, StageOneConson.class, StageOneConson[].class, jsonArray.toString(), conson);
				done.run();// game start from here?
			}
		}, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub
				
			}
		}, TERMINATE_ON_PAUSE);
	}
	
	public void postStage1bScore(final Context ctx, Syncable syn, ArrayList<NameValuePair> params, final Runnable done, String conson) {
		
		post(ctx, syn, "/score/stage1/conson/" + conson, params, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub

				Log.d("Stage 1 Score send","Score send successfully");
			}
		}, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub
				Log.d("Stage 1 Score send","Score send unsuccessfully");
			}
		}, TERMINATE_ON_PAUSE);
		
	}

	public void getStage2Conson(final Context ctx, Syncable syn, final Runnable done, final String conson) {
		//NameValuePair requiredParam1 = new PMNameValuePair("X-APP-USER-ID", "1");
		//NameValuePair requiredParam2 = new PMNameValuePair("X-APP-USER-DPI", "2");
		//ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(requiredParam1);
		//params.add(requiredParam2);
		get(ctx, syn, "/question/stage2/conson/"+ conson, new RunnableJSONArrary() {
			
			@Override
			public void run(JSONArray jsonArray) {
				// TODO Auto-generated method stub
				
				//after successfull download data
				new TypedSync<Stage2Conson>((Context) ctx, Stage2Conson.class, Stage2Conson[].class, jsonArray.toString(), conson);
				done.run();// game start from here?
			}
		}, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub
				
			}
		}, TERMINATE_ON_PAUSE);
	}
	
	public void postStage2bScore(final Context ctx, Syncable syn, ArrayList<NameValuePair> params, final Runnable done, String conson) {
		
		post(ctx, syn, "/score/stage2/conson/" + conson, params, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub

				Log.d("Stage 2 Score send","Score send successfully");
			}
		}, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub
				Log.d("Stage 2 Score send","Score send unsuccessfully");
			}
		}, TERMINATE_ON_PAUSE);
		
	}
	
	public void postScoreToEmail(final Context ctx, Syncable syn, ArrayList<NameValuePair> params, final Runnable done, final Runnable fail, String stage) {
		
		post(ctx, syn, "/score/email/stage/" + stage, params, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub

				Log.d("postScoreToEmail","Score send successfully");
				done.run();
			}
		}, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub
				Log.d("postScoreToEmail","Score send unsuccessfully");
				fail.run();
			}
		}, TERMINATE_ON_PAUSE);
		
	}
	
	public void getStage1Last10Records(final Activity act, final Context ctx, Syncable syn, final Runnable done, final String conson) {
		
		get(ctx, syn, "/score/last10/stage/1/conson/" + conson, new RunnableJSONArrary() {
			
			@Override
			public void run(JSONArray jsonArray) {
				// TODO Auto-generated method stub
				
				//after successfull download data
				new TypedSyncForLast10ResultRecords<StageOneResultRecord>(act, (Context) ctx, StageOneResultRecord.class, StageOneResultRecord[].class, jsonArray.toString(), conson);
				done.run();// game start from here?
			}
		}, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub
				
			}
		}, TERMINATE_ON_PAUSE);
	}

public void getStage2Last10Records(final Activity act, final Context ctx, Syncable syn, final Runnable done, final String conson) {
		
		get(ctx, syn, "/score/last10/stage/2/conson/" + conson, new RunnableJSONArrary() {
			
			@Override
			public void run(JSONArray jsonArray) {
				// TODO Auto-generated method stub
				
				//after successfull download data
				new TypedSyncForLast10ResultRecords<StageTwoResultRecord>(act, (Context) ctx, StageTwoResultRecord.class, StageTwoResultRecord[].class, jsonArray.toString(), conson);
				done.run();// game start from here?
			}
		}, new RunnableArgument() {
			
			@Override
			public void run(String object) {
				// TODO Auto-generated method stub
				
			}
		}, TERMINATE_ON_PAUSE);
	}

	
	public interface Prefs {
		final String MEG_PREFS = "hk.meg.android.sync.meg_prefs";
		final String QUIZ_UNLOCK = "hk.meg.android.sync.quiz_unlock";
		final String LOCAL_QUIZ_TIME_STAMP = "hk.meg.android.sync.local_quiz_time_stamp";
		final String SERVER_QUIZ_TIME_STAMP = "hk.meg.android.sync.server_quiz_time_stamp";
	}	
}