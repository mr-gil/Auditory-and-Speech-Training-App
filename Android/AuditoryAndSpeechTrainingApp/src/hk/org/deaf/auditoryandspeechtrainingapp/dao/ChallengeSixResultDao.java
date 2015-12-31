package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSixResult;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeSixResultDao extends BaseDaoImpl<ChallengeSixResult, String>{
	public ChallengeSixResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeSixResult.class);
	}
}
