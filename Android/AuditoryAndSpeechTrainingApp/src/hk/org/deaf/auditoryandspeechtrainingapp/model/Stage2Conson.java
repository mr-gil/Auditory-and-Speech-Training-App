package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.com.playmore.syncframework.util.DatabaseModel;
import hk.org.deaf.auditoryandspeechtrainingapp.dao.Stage2ConsonDao;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.AuditoryDirectoryHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.CallDownloadService;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.ContextStorage;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "StageTwoConsons", daoClass = Stage2ConsonDao.class)
public class Stage2Conson implements Serializable, DatabaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2379264815280933753L;

	@DatabaseField(id = true)
	private String id;

	@DatabaseField
	private int conson;

	@DatabaseField
	private String picture;

	@DatabaseField
	private String sentence;

	@DatabaseField
	private String word1;

	@DatabaseField
	private String word2;

	@DatabaseField
	private String word3;

	@DatabaseField
	private String word4;

	@DatabaseField
	private String word5;

	@DatabaseField
	private String word6;

	@DatabaseField
	private String word7;

	@DatabaseField
	private String word8;

	@DatabaseField
	private String word9;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getConson() {
		return conson;
	}

	public void setConson(int conson) {
		this.conson = conson;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getWord1() {
		return word1;
	}

	public void setWord1(String word1) {
		this.word1 = word1;
	}

	public String getWord2() {
		return word2;
	}

	public void setWord2(String word2) {
		this.word2 = word2;
	}

	public String getWord3() {
		return word3;
	}

	public void setWord3(String word3) {
		this.word3 = word3;
	}

	public String getWord4() {
		return word4;
	}

	public void setWord4(String word4) {
		this.word4 = word4;
	}

	public String getWord5() {
		return word5;
	}

	public void setWord5(String word5) {
		this.word5 = word5;
	}

	public String getWord6() {
		return word6;
	}

	public void setWord6(String word6) {
		this.word6 = word6;
	}

	public String getWord7() {
		return word7;
	}

	public void setWord7(String word7) {
		this.word7 = word7;
	}

	public String getWord8() {
		return word8;
	}

	public void setWord8(String word8) {
		this.word8 = word8;
	}

	public String getWord9() {
		return word9;
	}

	public void setWord9(String word9) {
		this.word9 = word9;
	}

	public String getWord10() {
		return word10;
	}

	public void setWord10(String word10) {
		this.word10 = word10;
	}

	
	@DatabaseField
	private String word10;

	@DatabaseField
	private String voicechip;

	@DatabaseField
	private String sentenceVoicechip;

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		deleteFunctionForOnDeleteAndOnUpdate(this);
	}

	private void deleteFunctionForOnDeleteAndOnUpdate(Stage2Conson sc){
		String savePath = AuditoryDirectoryHelper
				.ObtainStage2SavePath(sc.getConson());
		File picFileForDelete = new File(savePath, sc.getPicture());
		File voiceForDelete = new File(savePath, sc.getSentenceVoicechip());
		File sentenceVoiceForDelete = new File(savePath, sc.getSentenceVoicechip());
		AuditoryDirectoryHelper.deleteRecursive(picFileForDelete);
		AuditoryDirectoryHelper.deleteRecursive(voiceForDelete);
		AuditoryDirectoryHelper.deleteRecursive(sentenceVoiceForDelete);
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		createProcessForCreateAndUpdate(this);
		
	}

	private void createProcessForCreateAndUpdate(Stage2Conson sc){
		String baseUrlString = "http://demo.pm/auditory/site/upload";
		String fullUrlStringForGetPicture = baseUrlString + "/" + sc.getPicture();
		String fullUrlStringForGetVoicechip = baseUrlString + "/" + sc.getVoicechip();
		String fullUrlStringForGetSentenceVoicechip = baseUrlString + "/" + sc.getSentenceVoicechip();
		
		@SuppressWarnings("unused")
		URL fullUrlForGetPicture, fullUrlForGetVoicechip, fullUrlForGetSentenceVoicechip;
		
		try {
			fullUrlForGetPicture = new URL(fullUrlStringForGetPicture);
			fullUrlForGetVoicechip = new URL(fullUrlStringForGetVoicechip);
			fullUrlForGetSentenceVoicechip = new URL(fullUrlStringForGetSentenceVoicechip);
			Log.d("downloadservice_full_url",fullUrlStringForGetPicture);
			
			String savePath = AuditoryDirectoryHelper
					.ObtainStage2SavePath(sc.getConson()); 
			Log.d("savePath123",savePath);
			//ContextStorage.getInstance().bindService();
			CallDownloadService callDownloadService = new CallDownloadService();
			callDownloadService.startDownloadService(ContextStorage.getInstance().getContext(), fullUrlForGetPicture, savePath, sc.getPicture());
			//callDownloadService.startDownloadService(ContextStorage.getInstance().getContext(), fullUrlForGetVoicechip, savePath, sc.getVoicechip());
			callDownloadService.startDownloadService(ContextStorage.getInstance().getContext(), fullUrlForGetSentenceVoicechip, savePath, sc.getSentenceVoicechip());
			
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void onUpdate(DatabaseModel server) {
		// TODO Auto-generated method stub
		deleteFunctionForOnDeleteAndOnUpdate(this);
		createProcessForCreateAndUpdate((Stage2Conson) server);
	}

	public String getVoicechip() {
		return voicechip;
	}

	public void setVoicechip(String voicechip) {
		this.voicechip = voicechip;
	}

	public String getSentenceVoicechip() {
		return sentenceVoicechip;
	}

	public void setSentenceVoicechip(String sentenceVoicechip) {
		this.sentenceVoicechip = sentenceVoicechip;
	}
}