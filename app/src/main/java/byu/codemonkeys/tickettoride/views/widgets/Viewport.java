package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;

/**
 * Created by Ryan on 10/23/2017.
 */

public class Viewport extends AbsoluteLayout {
	//region Public Properties
	// region ZoomFactor Property
	private float zoomFactor;
	private int previousPointerID;
	
	public float getZoomFactor() {
		return zoomFactor;
	}
	
	public void setZoomFactor(float zoomFactor) {
		this.ZoomToPoint(zoomFactor - this.zoomFactor,
						 new PointF(this.getWidth() / 2.0f, this.getHeight() / 2.0f));
	}
	// endregion
	
	// region MaxZoom Property
	private float maxZoom;
	
	public float getMaxZoom() {
		return this.maxZoom;
	}
	
	public void setMaxZoom(float maxZoom) {
		this.maxZoom = maxZoom;
		if (this.maxZoom < this.minZoom)
			throw new IllegalArgumentException("MaxZoom must be greater than MinZoom");
	}
	// endregion
	
	// region MinZoom Property
	private float minZoom;
	
	public float getMinZoom() {
		return this.minZoom;
	}
	
	public void setMinZoom(float minZoom) {
		this.minZoom = minZoom;
		if (this.minZoom > this.maxZoom)
			throw new IllegalArgumentException("MinZoom must be less than MaxZoom");
	}
	// endregion
	
	// region OffsetX Property
	private float offsetX;
	
	public float getOffsetX() {
		return offsetX;
	}
	
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	// endregion
	
	// region OffsetY Property
	private float offsetY;
	
	public float getOffsetY() {
		return offsetY;
	}
	
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	// endregion
	// endregion
	
	private static final float ZOOM_INCREMENT = 0.1f;
	private PointF previousPoint = new PointF();
	private float previousPinchDistance;
	private Matrix transformMatrix;
	
	// region Constructors
	public Viewport(Context context) {
		super(context);
		init(context);
	}
	
