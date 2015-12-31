package hk.com.playmore.syncframework.helper;

import hk.com.playmore.syncframework.service.PMSyncService;
import hk.com.playmore.syncframework.util.HttpHandler;
import hk.com.playmore.syncframework.util.RunnableArgument;
import hk.com.playmore.syncframework.util.SyncResultReceiver;
import hk.com.playmore.syncframework.util.SyncResultReceiver.Receiver;
import hk.com.playmore.syncframework.util.Syncable;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;

public abstract class PMDataSource implements Receiver {

	protected SyncResultReceiver receiver = new SyncResultReceiver(
			new Handler(), this);

	public static final int TERMINATE_ON_PAUSE = 0;
	public static final int TERMINATE_ON_STOP = 1;
	public static final int TERMINATE_ON_DESTROY = 2;
	
	public class SyncServiceTask {
		RunnableArgument success;
		RunnableArgument failure;
		
		public SyncServiceTask(RunnableArgument success, RunnableArgument failure) {
			this.success = success;
			this.failure = failure;
		}
	}
	
	protected SparseArray<SyncServiceTask> callMaps = new SparseArray<SyncServiceTask>();

	protected static int tag = 0;
	
	private Class<?> syncServiceClass;
	
	public PMDataSource(Class<?> syncServiceClass) {
		this.syncServiceClass = syncServiceClass;
	}
	
	public void get(Context ctx, Syncable syn, String path, RunnableArgument success, RunnableArgument failure, int lifetime) {
		request(ctx, syn, "get", path, null, success, failure, lifetime);
	}

	public void post(Context ctx, Syncable syn, String path, ArrayList<NameValuePair> params, RunnableArgument success, RunnableArgument failure, int lifetime) {
		request(ctx, syn, "post", path, params, success, failure, lifetime);
	}

	public void put(Context ctx, Syncable syn, String path, RunnableArgument success, RunnableArgument failure, int lifetime) {
		request(ctx, syn, "put", path, null, success, failure, lifetime);
	}

	public void delete(Context ctx, Syncable syn, String path, RunnableArgument success, RunnableArgument failure, int lifetime) {
		request(ctx, syn, "delete", path, null, success, failure, lifetime);
	}

	public void request(Context ctx, Syncable syn, String method, String path, ArrayList<NameValuePair> params, RunnableArgument success, RunnableArgument failure, int lifetime) {
		tag++;
		
		callMaps.put(tag, new SyncServiceTask(success, failure));
		
		final Intent intent = new Intent(PMSyncService.ACTION_GENERAL_ACTION,
				null, ctx, syncServiceClass);
		intent.putExtra(PMSyncService.EXTRA_STATUS_RECEIVER, receiver);
		intent.putExtra(PMSyncService.EXTRA_GENERAL_ACTION, method);
		intent.putExtra(PMSyncService.EXTRA_GENERAL_URL, path);
		intent.putExtra(PMSyncService.EXTRA_GENERAL_TAG, tag);
		if (params != null) {
			intent.putExtra(PMSyncService.EXTRA_GENERAL_PARAMS, params);
		}

		ctx.startService(intent);
		
		syn.syncTaskDidAdd(tag, lifetime, this);
	}
	
	public void stopTask(int tag) {
		callMaps.remove(tag);
	}
	
    public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == PMSyncService.STATUS_RUNNING) {
			return;
		}

		if (resultData == null) {
			return;
		}
		
		SyncServiceTask task = callMaps.get(resultData.getInt("tag"));
		if (task == null) {
			return;
		}

		if (resultCode == PMSyncService.STATUS_ERROR) {
			if (task.failure != null) {
				task.failure.run(resultData.getString(HttpHandler.RESPONSE_KEY));
			}
		} else {
			if (task.success != null) {
				task.success.run(resultData.getString(HttpHandler.RESPONSE_KEY));
			}
		}
    }
	
	public static void clearTasks(SparseArray<PMDataSource> tasks) {
		int max = tasks.size();
		for (int i = 0; i < max; i++) {
			int key = tasks.keyAt(i);
			tasks.get(key).stopTask(key);
		}
	}
}
