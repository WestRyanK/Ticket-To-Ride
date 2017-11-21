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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.MapContract;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.map.GameMapLoader;
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
	private Map<Integer, MapEdgeWidget> mapEdgeWidgets;
	private static final String VIEWPORT_ARG_ZOOM = "VIEWPORT_ARG_ZOOM";
	private static final String VIEWPORT_ARG_OFFSETX = "VIEWPORT_ARG_OFFSETX";
	private static final String VIEWPORT_ARG_OFFSETY = "VIEWPORT_ARG_OFFSETY";
	
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
//		Map<Integer, MapEdgesData.MapEdgeData> bubbleData = MapEdgesData.getInstance()
//																		.getAllBubbles();
		this.mapEdgeWidgets = new HashMap<>();
		int circleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
														 20,
														 getResources().getDisplayMetrics());
		Drawable mapDrawable = getResources().getDrawable(R.drawable.avatarmap);
		
		for (GameMapLoader.RouteData routeData : GameMapLoader.getInstance().loadRoutesPositionsFromResource()) {
			final MapEdgeWidget routeCircle = new MapEdgeWidget(getContext());
			routeCircle.setLengthValue(routeData.getLength());
			routeCircle.setId(routeData.getID());
			int x = (int) (routeData.getRatioX() * mapDrawable.getIntrinsicWidth() -
					circleSize / 2.0f);
			int y = (int) (routeData.getRatioY() * mapDrawable.getIntrinsicHeight() -
					circleSize / 2.0f);
			routeCircle.setLayoutParams(new Viewport.LayoutParams(circleSize, circleSize, x, y));
			routeCircle.setClaimedColor(PlayerColor.None);
			routeCircle.setId(routeData.ID);
			this.viewport.addView(routeCircle);
			
			routeCircle.setClickable(true);
			routeCircle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					presenter.claimRoute(routeCircle.getId());
					Toast.makeText(getContext(), String.valueOf(routeCircle.getId()), Toast.LENGTH_SHORT).show();
				}
			});
			this.mapEdgeWidgets.put(routeCircle.getId(), routeCircle);
		}
	}
	
	public void setPresenter(MapContract.Presenter presenter) {
		this.presenter = presenter;
	}
	
	private void setupViewport() {
		this.viewport.setMaxZoom(2.0f);
		this.viewport.setMinZoom(0.25f);
		this.viewport.setKeepOverContent(true);
		
//		if (this.viewportState != null) {
//			float zoom = this.viewportState.getFloat(VIEWPORT_ARG_ZOOM, 0);
//			float offsetX = this.viewportState.getFloat(VIEWPORT_ARG_OFFSETX, 0);
//			float offsetY = this.viewportState.getFloat(VIEWPORT_ARG_OFFSETY, 0);
//			if (zoom != 0) {
//				this.viewport.setZoomFactor(zoom);
//				this.viewport.setOffsetX(offsetX);
//				this.viewport.setOffsetY(offsetY);
//			}
//		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
//		this.viewportState = new Bundle();
//		this.viewportState.putFloat(VIEWPORT_ARG_ZOOM, this.viewport.getZoomFactor());
//		this.viewportState.putFloat(VIEWPORT_ARG_OFFSETX, this.viewport.getOffsetX());
//		this.viewportState.putFloat(VIEWPORT_ARG_OFFSETY, this.viewport.getOffsetY());
	}
	
	private void getViews(View view) {
		this.viewport = (Viewport) view.findViewById(R.id.map_viewport);
		this.imageViewMap = (ImageView) view.findViewById(R.id.map_imageViewMap);
	}
	
	@Override
	public void setRouteClaimed(int routeID, PlayerColor color) {
		MapEdgeWidget edgeWidget = this.mapEdgeWidgets.get(routeID);
		edgeWidget.setClaimedColor(color);
	}
}
