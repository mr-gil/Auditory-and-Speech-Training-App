package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeEightResult;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeEightResultDao extends BaseDaoImpl<ChallengeEightResult, String>{
	public ChallengeEightResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeEightResult.class);
	}
}
