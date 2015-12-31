package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.com.playmore.syncframework.util.DatabaseModel;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.AuditoryDirectoryHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.CallDownloadService;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ContextStorage;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;

public class StageOneConson implements Serializable, DatabaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6601554893277413972L;
	
	@DatabaseField(id = true)
	private String id;
	
	@DatabaseField
	private int conson;
	
	@DatabaseField
	private String voicechip1;
	
	@DatabaseField
	private String word1;
	
	@DatabaseField
	private String picture1;
	
	@DatabaseField
	private String voicechip2;
	
	@DatabaseField
	private String word2;
	
	@DatabaseField
	private String picture2;
	
	@DatabaseField
	private int answer;
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
	}

	public String getVoicechip1() {
		return voicechip1;
	}

	public void setVoicechip1(String voicechip1) {
		this.voicechip1 = voicechip1;
	}

	public String getWord1() {
		return word1;
	}

	public void setWord1(String word1) {
		this.word1 = word1;
	}

	public String getPicture1() {
		return picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}

	public String getWord2() {
		return word2;
	}

	public void setWord2(String word2) {
		this.word2 = word2;
	}

	public String getPicture2() {
		return picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		deleteFunctionForOnDeleteAndOnUpdate(this);
	}

	private void deleteFunctionForOnDeleteAndOnUpdate(StageOneConson sc){
		String savePath = AuditoryDirectoryHelper
				.ObtainStage2SavePath(sc.getConson());
		File picFileForDelete1 = new File(savePath, sc.getPicture1());
		File voiceForDelete1 = new File(savePath, sc.getVoicechip1());
		File picFileForDelete2 = new File(savePath, sc.getPicture2());
		File voiceForDelete2 = new File(savePath, sc.getVoicechip2());
		AuditoryDirectoryHelper.deleteRecursive(picFileForDelete1);
		AuditoryDirectoryHelper.deleteRecursive(voiceForDelete1);
		AuditoryDirectoryHelper.deleteRecursive(picFileForDelete2);
		AuditoryDirectoryHelper.deleteRecursive(voiceForDelete2);
	}
	
	private void createProcessForCreateAndUpdate(StageOneConson sc){
		String baseUrlString = "http://demo.pm/auditory/site/upload";
		String fullUrlStringForGetPicture1 = baseUrlString + "/" + sc.getPicture1();
		String fullUrlStringForGetVoicechip1 = baseUrlString + "/" + sc.getVoicechip1();
		String fullUrlStringForGetPicture2 = baseUrlString + "/" + sc.getPicture2();
		String fullUrlStringForGetVoicechip2 = baseUrlString + "/" + sc.getVoicechip2();
		
		
		URL fullUrlForGetPicture1, fullUrlForGetVoicechip1, fullUrlForGetPicture2, fullUrlForGetVoicechip2;
		
		try {
			fullUrlForGetPicture1 = new URL(fullUrlStringForGetPicture1);
			fullUrlForGetVoicechip1 = new URL(fullUrlStringForGetVoicechip1);
			fullUrlForGetPicture2 = new URL(fullUrlStringForGetPicture2);
			fullUrlForGetVoicechip2 = new URL(fullUrlStringForGetVoicechip2);
			Log.d("downloadservice_full_url",fullUrlStringForGetPicture1);
			Log.d("downloadservice_full_url",fullUrlStringForGetPicture2);
			
			String savePath = AuditoryDirectoryHelper
					.ObtainStageOneSavePath(sc.getConson()); 
			Log.d("savePathForStageOne",savePath);
			//ContextStorage.getInstance().bindService();
			CallDownloadService callDownloadService = new CallDownloadService();
			callDownloadService.startDownloadService(ContextStorage.getInstance().getContext(), fullUrlForGetPicture1, savePath, sc.getPicture1());
			callDownloadService.startDownloadService(ContextStorage.getInstance().getContext(), fullUrlForGetVoicechip1, savePath, sc.getVoicechip1());
			callDownloadService.startDownloadService(ContextStorage.getInstance().getContext(), fullUrlForGetPicture2, savePath, sc.getPicture2());
			callDownloadService.startDownloadService(ContextStorage.getInstance().getContext(), fullUrlForGetVoicechip2, savePath, sc.getVoicechip2());
			
			
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		createProcessForCreateAndUpdate(this);
	}

	@Override
	public void onUpdate(DatabaseModel server) {
		// TODO Auto-generated method stub
		deleteFunctionForOnDeleteAndOnUpdate(this);
		createProcessForCreateAndUpdate((StageOneConson) server);
	}

	
	public String getVoicechip2() {
		return voicechip2;
	}

	public void setVoicechip2(String voicechip2) {
		this.voicechip2 = voicechip2;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
}
