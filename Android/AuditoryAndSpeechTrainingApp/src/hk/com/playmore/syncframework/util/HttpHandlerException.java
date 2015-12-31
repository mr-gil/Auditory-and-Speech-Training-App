package hk.com.playmore.syncframework.util;

public class HttpHandlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8550546500937620686L;

	public HttpHandlerException(String message) {
        super(message);
    }
	
    public HttpHandlerException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }

    @Override
    public String toString() {
        if (getCause() != null) {
            return getLocalizedMessage() + ": " + getCause();
        } else {
            return getLocalizedMessage();
        }
    }

}
