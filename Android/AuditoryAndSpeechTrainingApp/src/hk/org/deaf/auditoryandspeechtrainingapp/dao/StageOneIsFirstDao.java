package hk.org.deaf.auditoryandspeechtrainingapp.dao;


import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneIsFirst;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class StageOneIsFirstDao extends BaseDaoImpl<StageOneIsFirst, String> {

	public StageOneIsFirstDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, StageOneIsFirst.class);
	}

}