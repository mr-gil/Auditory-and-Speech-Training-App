package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import android.util.Log;
import hk.org.deaf.auditoryandspeechtrainingapp.model.PassToStage2StateRecord;

public class ProcessPassToStage2StateRecord {
	boolean TESTMODE = true;
	GetDataFromLocalDatabase<PassToStage2StateRecord> process;
	PassToStage2StateRecord saveRecord;

	public ProcessPassToStage2StateRecord(
			GetDataFromLocalDatabase<PassToStage2StateRecord> process, int user) {
		this.process = process;
		saveRecord = process.getPassToStage2StateRecord(user);
	}

	public boolean getPassToStage2StateRecord(int user) {
		if (TESTMODE) return true;
		
		boolean state = true;
		if (saveRecord != null) {
			if (saveRecord.getPhVsP() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getPhVsTh() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getThVsT() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getThVsKh() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getKhVsK() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getsVsTs() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getTshVsTs() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getnVsM() == 1)
				state = state && true;
			else
				state = state && false;

			if (saveRecord.getNgVsN() == 1)
				state = state && true;
			else
				state = state && false;

			return state;
		} else {
			Log.d("PassToStageTwoStateObject","is null");
		}
		
		return false;
	}

	public void saveStateToDatabase(int state, int conson) {
		if (saveRecord != null){
			switch (conson) {
				case 0:
					saveRecord.setPhVsP(state);
				case 1:
					saveRecord.setPhVsTh(state);
				case 2:
					saveRecord.setThVsT(state);
				case 3:
					saveRecord.setThVsKh(state);
				case 4:
					saveRecord.setKhVsK(state);
				case 5:
					saveRecord.setsVsTs(state);
				case 6:
					saveRecord.setTshVsTs(state);
				case 7:
					saveRecord.setnVsM(state);
				case 8:
					saveRecord.setNgVsN(state);
			}
	
			process.saveDataToLocalDatabase(saveRecord);
		} else {
			Log.d("saveRecord","is null");
		}
	}
}
