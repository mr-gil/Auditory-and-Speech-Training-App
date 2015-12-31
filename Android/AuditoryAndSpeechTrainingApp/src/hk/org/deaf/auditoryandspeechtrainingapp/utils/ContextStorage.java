package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.org.deaf.auditoryandspeechtrainingapp.MainFragmentActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.DownloadService.DownloadProgressUpdateReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class ContextStorage {
	private static ContextStorage instance;
	private Context context;
	private DownloadService downloadService;
	public ServiceConnection mConnection = new ServiceConnection() {
		
		public void onServiceConnected(ComponentName className, IBinder binder) {
			downloadService = ((DownloadService.DownloadBinder) binder).getService();
			downloadService.setUpdateReceiver(new AfterFinishDownloadAction());
	    }

	    public void onServiceDisconnected(ComponentName className) {
	    	downloadService.removeUpdateReceiver(null);
	    	downloadService = null;
	    }
	  };
	  
	public static ContextStorage getInstance(){
		if (instance == null){
			instance = new ContextStorage();
		}
		return instance; 
	}
	
	private ContextStorage(){
		
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public void bindService(){
		if (this.context != null){
			this.context.bindService(new Intent(this.context, DownloadService.class), mConnection, this.context.BIND_AUTO_CREATE);
		}
	}
	
	private class AfterFinishDownloadAction implements DownloadProgressUpdateReceiver {

		@Override
		public void onDownloadServiceProgressDone(int tag) {
			// TODO Auto-generated method stub
			Log.d("TestDownloadFinish", "TestDownloadFinish");
		}

		@Override
		public void onDownloadServiceProgressStart() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void createNeededDir(){
		createNeededDirForStageOne();
		createNeededDirForStageTwo();
	}
	
	public void createNeededDirForStageOne(){
		String pathPhVsP = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "0");
		MainFragmentActivity.pathPhVsP = pathPhVsP;
		String pathPhVsTh = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "1");
		MainFragmentActivity.pathPhVsTh = pathPhVsTh;
		String pathThVsT = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "2");
		MainFragmentActivity.pathThVsT = pathThVsT;
		String pathThVsKh = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "3");
		MainFragmentActivity.pathThVsKh = pathThVsKh;
		String pathKhVsK = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "4");
		MainFragmentActivity.pathKhVsK = pathKhVsK;
		String pathSVsTs = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "5");
		MainFragmentActivity.pathSVsTs = pathSVsTs;
		String pathTshVsTs = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "6");
		MainFragmentActivity.pathTshVsTs = pathTshVsTs;
		String pathNVsM = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "7");
		MainFragmentActivity.pathNVsM = pathNVsM;
		String pathNgVsN = AuditoryDirectoryHelper.getAuditoryStageOneSubDir(ContextStorage.getInstance().getContext(), "8");
		MainFragmentActivity.pathNgVsN = pathNgVsN;
		
	}
	public void createNeededDirForStageTwo(){
		String pathPh = AuditoryDirectoryHelper.getAuditoryStage2SubDir(ContextStorage.getInstance().getContext(), "0");
		MainFragmentActivity.pathPh = pathPh;
		String pathTh = AuditoryDirectoryHelper.getAuditoryStage2SubDir(ContextStorage.getInstance().getContext(), "1");
		MainFragmentActivity.pathTh = pathTh;
		String pathKh = AuditoryDirectoryHelper.getAuditoryStage2SubDir(ContextStorage.getInstance().getContext(), "2");
		MainFragmentActivity.pathKh = pathKh;
		String pathS = AuditoryDirectoryHelper.getAuditoryStage2SubDir(ContextStorage.getInstance().getContext(), "3");
		MainFragmentActivity.pathS = pathS;
		String pathTsh = AuditoryDirectoryHelper.getAuditoryStage2SubDir(ContextStorage.getInstance().getContext(), "4");
		MainFragmentActivity.pathTsh = pathTsh;
		String pathNg = AuditoryDirectoryHelper.getAuditoryStage2SubDir(ContextStorage.getInstance().getContext(), "5");
		MainFragmentActivity.pathNg = pathNg;
		String pathN = AuditoryDirectoryHelper.getAuditoryStage2SubDir(ContextStorage.getInstance().getContext(), "6");
		MainFragmentActivity.pathN = pathN;
	}
}
