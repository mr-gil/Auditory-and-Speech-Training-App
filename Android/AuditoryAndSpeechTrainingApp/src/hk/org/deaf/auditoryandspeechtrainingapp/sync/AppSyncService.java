package hk.org.deaf.auditoryandspeechtrainingapp.sync;

import hk.com.playmore.syncframework.helper.PMDatabaseHelper;
import hk.com.playmore.syncframework.service.PMSyncService;
import hk.com.playmore.syncframework.util.HttpRequest;
import hk.org.deaf.auditoryandspeechtrainingapp.BuildConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.RequestLine;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSyncService extends PMSyncService {
	
	@Override
	protected PMDatabaseHelper getNewDatabaseHelper() {
		return new AppDatabaseHelper(this);
	}

	protected String getCurrentUserId() {
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		int userId = prefs.getInt("user_id", 1);
		return String.valueOf(userId);
	}

	@Override
	protected HttpRequest getNewHttpRequest() {
		HttpRequest request = new HttpRequest();
		request.setBaseURL(getBaseUrl());

		DefaultHttpClient httpClient = (DefaultHttpClient) request
				.getHttpClient();
		httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(org.apache.http.HttpRequest request,
					HttpContext context) throws HttpException, IOException {
				RequestLine requestLine = request.getRequestLine();
				List<NameValuePair> params = null;
				if (requestLine.getMethod().equalsIgnoreCase("get") || requestLine.getMethod().equalsIgnoreCase("delete")) {
					String path = requestLine.getUri();
					try {
						params = URLEncodedUtils.parse(new URI(path), "UTF-8");
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				} else {
					if (request instanceof HttpEntityEnclosingRequest) {
					    HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
						params = URLEncodedUtils.parse(entity);
					}
				}
				request.addHeader("X-APP-USER-ID", getCurrentUserId());
				request.addHeader("X-APP-DEVICE-DPI", "1");
				request.addHeader("X-APP-SECRET", tokenize(params));
			}
		});
		return request;
	}

	protected String tokenize(List<NameValuePair> params) {
		Collections.sort(params, new Comparator<NameValuePair>() {
			@Override
			public int compare(NameValuePair pair1, NameValuePair pair2) {
				return pair1.getName().compareTo(pair2.getName());
			}
		});
	
		StringBuilder digest = new StringBuilder();
	
		digest.append("auditory_");
		
		for (NameValuePair pair : params) {
			digest.append(pair.getValue());
		}
	
		if (getCurrentUserId() != null) {
			digest.append(getCurrentUserId());
		}
	
		try {
			return SHA1(digest.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("utf-8"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

	public static String getBaseUrl() {
		/*if (BuildConfig.DEBUG) {
			return "http://demo.pm/auditory/site";
		} else {
			return "http://demo.pm/auditory/site";
		}*/
		return "http://stapps.deaf.org.hk";
	}

	
}
