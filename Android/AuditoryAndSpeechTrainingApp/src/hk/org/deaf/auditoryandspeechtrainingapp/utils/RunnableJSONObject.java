package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import hk.com.playmore.syncframework.util.RunnableArgument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class RunnableJSONObject implements RunnableArgument{

 
	@Override
	final public void run(String array) {
		try {
			JSONObject json = (JSONObject) new JSONTokener(array).nextValue();
	        run(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public abstract  void run(JSONObject object);
		// TODO Auto-generated method stub
		
	
}