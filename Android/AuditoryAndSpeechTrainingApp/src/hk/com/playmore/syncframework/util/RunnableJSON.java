package hk.com.playmore.syncframework.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class RunnableJSON implements RunnableArgument {
	@Override
	final public void run(String object) {
		try {
			JSONObject json = (JSONObject) new JSONTokener(object).nextValue();
	        run(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void run(JSONObject object);
}
