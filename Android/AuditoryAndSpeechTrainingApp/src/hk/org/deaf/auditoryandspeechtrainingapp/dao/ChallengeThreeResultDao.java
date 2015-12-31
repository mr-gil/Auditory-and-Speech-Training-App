package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeThreeResult;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeThreeResultDao extends BaseDaoImpl<ChallengeThreeResult, String>{
	public ChallengeThreeResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeThreeResult.class);
	}
}
