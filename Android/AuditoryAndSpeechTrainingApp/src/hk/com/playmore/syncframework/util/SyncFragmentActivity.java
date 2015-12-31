package hk.com.playmore.syncframework.util;

import hk.com.playmore.syncframework.helper.PMDataSource;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;

public class SyncFragmentActivity extends FragmentActivity implements Syncable {

	protected SparseArray<PMDataSource> taskCancelOnPause = new SparseArray<PMDataSource>();
	protected SparseArray<PMDataSource> taskCancelOnStop = new SparseArray<PMDataSource>();
	protected SparseArray<PMDataSource> taskCancelOnDestroy = new SparseArray<PMDataSource>();

	@Override
	public void syncTaskDidAdd(int tag, int lifetime, PMDataSource helper) {
		if (lifetime == PMDataSource.TERMINATE_ON_PAUSE) {
			taskCancelOnPause.put(tag, helper);
		} else if (lifetime == PMDataSource.TERMINATE_ON_STOP) {
			taskCancelOnStop.put(tag, helper);
		} else {
			taskCancelOnDestroy.put(tag, helper);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		PMDataSource.clearTasks(taskCancelOnPause);
	}

	@Override
	protected void onStop() {
		super.onStop();

		PMDataSource.clearTasks(taskCancelOnStop);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		PMDataSource.clearTasks(taskCancelOnDestroy);
	}

	@Override
	public void syncTaskDidFinish(int tag) {
		taskCancelOnPause.remove(tag);
		taskCancelOnStop.remove(tag);
		taskCancelOnDestroy.remove(tag);
	}
}