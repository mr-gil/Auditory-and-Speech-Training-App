package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.com.playmore.syncframework.util.DatabaseModel;
import hk.org.deaf.auditoryandspeechtrainingapp.dao.StageOneResultRecordDao;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "StageOneResultRecords", daoClass = StageOneResultRecordDao.class)
public class StageOneResultRecord implements Serializable, DatabaseModel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7429047814985671750L;

	@DatabaseField(id = true)
	private String id;

	@DatabaseField
	private int conson;

	@DatabaseField
	private int user;

	@DatabaseField
	private int score;

	@DatabaseField
	private String create_time;

	

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

	
	
	
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
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

	
}