package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
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
	//region KeepOverContent Property
	private boolean keepOverContent;
	
	public boolean isKeepOverContent() {
		return keepOverContent;
	}
	
	public void setKeepOverContent(boolean keepOverContent) {
		this.keepOverContent = keepOverContent;
	}
	
	//endregion
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
		
		float offsetChangeX = -(float) (prevScale - zoomFactor) * imageCoords.x;
		float offsetChangeY = -(float) (prevScale - zoomFactor) * imageCoords.y;
		
		if (this.keepOverContent) {
			RectF bounds = getContentExtents();
			bounds.left *= this.zoomFactor;
			bounds.right *= this.zoomFactor;
			bounds.top *= this.zoomFactor;
			bounds.bottom *= this.zoomFactor;
			if (bounds.width() < this.getWidth() || bounds.height() < this.getHeight()) {
				bounds = getContentExtents();
				float zoomX = this.getWidth() / bounds.width();
				float zoomY = this.getHeight() / bounds.height();
				this.zoomFactor = Math.max(zoomX, zoomY);
				
				offsetChangeX = -(float) (prevScale - zoomFactor) * imageCoords.x;
				offsetChangeY = -(float) (prevScale - zoomFactor) * imageCoords.y;
			}
			
			PointF correctedOffset = getKeepOverContentOffset(offsetChangeX, offsetChangeY);
			offsetChangeX = correctedOffset.x;
			offsetChangeY = correctedOffset.y;
		}
		// Offset the image so that the current point on the image under the mouse doesn't move.
		this.offsetX += offsetChangeX;
		this.offsetY += offsetChangeY;
		
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
		float offsetChangeX = previousPoint.x - x;
		float offsetChangeY = previousPoint.y - y;
		
		if (this.keepOverContent) {
			PointF correctedOffset = getKeepOverContentOffset(offsetChangeX, offsetChangeY);
			offsetChangeX = correctedOffset.x;
			offsetChangeY = correctedOffset.y;
		}
		
		// Pans the viewport
		this.offsetX += offsetChangeX;
		this.offsetY += offsetChangeY;
		
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
	
	private PointF getKeepOverContentOffset(float offsetX, float offsetY) {
		// See how the bounds of the content if the offset were applied
		RectF bounds = getContentExtents();
		bounds.left *= this.zoomFactor;
		bounds.right *= this.zoomFactor;
		bounds.top *= this.zoomFactor;
		bounds.bottom *= this.zoomFactor;
		
		bounds.left -= this.offsetX;
		bounds.right -= this.offsetX;
		bounds.top -= this.offsetY;
		bounds.bottom -= this.offsetY;
		
		RectF offsetBounds = new RectF(bounds.left, bounds.top, bounds.right, bounds.bottom);
		offsetBounds.left -= offsetX;
		offsetBounds.right -= offsetX;
		offsetBounds.top -= offsetY;
		offsetBounds.bottom -= offsetY;
		
		
		boolean contentCoversLeft = offsetBounds.left <= 0;
		boolean contentCoversTop = offsetBounds.top <= 0;
		boolean contentCoversRight = offsetBounds.right >= this.getWidth();
		boolean contentCoversBottom = offsetBounds.bottom >= this.getHeight();
		if (!contentCoversLeft)
			offsetX = bounds.left;
		if (!contentCoversRight)
			offsetX = bounds.right - this.getWidth();
		if (!contentCoversTop)
			offsetY = bounds.top;
		if (!contentCoversBottom)
			offsetY = bounds.bottom - this.getHeight();
		
		return new PointF(offsetX, offsetY);
	}
	
	private RectF getContentExtents() {
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
		
		return new RectF(minX, minY, maxX, maxY);
	}
	
	public void fillViewport() {
		RectF contentExtents = getContentExtents();
		float zoomX = this.getWidth() / contentExtents.width();
		float zoomY = this.getHeight() / contentExtents.height();
		this.zoomFactor = Math.max(zoomX, zoomY);
		
		contentExtents.set(contentExtents.left * this.zoomFactor,
						   contentExtents.top * this.zoomFactor,
						   contentExtents.right * this.zoomFactor,
						   contentExtents.bottom * this.zoomFactor);
		
		this.offsetX = -(this.getWidth() - contentExtents.width()) / 2.0f;
		this.offsetY = -(this.getHeight() - contentExtents.height()) / 2.0f;
		this.updateTransformationMatrix();
		this.invalidate();
	}
}
