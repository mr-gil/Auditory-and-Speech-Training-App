package hk.com.playmore.syncframework.util;

import hk.com.playmore.syncframework.service.PMSyncService;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class HttpHandler {

	public static final String RESPONSE_KEY = "responeString";
	
	private ResultReceiver receiver;
	private Context context;
	private String action;
	private int tag;

	public HttpHandler(Context context, String action, int tag, ResultReceiver receiver) {
		setContext(context);
		setAction(action);
		setReceiver(receiver);
		setTag(tag);
	}

	public ResultReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(ResultReceiver receiver) {
		this.receiver = receiver;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	protected void succeed() {
		succeed(null);
	}
	
	protected void succeed(Bundle bundle) {
		if (bundle == null) {
			bundle = new Bundle();
		}

		bundle.putString("action", getAction());
		bundle.putInt("tag", getTag());

		if (getReceiver() != null) {
			getReceiver().send(PMSyncService.STATUS_FINISHED, bundle);
		}
	}

	protected void fail() {
		fail(null, null);
	}

	protected void fail(Bundle bundle) {
		fail(null, bundle);
	}

	protected void fail(String message) {
		Bundle bundle = new Bundle();
		bundle.putString(Intent.EXTRA_TEXT, message);
		fail(null, bundle);
	}

	protected void fail(int statusCode, int errorNo) {
		Bundle bundle = new Bundle();
		bundle.putInt(PMSyncService.EXTRA_STATUS, statusCode);
		bundle.putInt(PMSyncService.EXTRA_ERROR_NO, errorNo);
		fail(null, bundle);
	}

	protected void fail(Exception e, Bundle bundle) {
		if (bundle == null) {
			bundle = new Bundle();
		}

		bundle.putString("action", getAction());
		bundle.putInt("tag", getTag());
		
		if (e != null) {
			bundle.putString(Intent.EXTRA_TEXT, e.toString());
		}

		if (getReceiver() != null) {
			getReceiver().send(PMSyncService.STATUS_ERROR, bundle);
		}
	}
	
	public void onResponse(int status, Object object) throws HttpHandlerException {
		if (!shouldProcessResponse(status, object)) {
			return;
		}

		if (status != 201 && status != 200) {
			try {
				JSONObject json = (JSONObject) new JSONTokener((String) object).nextValue();
				fail(status, json.optInt("no"));
			} catch (Exception e) {
				e.printStackTrace();
				fail(status, 0);
			}
		} else {
			Bundle bundle = new Bundle();
			bundle.putString(RESPONSE_KEY, (String) object);
			
			succeed(bundle);
		}
	}
	
	/*
	 *  Override this method for stopping response for example user not auth.
	 */
	protected boolean shouldProcessResponse(int status, Object object) {
		return true;
	}

}
