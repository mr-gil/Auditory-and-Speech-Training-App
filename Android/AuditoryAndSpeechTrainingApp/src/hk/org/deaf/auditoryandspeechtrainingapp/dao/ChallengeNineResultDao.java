package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeNineResult;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeNineResultDao extends BaseDaoImpl<ChallengeNineResult, String>{
	public ChallengeNineResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeNineResult.class);
	}
}
