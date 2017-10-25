package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.icu.util.Currency;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Ryan on 10/23/2017.
 */

public class Viewport extends AbsoluteLayout {
	//region Public Properties
	// region ZoomFactor Property
	private float zoomFactor;
	
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
	private PointF prev = new PointF();
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
		if (event.getPointerCount() <= 1) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					PanBegin(event.getX(), event.getY());
					break;
				case MotionEvent.ACTION_MOVE:
					Pan(event.getX(), event.getY());
					break;
				case MotionEvent.ACTION_UP:
					PanEnd();
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
	
	// region Utilities
	public PointF ImagePointFromViewport(PointF viewportCoords) {
		
		PointF imgCoords = new PointF();
		imgCoords.x = (int) ((viewportCoords.x + this.getOffsetX()) / zoomFactor);
		imgCoords.y = (int) ((viewportCoords.y + this.getOffsetY()) / zoomFactor);
		
		return imgCoords;
	}
	
	public PointF ViewportPointFromImage(PointF imageCoords) {
		PointF viewportCoords = new PointF();
		
		viewportCoords.x = (imageCoords.x * zoomFactor - this.getOffsetX());
		viewportCoords.y = (imageCoords.y * zoomFactor - this.getOffsetY());
		
		return viewportCoords;
	}
	// endregion
	
	// region Panning
	private void Pan(float x, float y) {
		// Pans the viewport
		this.offsetX += prev.x - x;
		this.offsetY += prev.y - y;
		
		prev = new PointF(x, y);
		
		updateTransformationMatrix();
		// Draw the viewport (the paint function makes sure the image is within the viewport)
		this.invalidate();
	}
	
	private void PanEnd() {
		//		if (this.cursorOriginal != null)
		//			this.Cursor = this.cursorOriginal;
	}
	
	private void PanBegin(float x, float y) {
		//		this.cursorOriginal = this.Cursor;
		//		this.Cursor = Cursors.SizeAll;
		
		prev = new PointF(x, y);
	}
	// endregion
}
