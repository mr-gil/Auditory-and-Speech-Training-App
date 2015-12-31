package hk.com.playmore.syncframework.helper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public abstract class PMDatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private final String LOG_NAME = getClass().getName();

	protected HashMap<Class<?>, Dao<?, ?>> cachedDaos;
	
	protected boolean shouldDeleteOnUpdate = true;
	
	public PMDatabaseHelper(Context context, String databaseName, int databaseVersion) {
		super(context, databaseName, null, databaseVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
		try {
			for (Class<?> cls : getClasses()) {
				TableUtils.createTable(connectionSource, cls);
			}
		} catch (SQLException e) {
			Log.e(LOG_NAME, "Could not create new table", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		if (!shouldDeleteOnUpdate) {
			return;
		}
		try {
			for (Class<?> cls : getClasses()) {
				TableUtils.dropTable(connectionSource, cls, true);
			}

			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(LOG_NAME, "Could not upgrade the table", e);
		}
	}
	
	protected abstract List<Class<?>> getClasses();

}
