package hk.com.playmore.syncframework.service;

import hk.com.playmore.syncframework.helper.PMDatabaseHelper;
import hk.com.playmore.syncframework.util.HttpHandler;
import hk.com.playmore.syncframework.util.HttpHandlerException;
import hk.com.playmore.syncframework.util.HttpRequest;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Background {@link Service} that synchronizes data living in
 * {@link ScheduleProvider}. Reads data from both local {@link Resources} and
 * from remote sources, such as a spreadsheet.
 */
public abstract class PMSyncService extends IntentService {
	private static final String TAG = "SyncService";

	// Required Extra
	public static final String EXTRA_STATUS_RECEIVER = "hk.com.playmore.syncframework.util.STATUS_RECEIVER";
	public static final String EXTRA_GENERAL_ACTION = "hk.com.playmore.syncframework.util.EXTRA_GENERAL_ACTION";
	public static final String EXTRA_GENERAL_URL = "hk.com.playmore.syncframework.util.EXTRA_GENERAL_URL";
	public static final String EXTRA_GENERAL_PARAMS = "hk.com.playmore.syncframework.util.EXTRA_GENERAL_PARAMS";
	public static final String EXTRA_GENERAL_TAG = "hk.com.playmore.syncframework.util.EXTRA_GENERAL_TAG";

	// Returned Extra
	public static final String EXTRA_STATUS = "hk.com.playmore.syncframework.util.EXTRA_STATUS";
	public static final String EXTRA_ERROR_NO = "hk.com.playmore.syncframework.util.EXTRA_ERROR_NO";

	// Available Actions
	public static final String ACTION_GENERAL_ACTION = "hk.com.playmore.syncframework.util.ACTION_GENERAL_ACTION";

	// Receiver Status
	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	private HttpRequest httpRequest;
	private PMDatabaseHelper helper;
	private SQLiteDatabase db;

	public PMSyncService() {
		super(TAG);

		Log.d(TAG, "SyncService()");
	}

	protected abstract PMDatabaseHelper getNewDatabaseHelper();

	protected abstract HttpRequest getNewHttpRequest();

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "SyncService.onHandleIntent(intent=" + intent.toString()
				+ ")");

		final ResultReceiver receiver = intent
				.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (receiver != null) {
			Bundle bundle = new Bundle();
			bundle.putString("action", intent.getAction());
			receiver.send(STATUS_RUNNING, bundle);
		}

		try {
			// Always hit remote spreadsheet for any updates
			final long startRemote = System.currentTimeMillis();

			onHandleAction(intent, intent.getAction(), receiver);

			Log.d(TAG, "remote request took "
					+ (System.currentTimeMillis() - startRemote) + "ms");
		} catch (Exception e) {
			Log.e(TAG, "Problem while syncing", e);

			if (receiver != null) {
				// Pass back error to surface listener
				final Bundle errorBundle = new Bundle();
				errorBundle.putString("action", intent.getAction());
				errorBundle.putString(Intent.EXTRA_TEXT, e.toString());
				receiver.send(STATUS_ERROR, errorBundle);
			}
		}
		// Announce success to any surface listener

		Log.d(TAG, "sync finished");
	}

	@SuppressWarnings("unchecked")
	protected void onHandleAction(Intent intent, String action,
			ResultReceiver receiver) throws HttpHandlerException {
		if (action.equals(ACTION_GENERAL_ACTION)) {
			actionGeneral(action, intent.getStringExtra(EXTRA_GENERAL_ACTION),
					intent.getStringExtra(EXTRA_GENERAL_URL),
					(ArrayList<NameValuePair>) intent
							.getSerializableExtra(EXTRA_GENERAL_PARAMS),
					intent.getIntExtra(EXTRA_GENERAL_TAG, -1), receiver);
		}
	}

	private void actionGeneral(String action, String method, String path,
			ArrayList<NameValuePair> params, int tag, ResultReceiver receiver)
			throws HttpHandlerException {
		if (method.equalsIgnoreCase("get")) {
			getHttpRequest().get(path, new HttpHandler(this, action, tag, receiver));
		} else if (method.equalsIgnoreCase("post")) {
			getHttpRequest().post(path, params, new HttpHandler(this, action, tag, receiver));
		} else if (method.equalsIgnoreCase("put")) {
			getHttpRequest().put(path, params, new HttpHandler(this, action, tag, receiver));
		} else if (method.equalsIgnoreCase("delete")) {
			getHttpRequest().delete(path, new HttpHandler(this, action, tag, receiver));
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

		httpRequest = getNewHttpRequest();
		helper = getNewDatabaseHelper();
		db = helper.getWritableDatabase();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (helper != null) {
			helper.close();
		}
		if (db != null) {
			db.close();
		}
	}

	public PMDatabaseHelper getHelper() {
		return helper;
	}

	public HttpRequest getHttpRequest() {
		return httpRequest;
	}
}
