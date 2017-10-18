package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import byu.codemonkeys.tickettoride.R;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCardWidget extends View {
	
	private TextView textViewDestinationA;
	private TextView textViewDestinationB;
	private TextView textViewPointValue;
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
	}
	
	// region Constructors
	public DestinationCardWidget(Context context) {
		super(context);
		init(context);
	}
	
	public DestinationCardWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public DestinationCardWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
		
	}
	
	private void init(Context context) {
		//		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//		inflater.inflate(R.layout.destination_card, this, true);
		//		getViews();
		
		//		applyAttributes(context, attrs);
		
		
	}
	
	private void getViews() {
		this.textViewDestinationA = (TextView) findViewById(R.id.destinationCard_textViewDestinationA);
		this.textViewDestinationB = (TextView) findViewById(R.id.destinationCard_textViewDestinationB);
		this.textViewPointValue = (TextView) findViewById(R.id.destinationCard_textViewPointValue);
	}
	// endregion
	
	// region Public Properties
	// region DestinationA Property
	private String destinationA;
	
	public String getDestinationA() {
		return destinationA;
	}
	
	public void setDestinationA(String destinationA) {
		this.destinationA = destinationA;
		//		this.textViewDestinationA.setText(destinationA);
	}
	// endregion
	
	// region DestinationB Property
	private String destinationB;
	
	public String getDestinationB() {
		return destinationB;
	}
	
	public void setDestinationB(String destinationB) {
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
		final float destinationARatio = 0.145f;
		final float destinationBRatio = 0.9191f;
		final float pointsXRatio = 1.1f;
		final float pointsYRatio = 0.742f;
		final float fontSizeRatio = 0.1f;
		textPaint.setTextSize(fontSizeRatio * this.getHeight());
		Drawable d = getResources().getDrawable(R.drawable.destination_card);
		d.setBounds(canvas.getClipBounds());
		d.draw(canvas);
		canvas.drawText(this.destinationA, 10, destinationARatio * this.getHeight(), textPaint);
		canvas.drawText(this.destinationB, 10, destinationBRatio * this.getHeight(), textPaint);
		canvas.drawText(String.valueOf(this.pointValue),
						pointsXRatio * this.getHeight(),
						pointsYRatio * this.getHeight(),
						textPaint);
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
		
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
			height = (int) (width / drawableRatio);
		} else {
			height = heightSize;
			width = (int) (heightSize * drawableRatio);
		}
		
		int childWidthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		int childHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		//		measureChildren(childWidthSpec, childHeightSpec);
		setMeasuredDimension(width, height);
	}
}
