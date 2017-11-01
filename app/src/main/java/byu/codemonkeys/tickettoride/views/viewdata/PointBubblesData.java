package byu.codemonkeys.tickettoride.views.viewdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 10/23/2017.
 */

public class PointBubblesData {
	private static PointBubblesData instance;
	
	public static PointBubblesData getInstance() {
		if (instance == null)
			instance = new PointBubblesData();
		return instance;
	}
	
	private Map<Integer, PointBubbleData> bubblesMap;
	
	private PointBubblesData() {
		bubblesMap = new HashMap<>();
		
		int index = 0;
		//		bubblesMap.put(index++, new PointBubbleData()); //
		
		// Western Air Temple	Northern Water Tribe
		bubblesMap.put(index++, new PointBubbleData(0.380616413f, 0.198675497f, 1, 1, 17));
		// Battlefield of 100 Year War	Northern Water Tribe
		bubblesMap.put(index++, new PointBubbleData(0.412179725f, 0.229240958f, 1, 16, 17));
		// Pohuai Stronghold	Northern Water Tribe
		bubblesMap.put(index++, new PointBubbleData(0.479019681f, 0.211411105f, 1, 25, 17));
		// Northern Air Temple	Northern Water Tribe
		bubblesMap.put(index++, new PointBubbleData(0.518009655f, 0.147733062f, 1, 18, 17));
		// Pohuai Stronghold	Northern Air Temple
		bubblesMap.put(index++, new PointBubbleData(0.529149647f, 0.211411105f, 1, 25, 18));
		// Serpent's Pass	Northern Air Temple
		bubblesMap.put(index++, new PointBubbleData(0.597846268f, 0.254712175f, 1, 31, 18));
		// Ba Sing Se North	Northern Air Temple
		bubblesMap.put(index++, new PointBubbleData(0.631266246f, 0.198675497f, 1, 19, 18));
	}
	
	public Map<Integer, PointBubbleData> getAllBubbles() {
		return this.bubblesMap;
	}
	
	public class PointBubbleData {
		public float getMapXRatio() {
			return mapXRatio;
		}
		
		public float getMapYRatio() {
			return mapYRatio;
		}
		
		public int getPointValue() {
			return pointValue;
		}
		
		public int getCityA() {
			return cityA;
		}
		
		public int getCityB() {
			return cityB;
		}
		
		public PointBubbleData(float mapXRatio,
							   float mapYRatio,
							   int pointValue,
							   int cityA,
							   int cityB) {
			this.mapXRatio = mapXRatio;
			this.mapYRatio = mapYRatio;
			this.pointValue = pointValue;
			this.cityA = cityA;
			this.cityB = cityB;
		}
		
		private final float mapXRatio;
		private final float mapYRatio;
		private final int pointValue;
		private final int cityA;
		private final int cityB;
		
	}
}
