package byu.codemonkeys.tickettoride.views.game;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Map;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.models.PlayerColorEnum;
import byu.codemonkeys.tickettoride.views.viewdata.PointBubblesData;
import byu.codemonkeys.tickettoride.views.widgets.MapPointBubble;
import byu.codemonkeys.tickettoride.views.widgets.Viewport;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
	
	private Viewport viewport;
	private MapPointBubble pointBubble;
	private ImageView imageViewMap;
	
	public MapFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		getViews(view);
		setupViewport();
		setupMap();
		return view;
	}
	
	private void setupMap() {
		Map<Integer, PointBubblesData.PointBubbleData> bubbleData = PointBubblesData.getInstance()
																					.getAllBubbles();
		int bubbleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
														 20,
														 getResources().getDisplayMetrics());
		Drawable mapDrawable = getResources().getDrawable(R.drawable.avatarmap);
		for (PointBubblesData.PointBubbleData bubble : bubbleData.values()) {
			final MapPointBubble pointBubble = new MapPointBubble(getContext());
			pointBubble.setPoints(bubble.getPointValue());
			int x = (int) (bubble.getMapXRatio() * mapDrawable.getIntrinsicWidth() -
					bubbleSize / 2.0f);
			int y = (int) (bubble.getMapYRatio() * mapDrawable.getIntrinsicHeight() -
					bubbleSize / 2.0f);
			pointBubble.setLayoutParams(new Viewport.LayoutParams(bubbleSize, bubbleSize, x, y));
			pointBubble.setClaimedColor(PlayerColorEnum.None);
			this.viewport.addView(pointBubble);
			
			pointBubble.setClickable(true);
			pointBubble.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					pointBubble.setClaimedColor(PlayerColorEnum.Red);
					
				}
			});
		}
	}
	
	private void setupViewport() {
		this.viewport.setMaxZoom(1.0f);
		this.viewport.setMinZoom(0.35f);
		this.viewport.setZoomFactor(0.35f);
		
	}
	
	private void getViews(View view) {
		this.viewport = (Viewport) view.findViewById(R.id.map_viewport);
		this.imageViewMap = (ImageView) view.findViewById(R.id.map_imageViewMap);
	}
	
}