	public Viewport(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public Viewport(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		setWillNotDraw(false);
		this.maxZoom = 10.0f;
		this.minZoom = 0.1f;
		this.zoomFactor = 1.0f;
		this.offsetX = 0;
		this.offsetY = 0;
		this.transformMatrix = new Matrix();
	}
	// endregion
	
	// region UI Event Handling
	
	private void handleZoom(MotionEvent event) {
		if (event.getPointerCount() == 2) {
			MotionEvent.PointerCoords a = new MotionEvent.PointerCoords();
			event.getPointerCoords(0, a);
			MotionEvent.PointerCoords b = new MotionEvent.PointerCoords();
			event.getPointerCoords(1, b);
			
			float currentPinchDistance = (float) Math.sqrt(Math.pow(a.getAxisValue(0) -
																			b.getAxisValue(0), 2) +
																   Math.pow(a.getAxisValue(1) -
																					b.getAxisValue(1),
																			2));
			if (previousPinchDistance == 0)
				previousPinchDistance = currentPinchDistance;
			PointF midPoint = new PointF((a.getAxisValue(0) + b.getAxisValue(0)) / 2.0f,
										 (a.getAxisValue(1) + b.getAxisValue(1)) / 2.0f);
			
			float zoomIncrement = ((currentPinchDistance / previousPinchDistance) - 1) * zoomFactor;
			ZoomToPoint(zoomIncrement, midPoint);
			previousPinchDistance = currentPinchDistance;
		} else if (event.getPointerCount() <= 1) {
			previousPinchDistance = 0;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		event.transform(this.transformMatrix);
		handleZoom(event);
		handlePan(event);
		this.invalidate();
		return true;
	}
	
	private void handlePan(MotionEvent event) {
		if (event.getPointerCount() > 1) {
			MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
			if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
				//				if (this.previousPointerID != event.getPointerId(0))
				if (event.getPointerId(event.getActionIndex()) == this.previousPointerID) {
					event.getPointerCoords(1 - event.getActionIndex(), coords);
					updatePreviousPointer(coords.getAxisValue(0),
										  coords.getAxisValue(1),
										  event.getPointerId(1 - event.getActionIndex()));
				}
			}
			event.getPointerCoords(event.findPointerIndex(this.previousPointerID), coords);
			updatePreviousPointer(coords.getAxisValue(0),
								  coords.getAxisValue(1),
								  this.previousPointerID);
		}
		if (event.getPointerCount() <= 1) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_POINTER_UP:
					Log.v("VP", "ACTION_POINTER_UP");
					break;
				case MotionEvent.ACTION_UP:
					//					previousPoint = new PointF(event.getX(), event.getY());
					Log.v("VP", "ACTION_UP");
					break;
				case MotionEvent.ACTION_DOWN:
					updatePreviousPointer(event.getX(), event.getY(), event.getPointerId(0));
					Log.v("VP", "ACTION_DOWN");
					break;
				case MotionEvent.ACTION_MOVE:
					pan(event.getX(), event.getY(), event.getPointerId(0));
					Log.v("VP", "ACTION_MOVE");
					break;
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		updateTransformationMatrix();
		canvas.setMatrix(transformMatrix);
		super.onDraw(canvas);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		ev.transform(getInverse());
		boolean result = super.dispatchTouchEvent(ev);
		invalidate();
		return result;
	}
	// endregion
	
	// region Zooming
	public void ZoomInProportional() {
		double increment = this.zoomFactor * ZOOM_INCREMENT;
		this.ZoomToPoint(increment, new PointF(this.getWidth() / 2.0f, this.getHeight() / 2.0f));
	}
	
	public void ZoomOutProportional() {
		double increment = -this.zoomFactor * ZOOM_INCREMENT;
		this.ZoomToPoint(increment, new PointF(this.getWidth() / 2.0f, this.getHeight() / 2.0f));
	}
	
	private void ZoomToPoint(double increment, PointF viewportCoords) {
		PointF imageCoords = ImagePointFromViewport(viewportCoords);
		// save the previous scale in order to calculate the difference between previous and new
		double prevScale = zoomFactor;
		
		zoomFactor += increment;
		
		// Make sure we don't pass max or min zoom
		if (this.zoomFactor > maxZoom)
			zoomFactor = maxZoom;
		if (this.zoomFactor < minZoom)
			zoomFactor = minZoom;
		
		// Offset the image so that the current point on the image under the mouse doesn't move.
		this.offsetX -= (prevScale - zoomFactor) * imageCoords.x;
		this.offsetY -= (prevScale - zoomFactor) * imageCoords.y;
		
		// Draw the viewport (the paint function makes sure the image is within the viewport)
		updateTransformationMatrix();
		this.invalidate();
	}
	
	// endregion
	
	// region Utilities
	public PointF ImagePointFromViewport(PointF viewportCoords) {
		
		PointF imgCoords = new PointF();
		imgCoords.x = (int) ((viewportCoords.x + this.getOffsetX()) / zoomFactor);
		imgCoords.y = (int) ((viewportCoords.y + this.getOffsetY()) / zoomFactor);
		
		return imgCoords;
	}
	
	private Matrix getInverse() {
		Matrix inverse = new Matrix();
		this.transformMatrix.invert(inverse);
		return inverse;
	}
	
	private void updateTransformationMatrix() {
		transformMatrix.setTranslate(-offsetX, -offsetY);
		transformMatrix.preScale(this.zoomFactor, this.zoomFactor);
	}
	// endregion
	
	// region Panning
	private void pan(float x, float y, int pointerId) {
		//		if (this.previousPointerID != pointerId)
		//			updatePreviousPointer(x, y, pointerId);
		// Pans the viewport
		this.offsetX += previousPoint.x - x;
		this.offsetY += previousPoint.y - y;
		
		previousPoint = new PointF(x, y);
		
		updateTransformationMatrix();
		// Draw the viewport (the paint function makes sure the image is within the viewport)
		this.invalidate();
	}
	
	private void updatePreviousPointer(float x, float y, int pointerID) {
		this.previousPointerID = pointerID;
		this.previousPoint = new PointF(x, y);
	}
	// endregion
	
	public void fillViewport() {
		int childCount = this.getChildCount();
		float minX = Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;
		float maxY = Float.MIN_VALUE;
		
		for (int i = 0; i < childCount; i++) {
			View v = this.getChildAt(i);
			if (v.getX() < minX)
				minX = v.getX();
			if (v.getY() < minY)
				minY = v.getY();
			if (v.getX() + v.getWidth() > maxX)
				maxX = v.getX() + v.getWidth();
			if (v.getY() + v.getHeight() > maxY)
				maxY = v.getY() + v.getHeight();
		}
		float width = maxX - minX;
		float height = maxY - minY;
		float zoomX = this.getWidth() / width;
		float zoomY = this.getHeight() / height;
		this.zoomFactor = Math.max(zoomX, zoomY);
		
		maxX *= this.zoomFactor;
		maxY *= this.zoomFactor;
		minX *= this.zoomFactor;
		minY *= this.zoomFactor;
		
		width = maxX - minX;
		height = maxY - minY;
		
		this.offsetX = -(this.getWidth() - width) / 2.0f;
		this.offsetY = -(this.getHeight() - height) / 2.0f;
		this.updateTransformationMatrix();
		this.invalidate();
	}
}
