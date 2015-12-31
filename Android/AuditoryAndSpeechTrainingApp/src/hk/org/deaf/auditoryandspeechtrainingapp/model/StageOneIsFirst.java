package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.org.deaf.auditoryandspeechtrainingapp.dao.StageOneIsFirstDao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "StageOneIsFirsts", daoClass = StageOneIsFirstDao.class)
public class StageOneIsFirst {

	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimePhVsP;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimePhVsTh;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeThVsT;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeThVsKh;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeKhVsK;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeSVsTs;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeTshVsTs;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeNVsM;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeNgVsN;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFirstTimePhVsP() {
		return firstTimePhVsP;
	}

	public void setFirstTimePhVsP(int firstTimePhVsP) {
		this.firstTimePhVsP = firstTimePhVsP;
	}

	public int getFirstTimePhVsTh() {
		return firstTimePhVsTh;
	}

	public void setFirstTimePhVsTh(int firstTimePhVsTh) {
		this.firstTimePhVsTh = firstTimePhVsTh;
	}

	public int getFirstTimeThVsT() {
		return firstTimeThVsT;
	}

	public void setFirstTimeThVsT(int firstTimeThVsT) {
		this.firstTimeThVsT = firstTimeThVsT;
	}

	public int getFirstTimeThVsKh() {
		return firstTimeThVsKh;
	}

	public void setFirstTimeThVsKh(int firstTimeThVsKh) {
		this.firstTimeThVsKh = firstTimeThVsKh;
	}

	public int getFirstTimeKhVsK() {
		return firstTimeKhVsK;
	}

	public void setFirstTimeKhVsK(int firstTimeKhVsK) {
		this.firstTimeKhVsK = firstTimeKhVsK;
	}

	public int getFirstTimeSVsTs() {
		return firstTimeSVsTs;
	}

	public void setFirstTimeSVsTs(int firstTimeSVsTs) {
		this.firstTimeSVsTs = firstTimeSVsTs;
	}

	public int getFirstTimeTshVsTs() {
		return firstTimeTshVsTs;
	}

	public void setFirstTimeTshVsTs(int firstTimeTshVsTs) {
		this.firstTimeTshVsTs = firstTimeTshVsTs;
	}

	public int getFirstTimeNVsM() {
		return firstTimeNVsM;
	}

	public void setFirstTimeNVsM(int firstTimeNVsM) {
		this.firstTimeNVsM = firstTimeNVsM;
	}

	public int getFirstTimeNgVsN() {
		return firstTimeNgVsN;
	}

	public void setFirstTimeNgVsN(int firstTimeNgVsN) {
		this.firstTimeNgVsN = firstTimeNgVsN;
	}
	
}
