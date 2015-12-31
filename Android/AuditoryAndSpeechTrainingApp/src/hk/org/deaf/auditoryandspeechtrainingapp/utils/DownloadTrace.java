package hk.org.deaf.auditoryandspeechtrainingapp.utils;

public class DownloadTrace {
	private int noOfDownloads =0 ;
	private int noOfDownloadsComplete=0;
	private static DownloadTrace downloadtrace;
	
	public static DownloadTrace getInstance(){
		if (downloadtrace == null){
			downloadtrace = new DownloadTrace();
		}
		return downloadtrace;
		
	}
	
	private DownloadTrace(){
		
	}
	
	public int getNoOfDownloads() {
		return noOfDownloads;
	}

	public  void setNoOfDownloads(int noOfDownloads) {
		this.noOfDownloads = noOfDownloads;
	}

	public int getNoOfDownloadsComplete() {
		return noOfDownloadsComplete;
	}

	public void setNoOfDownloadsComplete(int noOfDownloadsComplete) {
		this.noOfDownloadsComplete = noOfDownloadsComplete;
	}
	
	public synchronized void addOneDownload(){
		setNoOfDownloads(getNoOfDownloads() + 1);
	}
	
	public synchronized void addOneDownloadsComplete(){
		setNoOfDownloadsComplete(getNoOfDownloadsComplete() + 1 );
	}
	
}
