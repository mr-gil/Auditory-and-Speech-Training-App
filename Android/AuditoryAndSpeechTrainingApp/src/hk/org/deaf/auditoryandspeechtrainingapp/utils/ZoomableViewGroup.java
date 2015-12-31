package hk.org.deaf.auditoryandspeechtrainingapp.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ZoomableViewGroup extends ViewGroup {

	public interface ShouldTouchDelegate {
		public boolean shouldTouch();
	}
	
	public interface OnZoomableViewZoomListener{
		public void onZoomToScaleFactor(float scaleFactor);
	}
	
	public interface OnZoomableViewSingleTapListener{
		public void onSingleTapConfirmed();
	}

	private ShouldTouchDelegate shouldTouchDelegate;
	private OnZoomableViewZoomListener onZoomListener;
	private OnZoomableViewSingleTapListener mSingleTapListener;

	public static final float MAX_SCALE = 2.5f;
	public static final float MIN_SCALE = 1;
	
	private static final int INVALID_POINTER_ID = 1;
	private int mActivePointerId = INVALID_POINTER_ID;
	protected OnGestureListener mGestureListener;
	protected GestureDetector mGestureDetector;

//	private float mScaleFactor = 1;
	private ScaleGestureDetector mScaleDetector;
	//private Matrix mScaleMatrix = new Matrix();
	//private Matrix mScaleMatrixInverse = new Matrix();

	//private float mPosX;
	//private float mPosY;
	//private Matrix mTranslateMatrix = new Matrix();
	//private Matrix mTranslateMatrixInverse = new Matrix();

	private float currentScaleFactor;
	
	private Matrix mTransformationMatrix = new Matrix();
	private Matrix mCanvasMatrix = new Matrix();
	
	private float mLastTouchX;
	private float mLastTouchY;

	private float mFocusY;

	private float mFocusX;

	private float[] mInvalidateWorkingArray = new float[6];
	private float[] mDispatchTouchEventWorkingArray = new float[2];
	private float[] mOnTouchEventWorkingArray = new float[2];

	public ZoomableViewGroup(Context context) {
		super(context);
		init();
	}
	
	public ZoomableViewGroup(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}
	
	public ZoomableViewGroup(Context context, AttributeSet attr, int def) {
		super(context, attr, def);
		init();
	}
	
	private void init(){
		mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
		//mTranslateMatrix.setTranslate(0, 0);
		//mScaleMatrix.setScale(1, 1);
		mTransformationMatrix.setTranslate(0, 0);
		
		mGestureListener = new GestureListener();
		mGestureDetector = new GestureDetector( getContext(), mGestureListener, null, true );	
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				child.layout(l, t, l + child.getMeasuredWidth(),
						t + child.getMeasuredHeight());
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
				//Log.e(null, "bfg  " + child.getLeft());
			}
		}
	}

	float ek;
	@Override
	protected void dispatchDraw(Canvas canvas) {
		//if (mScaleFactor < 1){
			//mScaleFactor = 1;
		//}
		//Log.i(null, " mScaleFactor   " +  mScaleFactor);
		//Log.e(null, "mPosX  " + mPosX +  " mPosY " + mPosY);
//		Log.e(null, "mFocusX  " + mFocusX +  " mFocusY " + mFocusY);
//		Log.e(null, "getWidth  " + getWidth() +  " getHeight " + getHeight());
		
		RectF rect = new RectF(0, 0, getWidth(), getHeight());
		
		mCanvasMatrix = canvas.getMatrix();
		mCanvasMatrix.preConcat(mTransformationMatrix);

//		Log.w(null, "top  " + rect.top +  " left " + rect.left + "\n right " + rect.right  +" centerX " + rect.centerX());
		
		//float dx = mPosX * mScaleFactor, dy = mPosY * mScaleFactor;
		//float tx = dx - getWidth()/2 * mScaleFactor, ty = dy - getHeight()/2 * mScaleFactor;

//		Log.w(null, "pBounds()   "   + canvas.getClipBounds());
		canvas.save();
	
		
		canvas.setMatrix(mCanvasMatrix);
		if (canvas.getClipBounds().left < 0) {
			mTransformationMatrix.preTranslate(canvas.getClipBounds().left, 0);
			mCanvasMatrix.preTranslate(canvas.getClipBounds().left, 0);
		}
		if (canvas.getClipBounds().top < 0) {
			mTransformationMatrix.preTranslate(0, canvas.getClipBounds().top);
			mCanvasMatrix.preTranslate(0, canvas.getClipBounds().top);
		}
		if (canvas.getClipBounds().bottom > getHeight()) {
			mTransformationMatrix.preTranslate(0, canvas.getClipBounds().bottom - getHeight());
			mCanvasMatrix.preTranslate(0, canvas.getClipBounds().bottom - getHeight());
		}
		if (canvas.getClipBounds().right > getWidth()) {
			mTransformationMatrix.preTranslate(canvas.getClipBounds().right - getWidth(), 0);
			mCanvasMatrix.preTranslate(canvas.getClipBounds().right - getWidth(), 0);
		}
		canvas.setMatrix(mCanvasMatrix);
		
		//canvas.setMatrix(mTransformationMatrix);
		
//		Log.w(null, ")   "   + canvas.getClipBounds());
		
		super.dispatchDraw(canvas);
		canvas.restore();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mDispatchTouchEventWorkingArray[0] = ev.getX();
		mDispatchTouchEventWorkingArray[1] = ev.getY();
		mDispatchTouchEventWorkingArray = screenPointsToScaledPoints(mDispatchTouchEventWorkingArray);
		ev.setLocation(mDispatchTouchEventWorkingArray[0],
				mDispatchTouchEventWorkingArray[1]);
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * Although the docs say that you shouldn't override this, I decided to do
	 * so because it offers me an easy way to change the invalidated area to my
	 * likening.
	 */
	@Override
	public ViewParent invalidateChildInParent(int[] location, Rect dirty) {

		mInvalidateWorkingArray[0] = dirty.left;
		mInvalidateWorkingArray[1] = dirty.top;
		mInvalidateWorkingArray[2] = dirty.right;
		mInvalidateWorkingArray[3] = dirty.bottom;

		mInvalidateWorkingArray = scaledPointsToScreenPoints(mInvalidateWorkingArray);
		dirty.set(Math.round(mInvalidateWorkingArray[0]),
				Math.round(mInvalidateWorkingArray[1]),
				Math.round(mInvalidateWorkingArray[2]),
				Math.round(mInvalidateWorkingArray[3]));

		float[] matVals = new float[9];
		mTransformationMatrix.getValues(matVals);
		float scaleFactor = matVals[0];
		location[0] *= scaleFactor;
		location[1] *= scaleFactor;
		return super.invalidateChildInParent(location, dirty);
	}

	private float[] scaledPointsToScreenPoints(float[] a) {
		//mScaleMatrix.mapPoints(a);
		//mTranslateMatrix.mapPoints(a);
		mTransformationMatrix.mapPoints(a);

		return a;
	}

	private float[] screenPointsToScaledPoints(float[] a) {
		//mTranslateMatrixInverse.mapPoints(a);
		//mScaleMatrixInverse.mapPoints(a);
		Matrix inverse = new Matrix();
		boolean result = mTransformationMatrix.invert(inverse);
		inverse.mapPoints(a);
		return a;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mOnTouchEventWorkingArray[0] = ev.getX();
		mOnTouchEventWorkingArray[1] = ev.getY();

		mOnTouchEventWorkingArray = scaledPointsToScreenPoints(mOnTouchEventWorkingArray);

		ev.setLocation(mOnTouchEventWorkingArray[0],
				mOnTouchEventWorkingArray[1]);
		mScaleDetector.onTouchEvent(ev);

		if (!mScaleDetector.isInProgress()){
			mGestureDetector.onTouchEvent(ev);
		}

		final int action = ev.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();

			mLastTouchX = x;
			mLastTouchY = y;

			// Save the ID of this pointer
			mActivePointerId = ev.getPointerId(0);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			// Find the index of the active pointer and fetch its position
			final int pointerIndex = ev.findPointerIndex(mActivePointerId);
			final float x = ev.getX(pointerIndex);
			final float y = ev.getY(pointerIndex);

			final float dx = x - mLastTouchX;
			final float dy = y - mLastTouchY;

			mTransformationMatrix.preTranslate(dx,  dy);
			
			
			/*mPosX += dx;
			mPosY += dy;
			
			mTranslateMatrix.preTranslate(dx, dy);
			mTranslateMatrix.invert(mTranslateMatrixInverse);*/

			mLastTouchX = x;
			mLastTouchY = y;

			invalidate();
			break;
		}

		case MotionEvent.ACTION_UP: {
			mActivePointerId = INVALID_POINTER_ID;

			break;
		}

		case MotionEvent.ACTION_CANCEL: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		case MotionEvent.ACTION_POINTER_UP: {
			// Extract the index of the pointer that left the touch sensor
			final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			final int pointerId = ev.getPointerId(pointerIndex);
			if (pointerId == mActivePointerId) {
				// This was our active p   ointer going up. Choose a new
				// active pointer and adjust accordingly.
				final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				mLastTouchX = ev.getX(newPointerIndex);
				mLastTouchY = ev.getY(newPointerIndex);
				mActivePointerId = ev.getPointerId(newPointerIndex);
			}
			break;
		}
		}
		return true;
	}

	public OnZoomableViewZoomListener getOnZoomListener() {
		return onZoomListener;
	}

	public void setOnZoomListener(OnZoomableViewZoomListener onZoomListener) {
		this.onZoomListener = onZoomListener;
	}
	
	public void setSingleTapListener(OnZoomableViewSingleTapListener singleTapListener){
		this.mSingleTapListener = singleTapListener;
	}

	public void setShouldTouchDelegate(ShouldTouchDelegate shouldTouchDelegate) {
		this.shouldTouchDelegate = shouldTouchDelegate;
	}

	public ShouldTouchDelegate getShouldTouchDelegate() {
		return shouldTouchDelegate;
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			//mScaleFactor *= detector.getScaleFactor();
			if (detector.isInProgress()) {
				mFocusX = detector.getFocusX();
				mFocusY = detector.getFocusY();
			}
			mTransformationMatrix.preScale(detector.getScaleFactor(), detector.getScaleFactor(), mFocusX, mFocusY);
			float[] matVals = new float[9];
			mTransformationMatrix.getValues(matVals);
			float scaleFactor = matVals[0];
			float newScaleFactor = Math.min(MAX_SCALE, Math.max(MIN_SCALE, scaleFactor));
			mTransformationMatrix.preScale(newScaleFactor/scaleFactor, newScaleFactor/scaleFactor, mFocusX, mFocusY);
			
			if (getOnZoomListener() != null){
				getOnZoomListener().onZoomToScaleFactor(newScaleFactor);
			}
			
			currentScaleFactor = newScaleFactor;
			
			//mTransformationMatrix.
			//mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 3.0f));
			//mScaleMatrix.setScale(mScaleFactor, mScaleFactor, mFocusX, mFocusY);
			//mScaleMatrix.invert(mScaleMatrixInverse);
			invalidate();
			requestLayout();

			return true;
		}
	}
	
	public class GestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (getShouldTouchDelegate() == null || !getShouldTouchDelegate().shouldTouch()) {
				return super.onSingleTapConfirmed(e);
			}

			if ( null != mSingleTapListener ) {
				mSingleTapListener.onSingleTapConfirmed();
			}

			return super.onSingleTapConfirmed( e );
		}

	    @Override
	    public boolean onDoubleTap(MotionEvent e) {
			if (getShouldTouchDelegate() == null || !getShouldTouchDelegate().shouldTouch()) {
				return super.onSingleTapConfirmed(e);
			}
			
			mFocusX = e.getX();
			mFocusY = e.getY();

			float doubleTapScaleFactor = currentScaleFactor;
			Log.d("factor", "current: " + currentScaleFactor);
			if (doubleTapScaleFactor >= MAX_SCALE) {
				doubleTapScaleFactor = MIN_SCALE;
			} else {
				doubleTapScaleFactor = MAX_SCALE;
			}
			
			float[] matVals = new float[9];
			mTransformationMatrix.getValues(matVals);
			float scaleFactor = matVals[0];
			float newScaleFactor = doubleTapScaleFactor;
			mTransformationMatrix.preScale(newScaleFactor/scaleFactor, newScaleFactor/scaleFactor, mFocusX, mFocusY);

			if (getOnZoomListener() != null){
				getOnZoomListener().onZoomToScaleFactor(newScaleFactor);
			}
			currentScaleFactor = newScaleFactor;
			
			invalidate();
			requestLayout();

	        return true;
	    }

	}

}