package byu.codemonkeys.tickettoride.views.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Ryan on 10/16/2017.
 */

public class FontifiedButton extends android.support.v7.widget.AppCompatButton {
	public FontifiedButton(Context context) {
		super(context);
		setFont();
	}
	
	public FontifiedButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont();
	}
	
	public FontifiedButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont();
	}
	
	private void setFont() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(),
				//				, "levibrush.ttf");
												 "fonts/levibrush.ttf");
		setTypeface(font, Typeface.NORMAL);
	}
}