package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import byu.codemonkeys.tickettoride.R;

public class TrainCardWidget extends View implements OnClickListener, OnFocusChangeListener, OnLongClickListener {
	
	private static final String TAG = "TRAIN_CARD";
	private Rect clipBounds = new Rect();
	
	private static Typeface levibrush;
	private static Paint textFillPaint;
	private static Paint textStrokePaint;
	
	{
		levibrush = Typeface.createFromAsset(getContext().getAssets(),
											 //				, "levibrush.ttf");
											 "fonts/levibrush.ttf");
		textFillPaint = new Paint();
		textFillPaint.setColor(Color.parseColor("#FFFFFF"));
		textFillPaint.setTypeface(levibrush);
		textFillPaint.setTextSize(100);
		textFillPaint.setTextAlign(Paint.Align.RIGHT);
		textFillPaint.setAntiAlias(true);
		
		textStrokePaint = new Paint();
		textStrokePaint.setColor(Color.parseColor("#FFFFFF"));
		textStrokePaint.setTypeface(levibrush);
		textStrokePaint.setTextSize(100);
		textStrokePaint.setTextAlign(Paint.Align.RIGHT);
		textStrokePaint.setAntiAlias(true);
		textStrokePaint.setStyle(Paint.Style.STROKE);
		textStrokePaint.setStrokeWidth(3);
		textStrokePaint.setColor(Color.BLACK);
	}
	
	// region Public Properties
	
	// region Count Property
	private int count;
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
		this.invalidate();
	}
	// endregion
	
	// region ShowCount Property
	private boolean showCount;
	
	public boolean isShowCount() {
		return showCount;
	}
	
	public void setShowCount(boolean showCount) {
		this.showCount = showCount;
		this.invalidate();
	}
	// endregion
	
	// region CardDrawable Property
	private Drawable cardDrawable;
	
	public Drawable getCardDrawable() {
		return cardDrawable;
	}
	
	public void setCardDrawable(Drawable cardDrawable) {
		this.cardDrawable = cardDrawable;
		this.invalidate();
	}
	// endregion
	// endregion
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final float countXRatio = 1.25f;
		final float countYRatio = 0.85f;
		final float fontSizeRatio = 0.5f;
		textFillPaint.setTextSize(fontSizeRatio * this.getHeight());
		textStrokePaint.setTextSize(fontSizeRatio * this.getHeight());
		
		if (this.cardDrawable != null) {
			canvas.getClipBounds(clipBounds);
			this.cardDrawable.setBounds(clipBounds);
			this.cardDrawable.draw(canvas);
		}
		
		if (this.showCount) {
			canvas.drawText(String.valueOf(this.count),
							countXRatio * this.getHeight(),
							countYRatio * this.getHeight(),
							textStrokePaint);
			canvas.drawText(String.valueOf(this.count),
							countXRatio * this.getHeight(),
							countYRatio * this.getHeight(),
							textFillPaint);
		}
		
		if (this.isSelected()) {
			Drawable selectedCircle = getResources().getDrawable(R.drawable.ic_selected_circle);
			selectedCircle.setBounds(canvas.getClipBounds());
			selectedCircle.draw(canvas);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		Drawable d = getCardDrawable();
		if (d == null)
			d = getResources().getDrawable(R.drawable.card_black);
		
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
		setMeasuredDimension(width, height);
	}
	
	// region Constructors
	public TrainCardWidget(Context context) {
		this(context, null);
	}
	
	public TrainCardWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	@SuppressWarnings({"UnusedDeclaration"})
	public TrainCardWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		applyAttributes(context, attrs);
	}
	
	private void applyAttributes(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme()
							  .obtainStyledAttributes(attrs, R.styleable.TrainCardWidget, 0, 0);
		
		try {
			setShowCount(a.getBoolean(R.styleable.TrainCardWidget_showCount, false));
			setCount(a.getInteger(R.styleable.TrainCardWidget_count, 0));
			setCardDrawable(a.getDrawable(R.styleable.TrainCardWidget_cardDrawable));
		} finally {
			a.recycle();
		}
	}
	// endregion
	
	public void onClick(View v) {
	}
	
	public void onFocusChange(View v, boolean hasFocus) {
		
	}
	
	public boolean onLongClick(View v) {
		return true;
	}
}
