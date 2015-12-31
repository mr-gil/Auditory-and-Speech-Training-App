package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.org.deaf.auditoryandspeechtrainingapp.MainFragmentActivity;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class AuditoryDirectoryHelper {
	
	/*private static String getAuditoryDir(Context ctx) {
		String path = getStoragePath("/auditory", ctx);
		return path;
	}*/

	/*private static String getAuditoryStage2BaseDir(Context ctx) {
		String path = getAuditoryDir(ctx);
		path = getStoragePath(path + "/stage2", ctx);
		return path;
	}*/

	public static String getAuditoryStage2SubDir(Context ctx, String subPath) {

		String fullPath = getStoragePath("/auditory" + "/stage2" + "/" + subPath, ctx);
		Log.d("fullPath", fullPath);
		return fullPath;
	}

	public static String getAuditoryStageOneSubDir(Context ctx, String subPath) {
		String fullPath = getStoragePath("/auditory" + "/stage1" + "/" + subPath, ctx);
		Log.d("fullPath", fullPath);
		return fullPath;
		
	}
	private static boolean isExternalStorageMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	private static String getExternalStoragePathByContext(String path,
			Context context) {
		File dirPath = context.getExternalFilesDir(path);
		if (dirPath == null) {
			dirPath = new File(context.getExternalFilesDir(null)
					.getAbsolutePath() + "/" + path + "/");
		}

		if (!dirPath.exists()) {
			dirPath.mkdir();
		}
		return dirPath.getAbsolutePath();
	}

	public static String getStoragePath(String path, Context ctx) {
		if (isExternalStorageMounted()) {
			return getExternalStoragePathByContext(path, ctx);
		} else {
			return getInternalStoragePathByContext(path, ctx);
		}
	}

	private static String getInternalStoragePathByContext(String path,
			Context context) {
		File dirPath = context.getDir(path, Context.MODE_PRIVATE);
		if (dirPath.exists()) {
			dirPath.mkdir();
		}
		return dirPath.getAbsolutePath();
	}
	
	public static void deleteRecursive(File fileOrDirectory) {
	    if (fileOrDirectory.isDirectory())
	        for (File child : fileOrDirectory.listFiles()){
	            deleteRecursive(child);
	        }

	    fileOrDirectory.delete();
	}
	
	public static String ObtainStageOneSavePath(int conson){
		switch (conson){
			case 0: return MainFragmentActivity.pathPhVsP;
			case 1: return MainFragmentActivity.pathPhVsTh;
			case 2: return MainFragmentActivity.pathThVsT;
			case 3: return MainFragmentActivity.pathThVsKh;
			case 4: return MainFragmentActivity.pathKhVsK;
			case 5: return MainFragmentActivity.pathSVsTs;
			case 6: return MainFragmentActivity.pathTshVsTs;
			case 7: return MainFragmentActivity.pathNVsM;
			case 8: return MainFragmentActivity.pathNgVsN;
		}
		return null;
	}
	public static String ObtainStage2SavePath(int conson){
		switch (conson){
		case 0: return MainFragmentActivity.pathPh;
		case 1: return MainFragmentActivity.pathTh;
		case 2: return MainFragmentActivity.pathKh;
		case 3: return MainFragmentActivity.pathS;
		case 4: return MainFragmentActivity.pathTsh;
		case 5: return MainFragmentActivity.pathNg;
		case 6: return MainFragmentActivity.pathN;
		}
		return null;
	}
	
	
}
