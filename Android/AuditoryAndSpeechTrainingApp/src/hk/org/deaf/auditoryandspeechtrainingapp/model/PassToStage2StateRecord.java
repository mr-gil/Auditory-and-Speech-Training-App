package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.org.deaf.auditoryandspeechtrainingapp.dao.PassToStage2StateRecordDao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "PassToStage2StateRecords", daoClass = PassToStage2StateRecordDao.class)
public class PassToStage2StateRecord {

	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField
	private int user;
	
	@DatabaseField(defaultValue = "0")
	private int phVsP;
	
	@DatabaseField(defaultValue = "0")
	private int phVsTh;
	
	@DatabaseField(defaultValue = "0")
	private int thVsT;
	
	@DatabaseField(defaultValue = "0")
	private int thVsKh;
	
	@DatabaseField(defaultValue = "0")
	private int khVsK;
	
	@DatabaseField(defaultValue = "0")
	private int sVsTs;
	
	@DatabaseField(defaultValue = "0")
	private int tshVsTs;
	
	@DatabaseField(defaultValue = "0")
	private int nVsM;
	
	@DatabaseField(defaultValue = "0")
	private int ngVsN;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public int getPhVsP() {
		return phVsP;
	}
	public void setPhVsP(int phVsP) {
		this.phVsP = phVsP;
	}
	public int getPhVsTh() {
		return phVsTh;
	}
	public void setPhVsTh(int phVsTh) {
		this.phVsTh = phVsTh;
	}
	public int getThVsT() {
		return thVsT;
	}
	public void setThVsT(int thVsT) {
		this.thVsT = thVsT;
	}
	public int getThVsKh() {
		return thVsKh;
	}
	public void setThVsKh(int thVsKh) {
		this.thVsKh = thVsKh;
	}
	public int getKhVsK() {
		return khVsK;
	}
	public void setKhVsK(int khVsK) {
		this.khVsK = khVsK;
	}
	public int getsVsTs() {
		return sVsTs;
	}
	public void setsVsTs(int sVsTs) {
		this.sVsTs = sVsTs;
	}
	public int getTshVsTs() {
		return tshVsTs;
	}
	public void setTshVsTs(int tshVsTs) {
		this.tshVsTs = tshVsTs;
	}
	public int getnVsM() {
		return nVsM;
	}
	public void setnVsM(int nVsM) {
		this.nVsM = nVsM;
	}
	public int getNgVsN() {
		return ngVsN;
	}
	public void setNgVsN(int ngVsN) {
		this.ngVsN = ngVsN;
	}
	
}
