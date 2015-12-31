package hk.org.deaf.auditoryandspeechtrainingapp.dao;


import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneResultRecord;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class StageOneResultRecordDao extends BaseDaoImpl<StageOneResultRecord, String> {

	public StageOneResultRecordDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, StageOneResultRecord.class);
	}

}