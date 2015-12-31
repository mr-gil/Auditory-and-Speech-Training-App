package hk.com.playmore.syncframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.format.DateUtils;
import android.util.Log;

public class HttpRequest {

    private final HttpClient httpClient; 
    
    private String baseURL;

    public HttpRequest() {
        httpClient = new DefaultHttpClient();
    }

    public HttpRequest(Context context) {
        httpClient = getHttpClient(context);
    }
    
    public HttpRequest(HttpClient aHttpClient) {
        httpClient = aHttpClient;
    }

	public HttpClient getHttpClient() {
		return httpClient;
	}
	
	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
    
	public void get(String url, HttpHandler handler) throws HttpHandlerException {
		get(url, null, handler);
	}

	public void get(String url, List<NameValuePair> params, HttpHandler handler) throws HttpHandlerException {
		String query = "";
		if ( params != null ) {
			try {
				query = getQuery(params);
			} catch (UnsupportedEncodingException e) {
                throw new HttpHandlerException("Malformed query for " + params.toString(), e);
			}
			if ( query.length() > 0 ) {
				query = '?' + query;
			}
		}
		Log.d("HttpRequest", baseURL + url + query);
		final HttpUriRequest req = new HttpGet(baseURL + url + query);
		exec(req, handler);
	}

    public void post(String url, List<NameValuePair> params, HttpHandler handler) throws HttpHandlerException {
        final HttpPost request = new HttpPost(baseURL + url);
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new HttpHandlerException("Malformed request for " + params.toString(), e);
		}
        exec(request, handler);
    }

    public void put(String url, List<NameValuePair> params, HttpHandler handler) throws HttpHandlerException {
        final HttpPut request = new HttpPut(baseURL + url);
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new HttpHandlerException("Malformed request for " + params.toString(), e);
		}
        exec(request, handler);
    }
    
	public void delete(String url, HttpHandler handler) throws HttpHandlerException {
		delete(url, null, handler);
	}

	public void delete(String url, List<NameValuePair> params, HttpHandler handler) throws HttpHandlerException {
		String query = "";
		if ( params != null ) {
			try {
				query = getQuery(params);
			} catch (UnsupportedEncodingException e) {
                throw new HttpHandlerException("Malformed query for " + params.toString(), e);
			}
			if ( query.length() > 0 ) {
				query = '?' + query;
			}
		}
		final HttpUriRequest req = new HttpDelete(baseURL + url + query);
		exec(req, handler);
	}
	 
    public void exec(HttpUriRequest request, HttpHandler handler) throws HttpHandlerException {
        try {
            final HttpResponse resp = getHttpClient().execute(request);
            final int status = resp.getStatusLine().getStatusCode();
            final InputStream input = resp.getEntity().getContent();
            
            try {
            	 Object object = processInput(request, input);
            	 handler.onResponse(status, object);
            } catch (NullPointerException e) {
                throw new HttpHandlerException("Null response for " + request.getRequestLine(), e);
            } finally {
                if (input != null) input.close();
            }
        } catch (HttpHandlerException e) {
            throw e;
        } catch (IOException e) {
            throw new HttpHandlerException("Problem reading remote response for "
                    + request.getRequestLine(), e);
        }
    }

    protected Object processInput(HttpUriRequest request, final InputStream input) throws HttpHandlerException
    {
    	return StringHelper.convertStreamToString(input);
    }

	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}
	
	private static final int SECOND_IN_MILLIS = (int) DateUtils.SECOND_IN_MILLIS;

	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";

	public static HttpClient getHttpClient(Context context) {
		final HttpParams params = new BasicHttpParams();

		// Use generous timeouts for slow mobile networks
		HttpConnectionParams
				.setConnectionTimeout(params, 20 * SECOND_IN_MILLIS);
		HttpConnectionParams.setSoTimeout(params, 20 * SECOND_IN_MILLIS);

		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpProtocolParams.setUserAgent(params, buildUserAgent(context));

		final DefaultHttpClient client = new DefaultHttpClient(params);

		client.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(org.apache.http.HttpRequest request,
					HttpContext context) throws HttpException, IOException {
				if (!request.containsHeader(HEADER_ACCEPT_ENCODING)) {
					request.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
				}
			}
		});

		client.addResponseInterceptor(new HttpResponseInterceptor() {
			public void process(HttpResponse response, HttpContext context) {
				// Inflate any responses compressed with gzip
				final HttpEntity entity = response.getEntity();
				final Header encoding = entity.getContentEncoding();
				if (encoding != null) {
					for (HeaderElement element : encoding.getElements()) {
						if (element.getName().equalsIgnoreCase(ENCODING_GZIP)) {
							response.setEntity(new InflatingEntity(response
									.getEntity()));
							break;
						}
					}
				}
			}
		});

		return client;
	}

	private static String buildUserAgent(Context context) {
		try {
			final PackageManager manager = context.getPackageManager();
			final PackageInfo info = manager.getPackageInfo(
					context.getPackageName(), 0);

			// Some APIs require "(gzip)" in the user-agent string.
			return info.packageName + "/" + info.versionName + " ("
					+ info.versionCode + ") (gzip)";
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	private static class InflatingEntity extends HttpEntityWrapper {
		public InflatingEntity(HttpEntity wrapped) {
			super(wrapped);
		}

		@Override
		public InputStream getContent() throws IOException {
			return new GZIPInputStream(wrappedEntity.getContent());
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}

}
