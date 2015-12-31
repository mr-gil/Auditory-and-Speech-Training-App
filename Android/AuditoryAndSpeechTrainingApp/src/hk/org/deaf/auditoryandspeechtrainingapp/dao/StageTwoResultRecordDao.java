package hk.org.deaf.auditoryandspeechtrainingapp.dao;


import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoResultRecord;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class StageTwoResultRecordDao extends BaseDaoImpl<StageTwoResultRecord, String> {

	public StageTwoResultRecordDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, StageTwoResultRecord.class);
	}

}