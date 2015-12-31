package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.org.deaf.auditoryandspeechtrainingapp.dao.StageTwoIsFirstDao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "StageTwoIsFirsts", daoClass = StageTwoIsFirstDao.class)
public class StageTwoIsFirst {
	
	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimePh;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeTh;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeKh;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeS;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeTsh;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeNg;
	
	@DatabaseField(defaultValue = "1")
	private int firstTimeN;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFirstTimePh() {
		return firstTimePh;
	}
	public void setFirstTimePh(int firstTimePh) {
		this.firstTimePh = firstTimePh;
	}
	public int getFirstTimeTh() {
		return firstTimeTh;
	}
	public void setFirstTimeTh(int firstTimeTh) {
		this.firstTimeTh = firstTimeTh;
	}
	public int getFirstTimeKh() {
		return firstTimeKh;
	}
	public void setFirstTimeKh(int firstTimeKh) {
		this.firstTimeKh = firstTimeKh;
	}
	public int getFirstTimeS() {
		return firstTimeS;
	}
	public void setFirstTimeS(int firstTimeS) {
		this.firstTimeS = firstTimeS;
	}
	public int getFirstTimeTsh() {
		return firstTimeTsh;
	}
	public void setFirstTimeTsh(int firstTimeTsh) {
		this.firstTimeTsh = firstTimeTsh;
	}
	public int getFirstTimeNg() {
		return firstTimeNg;
	}
	public void setFirstTimeNg(int firstTimeNg) {
		this.firstTimeNg = firstTimeNg;
	}
	public int getFirstTimeN() {
		return firstTimeN;
	}
	public void setFirstTimeN(int firstTimeN) {
		this.firstTimeN = firstTimeN;
	}
	
}
