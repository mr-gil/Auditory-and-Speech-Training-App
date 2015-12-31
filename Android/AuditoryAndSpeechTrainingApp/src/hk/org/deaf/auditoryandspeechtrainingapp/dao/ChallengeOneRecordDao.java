package hk.org.deaf.auditoryandspeechtrainingapp.dao;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengeOneRecordDao extends BaseDaoImpl<ChallengeOneRecordDao, String>{
	public ChallengeOneRecordDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, ChallengeOneRecordDao.class);
	}
}
