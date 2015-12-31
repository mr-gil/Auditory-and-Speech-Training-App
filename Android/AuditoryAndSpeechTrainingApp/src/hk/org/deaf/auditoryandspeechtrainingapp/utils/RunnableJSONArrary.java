package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.com.playmore.syncframework.util.RunnableArgument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public abstract class RunnableJSONArrary implements RunnableArgument{

 
	@Override
	final public void run(String array) {
		try {
			JSONArray json = (JSONArray) new JSONTokener(array).nextValue();
	        run(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void run(JSONArray jsonArray);
}