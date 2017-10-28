package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by Ryan on 10/17/2017.
 */

public class ViewportOLD extends android.support.v7.widget.AppCompatImageView {
	
	ScaleGestureDetector scaleGestureDetector;
	private int userInteractionMode;
	private static final int MODE_NONE = 0;
	private static final int MODE_ZOOM = 1;
	private static final int MODE_PAN = 2;
	private float startX;
	private float startY;
	private float previousX;
	private float previousY;
	private float lastGestureX;
	private float lastGestureY;
	private Paint myPaint;
	
	// region Public Properties
	// region TranslateX
	private float translateX;
	
	public float getTranslateX() {
		return translateX;
	}
	
	public void setTranslateX(float translateX) {
		this.translateX = translateX;
	}
	// endregion
	
	// region TranslateY
	private float translateY;
	
	public float getTranslateY() {
		return translateY;
	}
	
	public void setTranslateY(float translateY) {
		this.translateY = translateY;
	}
	// endregion
	
	// region ScaleFactor Property
	private float scaleFactor;
	
	public float getScaleFactor() {
		return scaleFactor;
	}
	
	public void setScaleFactor(float scaleFactor) {
		this.scaleFactor = Math.min(Math.max(scaleFactor, minScale), maxScale);
	}
	// endregion
	
	// region MinScale Property
	private float minScale;
	
	public float getMinScale() {
		return minScale;
	}
	
	public void setMinScale(float minScale) {
		this.minScale = minScale;
		if (minScale <= 0)
			minScale = 0.01f;
		if (maxScale < minScale)
			maxScale = minScale;
		setScaleFactor(scaleFactor);
	}
	// endregion
	
	// MaxScale Property
	private float maxScale;
	
	public float getMaxScale() {
		return maxScale;
	}
	
	public void setMaxScale(float maxScale) {
		this.maxScale = maxScale;
		setMinScale(minScale);
	}
	
	// endregion
	
	// region Constructors
	public ViewportOLD(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public ViewportOLD(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	public ViewportOLD(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		myPaint = new Paint();
		myPaint.setColor(Color.RED);
		myPaint.setStrokeWidth(20);
		scaleFactor = 1;
		minScale = 0.01f;
		maxScale = 100f;
		scaleGestureDetector = new ScaleGestureDetector(getContext(),
														new ScaleGestureDetector.SimpleOnScaleGestureListener() {
															@Override
															public boolean onScale(
																	ScaleGestureDetector scaleGestureDetector) {
																setScaleFactor(scaleFactor *
																					   scaleGestureDetector
																							   .getScaleFactor());
																Log.v("VP",
																	  String.valueOf(scaleFactor));
																return true;
															}
														});
	}
	// endregion
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				userInteractionMode = MODE_PAN;
				startX = event.getX() - previousX;
				startY = event.getY() - previousY;
				break;
			case MotionEvent.ACTION_MOVE:
				translateX = event.getX() - startX;
				translateY = event.getY() - startY;
				if (scaleGestureDetector.isInProgress()) {
					lastGestureX = scaleGestureDetector.getFocusX();
					lastGestureY = scaleGestureDetector.getFocusY();
				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				userInteractionMode = MODE_ZOOM;
				break;
			case MotionEvent.ACTION_UP:
				userInteractionMode = MODE_NONE;
				previousX = translateX;
				previousY = translateY;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				userInteractionMode = MODE_PAN;
				previousX = translateX;
				previousY = translateY;
				break;
		}
		scaleGestureDetector.onTouchEvent(event);
		invalidate();
		//		Log.v("VP", String.valueOf(translateX) + " " + String.valueOf(translateY));
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		if (scaleGestureDetector.isInProgress()) {
			canvas.scale(scaleFactor,
						 scaleFactor,
						 this.scaleGestureDetector.getFocusX(),
						 this.scaleGestureDetector.getFocusY());
		} else {
			
			canvas.scale(scaleFactor, scaleFactor, lastGestureX, lastGestureY);
		}
		canvas.translate(translateX / scaleFactor, translateY / scaleFactor);
		canvas.drawLine(20, 20, 100, 100, myPaint);
		canvas.drawLine(20, 100, 100, 20, myPaint);
		
		super.onDraw(canvas);
		
		canvas.restore();
	}
	
}
