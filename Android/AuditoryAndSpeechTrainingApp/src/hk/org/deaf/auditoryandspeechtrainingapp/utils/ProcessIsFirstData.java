package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import java.util.ArrayList;

import android.util.Log;

import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoIsFirst;

public class ProcessIsFirstData<T> {
	private GetDataFromLocalDatabase<T> getDataFromLocalDatabase;

	public ProcessIsFirstData(
			GetDataFromLocalDatabase<T> getDataFromLocalDatabase) {
		this.getDataFromLocalDatabase = getDataFromLocalDatabase;
	}

	public void getDataFromDataBase() {
		ArrayList<T> localDatas;
		T data;
		localDatas = getDataFromLocalDatabase.getIsFirstDataFromLocalDatabase();
		if (localDatas != null && localDatas.size() > 0) {
			data = localDatas.get(0);
			if (data instanceof StageOneIsFirst) {
				StageOneIsFirst stageOneIsFirst = (StageOneIsFirst) data;
				if (stageOneIsFirst.getFirstTimePhVsP() == 0)
					L1aTestFragementActivity.setFirstTime(0);
				if (stageOneIsFirst.getFirstTimePhVsTh() == 0)
					L1aTestFragementActivity.setFirstTime(1);
				if (stageOneIsFirst.getFirstTimeThVsT() == 0)
					L1aTestFragementActivity.setFirstTime(2);
				if (stageOneIsFirst.getFirstTimeThVsKh() == 0)
					L1aTestFragementActivity.setFirstTime(3);
				if (stageOneIsFirst.getFirstTimeKhVsK() == 0)
					L1aTestFragementActivity.setFirstTime(4);
				if (stageOneIsFirst.getFirstTimeSVsTs() == 0)
					L1aTestFragementActivity.setFirstTime(5);
				if (stageOneIsFirst.getFirstTimeTshVsTs() == 0)
					L1aTestFragementActivity.setFirstTime(6);
				if (stageOneIsFirst.getFirstTimeNVsM() == 0)
					L1aTestFragementActivity.setFirstTime(7);
				if (stageOneIsFirst.getFirstTimeNgVsN() == 0)
					L1aTestFragementActivity.setFirstTime(8);

			} else if (data instanceof StageTwoIsFirst) {
				StageTwoIsFirst stageTwoIsFirst = (StageTwoIsFirst) data;
				if (stageTwoIsFirst.getFirstTimePh() == 0)
					L2aTestFragementActivity.setFirstTime(0);
				if (stageTwoIsFirst.getFirstTimeTh() == 0)
					L2aTestFragementActivity.setFirstTime(1);
				if (stageTwoIsFirst.getFirstTimeKh() == 0)
					L2aTestFragementActivity.setFirstTime(2);
				if (stageTwoIsFirst.getFirstTimeS() == 0)
					L2aTestFragementActivity.setFirstTime(3);
				if (stageTwoIsFirst.getFirstTimeTsh() == 0)
					L2aTestFragementActivity.setFirstTime(4);
				if (stageTwoIsFirst.getFirstTimeNg() == 0)
					L2aTestFragementActivity.setFirstTime(5);
				if (stageTwoIsFirst.getFirstTimeN() == 0)
					L2aTestFragementActivity.setFirstTime(6);
			}
		} else {
			Log.d("empty row in table", "empty row in table StageTwoIsFirst");
		}

	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void saveDataToLocalDataBase(T data) {

		if (data instanceof StageOneIsFirst) {
			StageOneIsFirst stageOneIsFirst = (StageOneIsFirst) data;
			if (L1aTestFragementActivity.isFirstTime(0))
				stageOneIsFirst.setFirstTimePhVsP(1);
			else
				stageOneIsFirst.setFirstTimePhVsP(0);
			if (L1aTestFragementActivity.isFirstTime(1))
				stageOneIsFirst.setFirstTimePhVsTh(1);
			else
				stageOneIsFirst.setFirstTimePhVsTh(0);
			if (L1aTestFragementActivity.isFirstTime(2))
				stageOneIsFirst.setFirstTimeThVsT(1);
			else
				stageOneIsFirst.setFirstTimeThVsT(0);
			if (L1aTestFragementActivity.isFirstTime(3))
				stageOneIsFirst.setFirstTimeThVsKh(1);
			else
				stageOneIsFirst.setFirstTimeThVsKh(0);
			if (L1aTestFragementActivity.isFirstTime(4))
				stageOneIsFirst.setFirstTimeKhVsK(0);
			else
				stageOneIsFirst.setFirstTimeKhVsK(0);
			if (L1aTestFragementActivity.isFirstTime(5))
				stageOneIsFirst.setFirstTimeSVsTs(1);
			else
				stageOneIsFirst.setFirstTimeSVsTs(0);
			if (L1aTestFragementActivity.isFirstTime(6))
				stageOneIsFirst.setFirstTimeTshVsTs(1);
			else
				stageOneIsFirst.setFirstTimeTshVsTs(0);
			if (L1aTestFragementActivity.isFirstTime(7))
				stageOneIsFirst.setFirstTimeNVsM(1);
			else
				stageOneIsFirst.setFirstTimeNVsM(0);
			if (L1aTestFragementActivity.isFirstTime(8))
				stageOneIsFirst.setFirstTimeNgVsN(1);
			else
				stageOneIsFirst.setFirstTimeNgVsN(0);
			stageOneIsFirst.setId(1);

			if (stageOneIsFirst == null)
				Log.d("isFirstData is null", "isFirstData is null");
			getDataFromLocalDatabase
					.sentIsFirstDataToLocalDatabase((T) stageOneIsFirst);

		} else if (data instanceof StageTwoIsFirst) {
			StageTwoIsFirst stageTwoIsFirst = (StageTwoIsFirst) data;
			if (L2aTestFragementActivity.isFirstTime(0))
				stageTwoIsFirst.setFirstTimePh(1);
			else
				stageTwoIsFirst.setFirstTimePh(0);
			if (L2aTestFragementActivity.isFirstTime(1))
				stageTwoIsFirst.setFirstTimeTh(1);
			else
				stageTwoIsFirst.setFirstTimeTh(0);
			if (L2aTestFragementActivity.isFirstTime(2))
				stageTwoIsFirst.setFirstTimeKh(1);
			else
				stageTwoIsFirst.setFirstTimeKh(0);
			if (L2aTestFragementActivity.isFirstTime(3))
				stageTwoIsFirst.setFirstTimeS(1);
			else
				stageTwoIsFirst.setFirstTimeS(0);
			if (L2aTestFragementActivity.isFirstTime(4))
				stageTwoIsFirst.setFirstTimeTsh(1);
			else
				stageTwoIsFirst.setFirstTimeTsh(0);
			if (L2aTestFragementActivity.isFirstTime(5))
				stageTwoIsFirst.setFirstTimeNg(1);
			else
				stageTwoIsFirst.setFirstTimeNg(0);
			if (L2aTestFragementActivity.isFirstTime(6))
				stageTwoIsFirst.setFirstTimeN(1);
			else
				stageTwoIsFirst.setFirstTimeN(0);
			stageTwoIsFirst.setId(1);
			if (stageTwoIsFirst == null)
				Log.d("isFirstData is null", "isFirstData is null");
			getDataFromLocalDatabase
					.sentIsFirstDataToLocalDatabase((T) stageTwoIsFirst);
		}
	}

}
