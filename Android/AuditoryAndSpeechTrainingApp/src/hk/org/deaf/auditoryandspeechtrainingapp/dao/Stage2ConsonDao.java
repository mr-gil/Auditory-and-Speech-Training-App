package hk.org.deaf.auditoryandspeechtrainingapp.dao;


import hk.org.deaf.auditoryandspeechtrainingapp.model.Stage2Conson;
import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class Stage2ConsonDao extends BaseDaoImpl<Stage2Conson, String> {

	public Stage2ConsonDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Stage2Conson.class);
	}

}