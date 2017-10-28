package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.content.res.TypedArray;
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

public class TrainCardWidget extends RelativeLayout implements OnClickListener, OnFocusChangeListener, OnLongClickListener {
	
	private static final String TAG = "TRAIN_CARD";
	private TextView textViewCount;
	private RelativeLayout layoutBackground;
	private ImageView imageViewCard;
	
	// region Public Properties
	
	// region Count Property
	private int count;
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
		this.textViewCount.setText(String.valueOf(count));
	}
	// endregion
	
	// region ShowCount Property
	private boolean showCount;
	
	public boolean isShowCount() {
		return showCount;
	}
	
	public void setShowCount(boolean showCount) {
		this.showCount = showCount;
		if (this.isShowCount()) {
			this.textViewCount.setVisibility(View.VISIBLE);
		} else {
			this.textViewCount.setVisibility(View.INVISIBLE);
		}
	}
	// endregion
	
	// region CardDrawable Property
	private Drawable cardDrawable;
	
	public Drawable getCardDrawable() {
		return cardDrawable;
	}
	
	public void setCardDrawable(Drawable cardDrawable) {
		this.cardDrawable = cardDrawable;
		this.imageViewCard.setImageDrawable(cardDrawable);
		//		this.imageViewCard.setBackground(cardDrawable);
		//						this.layoutBackground.setBackground(cardDrawable);
	}
	// endregion
	
	// endregion
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		Drawable d = getCardDrawable();
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
		measureChildren(childWidthSpec, childHeightSpec);
		setMeasuredDimension(width, height);
	}
	
	public TrainCardWidget(Context context) {
		this(context, null);
	}
	
	public TrainCardWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	@SuppressWarnings({"UnusedDeclaration"})
	public TrainCardWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.train_card, this, true);
		getViews();
		
		applyAttributes(context, attrs);
	}
	
	private void getViews() {
		textViewCount = (TextView) findViewById(R.id.trainCard_textViewCount);
		layoutBackground = (RelativeLayout) findViewById(R.id.trainCard_background);
		imageViewCard = (ImageView) findViewById(R.id.trainCard_imageViewCard);
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
	
	public void onClick(View v) {
	}
	
	public void onFocusChange(View v, boolean hasFocus) {
		
	}
	
	public boolean onLongClick(View v) {
		return true;
	}
}
