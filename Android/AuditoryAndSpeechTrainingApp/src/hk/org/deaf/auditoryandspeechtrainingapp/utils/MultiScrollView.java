package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class MultiScrollView extends ScrollView {
	public HorizontalScrollView hscroll;

	public MultiScrollView(Context context) {
		super(context);
		hscroll = new HorizontalScrollView(context);
		this.addView(hscroll);
	}

	public void AddChild(View child) {
		hscroll.addView(child);
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		hscroll.dispatchTouchEvent(event);
		onTouchEvent(event);
		return true;
	}
}
