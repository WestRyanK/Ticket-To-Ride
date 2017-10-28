package byu.codemonkeys.tickettoride.views.widgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ryan on 10/21/2017.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
	private final int spacing;
	
	public VerticalSpaceItemDecoration(int spacing) {
		this.spacing = spacing;
	}
	
	@Override
	public void getItemOffsets(Rect outRect,
							   View view,
							   RecyclerView parent,
							   RecyclerView.State state) {
		outRect.top = spacing;
		outRect.bottom = spacing;
	}
}
