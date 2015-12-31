package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFourResult;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeFourResultDao extends BaseDaoImpl<ChallengeFourResult, String>{
	public ChallengeFourResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeFourResult.class);
	}
}
