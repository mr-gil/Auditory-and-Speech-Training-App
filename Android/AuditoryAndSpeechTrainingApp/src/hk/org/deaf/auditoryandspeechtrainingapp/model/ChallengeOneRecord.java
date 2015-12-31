package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.com.playmore.syncframework.util.DatabaseModel;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

public class ChallengeOneRecord implements Serializable, DatabaseModel {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5486160708349267521L;
	@DatabaseField(id = true)
	private String id;
	
	@DatabaseField
	private int userId;
	
	@DatabaseField
	private int day;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	

	
	

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdate(DatabaseModel server) {
		// TODO Auto-generated method stub

	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
