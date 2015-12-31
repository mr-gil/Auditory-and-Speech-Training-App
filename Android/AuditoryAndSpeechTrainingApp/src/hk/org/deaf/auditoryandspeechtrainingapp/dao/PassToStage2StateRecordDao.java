package hk.org.deaf.auditoryandspeechtrainingapp.dao;


import hk.org.deaf.auditoryandspeechtrainingapp.model.PassToStage2StateRecord;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class PassToStage2StateRecordDao extends BaseDaoImpl<PassToStage2StateRecord, String> {

	public PassToStage2StateRecordDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, PassToStage2StateRecord.class);
	}

}