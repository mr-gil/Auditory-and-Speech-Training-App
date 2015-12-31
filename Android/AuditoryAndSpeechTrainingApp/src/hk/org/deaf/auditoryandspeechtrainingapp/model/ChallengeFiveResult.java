package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.com.playmore.syncframework.util.DatabaseModel;
import hk.org.deaf.auditoryandspeechtrainingapp.interfaces.ChallengeResultFunctions;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

public class ChallengeFiveResult implements Serializable, DatabaseModel, ChallengeResultFunctions {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7777130383724516865L;
	@DatabaseField(id = true)
	private String id;

	@DatabaseField
	private int userId;
	
	public int getUserId() {
		return userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isResultCompleted() {
		return resultCompleted;
	}

	public void setResultCompleted(boolean resultCompleted) {
		this.resultCompleted = resultCompleted;
	}

	@DatabaseField
	private boolean resultCompleted;

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

	@Override
	public void setUserId(int userId) {
		// TODO Auto-generated method stub
		this.userId = userId;
	}
}
