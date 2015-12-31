package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeTwoResult;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeTwoResultDao extends BaseDaoImpl<ChallengeTwoResult, String>{
	public ChallengeTwoResultDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeTwoResult.class);
	}
}
