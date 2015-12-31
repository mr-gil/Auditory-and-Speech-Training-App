package hk.com.playmore.syncframework.util;

public interface DatabaseModel {
	public String getId();
	
	public void onDelete();
	
	public void onCreate();
	
	public void onUpdate(DatabaseModel server);
}
