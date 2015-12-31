package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service {
	
	  
	public interface DownloadProgressUpdateReceiver {
		public void onDownloadServiceProgressStart();
		public void onDownloadServiceProgressDone(int tag);
	}
	
	

	public class DownloadBinder extends Binder {
		public DownloadService getService() {
			return DownloadService.this;
		}
	}

	private DownloadProgressUpdateReceiver updateReceiver;

	private final IBinder mBinder = new DownloadBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		URL url = (URL) intent.getExtras().getSerializable("url");
		String savePath = intent.getExtras().getString("savePath");
		String subPath = intent.getExtras().getString("subPath");
		int tag = intent.getExtras().getInt("tag", 0);

		if (updateReceiver != null) updateReceiver.onDownloadServiceProgressStart();
		addDownload(url, savePath, subPath, tag);
		return START_NOT_STICKY;
	}

	private ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 8, 1,
			TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

	public void clearDownloads() {
		clearDownloads(8);
	}

	public void clearDownloads(int numOfThreads) {
		pool.shutdownNow();
		pool = new ThreadPoolExecutor(numOfThreads, numOfThreads, 1,	TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
	}
	
	public void addDownload(URL url, String savePath, String subPath, int tag, int retry) {
		pool.execute(new DownloadTask(url, savePath, subPath, tag, retry));
	}
	
	public void addDownload(URL url, String savePath, String subPath, int tag) {
		addDownload(url, savePath, subPath, tag, 3);
	}

	public DownloadProgressUpdateReceiver getUpdateReceiver() {
		return updateReceiver;
	}

	public void setUpdateReceiver(DownloadProgressUpdateReceiver updateReceiver) {
		this.updateReceiver = updateReceiver;
	}

	public void removeUpdateReceiver(Object object) {
		if (this.updateReceiver == object) {
			this.updateReceiver = null;
		}
	}
	
	private class DownloadTask implements Runnable {
		URL url;
		String savePath;
		String subPath;
		int tag;
		int retry;
		
		public DownloadTask(URL url, String savePath, String subPath, int tag, int retry) {
			this.url = url;
			this.savePath = savePath;
			this.subPath = subPath;
			this.tag = tag;
			this.retry = retry;
		}

		@Override
		public void run() {
			if (url == null) return;
			if (savePath == null) return;
			if (subPath == null) return;
			if (retry <= 0) {
				if (getUpdateReceiver() != null) {
					getUpdateReceiver().onDownloadServiceProgressDone(tag);
				}
				return;
			}
			
			int TIMEOUT_VALUE = 10000;
			boolean success = false;
			
			try {
				File file = new File(savePath, subPath);

				long startTime = System.currentTimeMillis();
				Log.d("DownloadService", "download begining");
				Log.d("DownloadService", "download url:" + url);
				/* Open a connection to that URL. */
				URLConnection ucon = url.openConnection();
				ucon.setConnectTimeout(TIMEOUT_VALUE);
				ucon.setReadTimeout(TIMEOUT_VALUE);

				/*
				 * Define InputStreams to read from the URLConnection.
				 */
				InputStream is = ucon.getInputStream();

				byte[] buffer = new byte[4096];
				int n = - 1;

				FileOutputStream fos = new FileOutputStream( file );
				while ( (n = is.read(buffer)) != -1)
				{
				    if (n > 0)
				    {
				    	fos.write(buffer, 0, n);
				    }
				}
				fos.close();
				
				Log.d("DownloadService",
						"download ready in"
								+ ((System.currentTimeMillis() - startTime) / 1000)
								+ " sec");

				success = true;
			} catch (SocketTimeoutException e) {
				Log.d("Download Timeout", "More than " + TIMEOUT_VALUE + " elapsed.");
			} catch (IOException e) {
				Log.d("DownloadService", "Error: " + e);
			}

			if (!success) {
				addDownload(url, savePath, subPath, tag, retry - 1);
			} else {
				if (getUpdateReceiver() != null) {
					getUpdateReceiver().onDownloadServiceProgressDone(tag);
				}
			}
		}
	}


}
