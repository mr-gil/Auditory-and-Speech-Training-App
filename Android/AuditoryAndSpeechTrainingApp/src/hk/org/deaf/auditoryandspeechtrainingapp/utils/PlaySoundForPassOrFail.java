package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

public class PlaySoundForPassOrFail {
	Context ctx;
	MediaPlayer passPlayer, failPlayer, openningPlayer;
	StopPassSound stopPassSound;
	
	public PlaySoundForPassOrFail(Context ctx){
		this.ctx = ctx;
		//FileReader reader = new FileReader(descriptor.getFileDescriptor());
	}
	
	public void loadPassSoundFile(){
		try {
			AssetFileDescriptor descriptor = ctx.getAssets().openFd("pass.mp3");
			passPlayer = new MediaPlayer();
			passPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			passPlayer.prepare();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("PlaySound", "mp3 load sound error");
			e.printStackTrace();
		}
	}
	
	public void loadFailSoundFile(){
		try {
			AssetFileDescriptor descriptor = ctx.getAssets().openFd("fail.mp3");
			failPlayer = new MediaPlayer();
			failPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			failPlayer.prepare();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("PlaySound", "caf load sound error");
			e.printStackTrace();
		}
	}
	
	public void loadOpenningSoundFile(){
		try {
			AssetFileDescriptor descriptor = ctx.getAssets().openFd("pass.mp3");
			openningPlayer = new MediaPlayer();
			openningPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			openningPlayer.prepare();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("PlaySound", "mp3 load sound error");
			e.printStackTrace();
		}
	}
	
	public void playPassSound(){
		passPlayer.start();
		Thread stopSound = new Thread(new StopPassSound());
		stopSound.start();
	}
	
	public void playFailSound(){
		failPlayer.start();
		Thread stopSound = new Thread(new StopFailSound());
		stopSound.start();
	}
	
	public void playOpeningSound(){
		openningPlayer.start();
		
	}
	public void stopOpeningSound(){
		openningPlayer.stop();
	}
	class StopPassSound implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			passPlayer.stop();
		}
		
	}
	
	class StopFailSound implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			failPlayer.stop();
		}
		
	}
}
