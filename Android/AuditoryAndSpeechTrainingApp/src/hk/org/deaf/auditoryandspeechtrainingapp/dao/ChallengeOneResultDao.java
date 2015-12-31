package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeOneResult;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeOneResultDao extends BaseDaoImpl<ChallengeOneResult, String>{
	public ChallengeOneResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeOneResult.class);
	}
}
