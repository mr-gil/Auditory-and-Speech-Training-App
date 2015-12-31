package hk.org.deaf.auditoryandspeechtrainingapp.model;

import hk.com.playmore.syncframework.util.DatabaseModel;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

public class UserData implements Serializable, DatabaseModel {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5221806353592950958L;
	@DatabaseField(id = true)
	private String id;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStage2_enabled() {
		return stage2_enabled;
	}

	public void setStage2_enabled(int stage2_enabled) {
		this.stage2_enabled = stage2_enabled;
	}

	private String name;
	private String email;
	private int stage2_enabled;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdate(DatabaseModel server) {
		// TODO Auto-generated method stub

	}
}
