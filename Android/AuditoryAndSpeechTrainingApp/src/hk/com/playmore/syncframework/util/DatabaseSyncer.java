package hk.com.playmore.syncframework.util;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

public class DatabaseSyncer<T extends DatabaseModel> {

	@SuppressWarnings("unchecked")
	public void sync(Dao<T, String> dao, List<T> localList,
			List<T> serverList) throws SQLException, IllegalArgumentException, IllegalAccessException {
		Comparator<Object> idComparator = new Comparator<Object>() {
			@Override
			public int compare(Object olhs, Object orhs) {
				T lhs = (T) olhs;
				T rhs = (T) orhs;
				return lhs.getId().compareTo(rhs.getId());
			}
		};

		Object[] locals = localList.toArray();
		Arrays.sort(locals, idComparator);

		Object[] servers = serverList.toArray();
		Arrays.sort(servers, idComparator);

		Field[] cachedLocalFields = null;
		Field[] cachedLocalIfNullFields = null;
		
		int l = 0, s = 0;
		while (l < locals.length && s < servers.length) {
			T local = (T) locals[l];
			T server = (T) servers[s];

			String oid = local.getId();
			String nid = server.getId();

			if (oid.compareTo(nid) < 0) {
				// Server id is even larger than us. Means old record is deleted
				// in server. Proceeded one local.
				local.onDelete();
				dao.delete(local);
				l++;
			} else if (oid.compareTo(nid) > 0) {
				// Server id is smaller than us. Found new item and add this.
				// Proceed one server.
				dao.createOrUpdate(server);
				server.onCreate();
				s++;
			} else {
				// Id is the same. Do the update. Process one server and one
				// local.
				
				if (cachedLocalFields == null || cachedLocalIfNullFields == null) {
					cachedLocalFields = new Field[0];
					cachedLocalIfNullFields = new Field[0];
					ArrayList<Field> localFields = new ArrayList<Field>();
					ArrayList<Field> localIfNullFields = new ArrayList<Field>();
					Field[] fields = server.getClass().getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						SyncLocal localAnnotation = field.getAnnotation(
								SyncLocal.class);
						if (localAnnotation != null) {
							localFields.add(field);
						}
						SyncLocalIfNull localIfNullAnnotation = field.getAnnotation(
								SyncLocalIfNull.class);
						if (localIfNullAnnotation != null) {
							localIfNullFields.add(field);
						}
					}
					cachedLocalFields = localFields.toArray(cachedLocalFields);
					cachedLocalIfNullFields = localIfNullFields.toArray(cachedLocalIfNullFields);
				}
				for (Field field : cachedLocalFields) {
					Log.d("model sync", "Use local copy of " + field.getName() + " for " + local.getId());
					field.set(server, field.get(local));
				}
				for (Field field : cachedLocalIfNullFields) {
					if (field.get(server) == null) {
						Log.d("model sync", "Useed local copy of " + field.getName() + " for " + local.getId() + " as null");
						field.set(server, field.get(local));
					}
				}

				local.onUpdate(server);
				dao.update(server);
				l++;
				s++;
			}
		}
		while (l < locals.length) {
			// Still have unprocessed old stuff. Remove them
			((DatabaseModel) locals[l]).onDelete();
			dao.delete((T) locals[l]);
			l++;
		}
		while (s < servers.length) {
			// Still have unprocessed new stuff. ADd them
			dao.createOrUpdate((T) servers[s]);
			((DatabaseModel) servers[s]).onCreate();
			s++;
		}
	}
}
