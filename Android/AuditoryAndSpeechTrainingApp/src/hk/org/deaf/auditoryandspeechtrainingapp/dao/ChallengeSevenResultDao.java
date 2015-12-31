package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSevenResult;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeSevenResultDao extends BaseDaoImpl<ChallengeSevenResult, String>{
	public ChallengeSevenResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeSevenResult.class);
	}
}
