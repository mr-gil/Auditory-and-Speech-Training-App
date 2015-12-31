package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import java.net.URL;

import android.content.Context;
import android.content.Intent;

public class CallDownloadService {
	
	public void startDownloadService(Context ctx, URL url, String savePath, String subPath){
		Intent intent = new Intent(ctx, DownloadService.class);
		intent.putExtra("url", url);
		intent.putExtra("savePath", savePath);
		intent.putExtra("subPath", subPath);
		
		ctx.startService(intent);
	}
}
