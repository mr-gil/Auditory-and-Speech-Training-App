package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import java.io.Serializable;

import org.apache.http.NameValuePair;

public class PMNameValuePair implements Serializable, NameValuePair{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1008469189032167033L;
	String name, value;
	
	public PMNameValuePair(String name, String value){
		this.name = name;
		this.value = value;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

}
