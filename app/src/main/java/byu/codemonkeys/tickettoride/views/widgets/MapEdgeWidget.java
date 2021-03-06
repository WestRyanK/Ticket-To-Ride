package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.views.viewdata.PlayerColorData;

/**
 * Created by Ryan on 10/23/2017.
 */

public class MapEdgeWidget extends View {
	
	private static Typeface levibrush;
	private static Paint textPaint;
	private Paint circleFillPaint;
	private static Paint circleStrokePaint;
	
	{
		levibrush = Typeface.createFromAsset(getContext().getAssets(),
											 //				, "levibrush.ttf");
											 "fonts/levibrush.ttf");
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTypeface(levibrush);
		textPaint.setTextSize(100);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setAntiAlias(true);
		
		circleStrokePaint = new Paint();
		circleStrokePaint.setStyle(Paint.Style.STROKE);
		circleStrokePaint.setColor(Color.BLACK);
		circleStrokePaint.setStrokeWidth(2.0f);
		circleStrokePaint.setAntiAlias(true);
	}
	
	// region Public Properties
	// region Points Property
	private int lengthValue;
	
	public int getLengthValue() {
		return lengthValue;
	}
	
	public void setLengthValue(int lengthValue) {
		this.lengthValue = lengthValue;
		this.invalidate();
	}
	// endregion
	
	// region ClaimedColor Property
	private PlayerColor claimedColor;
	
	public PlayerColor getClaimedColor() {
		return claimedColor;
	}
	
	public void setClaimedColor(PlayerColor claimedColor) {
		this.claimedColor = claimedColor;
		this.invalidate();
	}
	// endregion
	// endregion
	
	// region Constructors
	public MapEdgeWidget(Context context) {
		super(context);
		init(context);
	}
	
	public MapEdgeWidget(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public MapEdgeWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		this.setLengthValue(1);
		this.setClaimedColor(PlayerColor.None);
		this.circleFillPaint = new Paint();
		this.circleFillPaint.setStyle(Paint.Style.FILL);
		this.circleFillPaint.setAntiAlias(true);
	}
	// endregion
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final float fontSizeRatio = 0.75f;
		this.circleFillPaint.setColor(PlayerColorData.getInstance().getColor(this.claimedColor));
		canvas.drawCircle(this.getWidth() / 2.0f,
						  this.getHeight() / 2.0f,
						  this.getWidth() / 2.0f,
						  circleFillPaint);
		canvas.drawCircle(this.getWidth() / 2.0f,
						  this.getHeight() / 2.0f,
						  this.getWidth() / 2.0f - 2,
						  circleStrokePaint);
		
		textPaint.setTextSize(fontSizeRatio * this.getHeight());
		String pointsText = String.valueOf(this.lengthValue);
		canvas.drawText(pointsText, this.getWidth() / 2.0f, (this.getHeight()) * 0.7f, textPaint);
	}
}
