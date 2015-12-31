package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class StageOneConsonDao extends BaseDaoImpl<StageOneConson, String>{
	public StageOneConsonDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, StageOneConson.class);
	}
}
