package hk.com.playmore.syncframework.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class SyncResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public SyncResultReceiver(Handler handler) {
        super(handler);
    }

    public SyncResultReceiver(Handler handler, Receiver receiver) {
        super(handler);
    	setReceiver(receiver);
    }
    
    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
