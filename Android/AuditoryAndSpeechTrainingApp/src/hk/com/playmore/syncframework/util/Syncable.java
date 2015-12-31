package hk.com.playmore.syncframework.util;

import hk.com.playmore.syncframework.helper.PMDataSource;

/*
 * This interface should be used for Context only
 */
public interface Syncable {
	
	public void syncTaskDidAdd(int tag, int lifetime, PMDataSource helper);
	
	public void syncTaskDidFinish(int tag);
	
}
