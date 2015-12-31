package hk.org.deaf.auditoryandspeechtrainingapp.dao;


import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoIsFirst;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class StageTwoIsFirstDao extends BaseDaoImpl<StageTwoIsFirst, String> {

	public StageTwoIsFirstDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, StageTwoIsFirst.class);
	}

}