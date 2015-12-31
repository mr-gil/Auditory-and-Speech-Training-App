package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFiveResult;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeFiveResultDao extends BaseDaoImpl<ChallengeFiveResult, String>{
	public ChallengeFiveResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeFiveResult.class);
	}
}
