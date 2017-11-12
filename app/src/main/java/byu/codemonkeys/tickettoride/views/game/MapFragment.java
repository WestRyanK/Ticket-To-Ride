package byu.codemonkeys.tickettoride.views.game;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.Map;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.MapContract;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.views.viewdata.PointBubblesData;
import byu.codemonkeys.tickettoride.views.widgets.MapEdgeWidget;
import byu.codemonkeys.tickettoride.views.widgets.Viewport;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements MapContract.View {
	
	private Viewport viewport;
	private MapEdgeWidget pointBubble;
	private ImageView imageViewMap;
	private MapContract.Presenter presenter;
	
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
		setListenFirstMeasure();
		return view;
	}
	
	private void setListenFirstMeasure() {
		viewport.getViewTreeObserver()
				.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			
					@Override
					public void onGlobalLayout() {
				
						// Removing layout listener to avoid multiple calls
						if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
							viewport.getViewTreeObserver().removeGlobalOnLayoutListener(this);
						} else {
							viewport.getViewTreeObserver().removeOnGlobalLayoutListener(this);
						}
				
						viewport.fillViewport();
					}
				});
	}
	
	private void setupMap() {
		Map<Integer, PointBubblesData.PointBubbleData> bubbleData = PointBubblesData.getInstance()
																					.getAllBubbles();
		int bubbleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
														 20,
														 getResources().getDisplayMetrics());
		Drawable mapDrawable = getResources().getDrawable(R.drawable.avatarmap);
		for (PointBubblesData.PointBubbleData bubble : bubbleData.values()) {
			final MapEdgeWidget pointBubble = new MapEdgeWidget(getContext());
			pointBubble.setPoints(bubble.getPointValue());
			int x = (int) (bubble.getMapXRatio() * mapDrawable.getIntrinsicWidth() -
					bubbleSize / 2.0f);
			int y = (int) (bubble.getMapYRatio() * mapDrawable.getIntrinsicHeight() -
					bubbleSize / 2.0f);
			pointBubble.setLayoutParams(new Viewport.LayoutParams(bubbleSize, bubbleSize, x, y));
			pointBubble.setClaimedColor(PlayerColor.None);
			this.viewport.addView(pointBubble);
			
			pointBubble.setClickable(true);
			pointBubble.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					presenter.claimRoute(pointBubble);
				}
			});
		}
	}
	
	public void setPresenter(MapContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
	private void setupViewport() {
		this.viewport.setMaxZoom(2.0f);
		this.viewport.setMinZoom(0.25f);
		this.viewport.setZoomFactor(0.35f);
		this.viewport.setKeepOverContent(true);
		
	}
	
	private void getViews(View view) {
		this.viewport = (Viewport) view.findViewById(R.id.map_viewport);
		this.imageViewMap = (ImageView) view.findViewById(R.id.map_imageViewMap);
	}
	
}
