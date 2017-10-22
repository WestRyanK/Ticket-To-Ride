package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.views.game.CitiesData;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCardWidget extends View {
	
	private static Typeface levibrush;
	private static Paint textPaint;
	
	{
		levibrush = Typeface.createFromAsset(getContext().getAssets(),
											 //				, "levibrush.ttf");
											 "fonts/levibrush.ttf");
		textPaint = new Paint();
		textPaint.setColor(getResources().getColor(R.color.colorPrimary));
		textPaint.setTypeface(levibrush);
		textPaint.setTextSize(100);
		textPaint.setTextAlign(Paint.Align.CENTER);
	}
	
	// region Constructors
	public DestinationCardWidget(Context context) {
		super(context);
	}
	
	public DestinationCardWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	// region Public Properties
	// region DestinationA Property
	private int destinationA;
	
	public int getDestinationA() {
		return destinationA;
	}
	
	public void setDestinationA(int destinationA) {
		this.destinationA = destinationA;
		//		this.textViewDestinationA.setText(destinationA);
	}
	// endregion
	
	// region DestinationB Property
	private int destinationB;
	
	public int getDestinationB() {
		return destinationB;
	}
	
	public void setDestinationB(int destinationB) {
		this.destinationB = destinationB;
		//		this.textViewDestinationB.setText(destinationB);
	}
	// endregion
	
	// region Point Value Property
	private int pointValue;
	
	public int getPointValue() {
		return pointValue;
	}
	
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
		//		this.textViewPointValue.setText(String.valueOf(pointValue));
	}
	// endregion
	
	// endregion
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final float destinationARatio = 0.14f;
		final float destinationBRatio = 0.915f;
		final float pointsXRatio = 1.154f;
		final float pointsYRatio = 0.735f;
		final float fontSizeRatio = 0.085f;
		textPaint.setTextSize(fontSizeRatio * this.getHeight());
		Drawable d = getResources().getDrawable(R.drawable.destination_card);
		d.setBounds(canvas.getClipBounds());
		d.draw(canvas);
		CitiesData.CityData destinationA = CitiesData.getInstance().getCity(this.destinationA);
		CitiesData.CityData destinationB = CitiesData.getInstance().getCity(this.destinationB);
		canvas.drawText(destinationA.getCityName(),
						this.getWidth() / 2,
						destinationARatio * this.getHeight(),
						textPaint);
		canvas.drawText(destinationB.getCityName(),
						this.getWidth() / 2,
						destinationBRatio * this.getHeight(),
						textPaint);
		canvas.drawText(String.valueOf(this.pointValue),
						pointsXRatio * this.getHeight(),
						pointsYRatio * this.getHeight(),
						textPaint);
		
		drawMarker(canvas,
				   destinationA.getCityDestinationCardXRatio(),
				   destinationA.getCityDestinationCardYRatio());
		drawMarker(canvas,
				   destinationB.getCityDestinationCardXRatio(),
				   destinationB.getCityDestinationCardYRatio());
		
	}
	
	private void drawMarker(Canvas canvas, float ratioX, float ratioY) {
		final float markerRatio = 0.03f;
		float markerRadius = markerRatio * getHeight();
		float x = ratioX * getWidth();
		float y = ratioY * getHeight();
		Drawable m = getResources().getDrawable(R.drawable.destination_marker);
		m.setBounds((int) (x - markerRadius),
					(int) (y - markerRadius),
					(int) (x + markerRadius),
					(int) (y + markerRadius));
		m.draw(canvas);
		
	}
	
	private float DPsToPixels(float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
										 dp,
										 getResources().getDisplayMetrics());
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		Drawable d = getResources().getDrawable(R.drawable.destination_card);
		if (d == null)
			return;
		
		float drawableRatio = d.getIntrinsicWidth() / (float) d.getIntrinsicHeight();
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width, height;
		
		if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
			width = widthSize;
			height = (int) (width / drawableRatio);
		} else {
			height = heightSize;
			width = (int) (heightSize * drawableRatio);
		}
		
		int childWidthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		int childHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		setMeasuredDimension(width, height);
	}
}
