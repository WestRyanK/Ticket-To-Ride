package byu.codemonkeys.tickettoride.views.viewdata;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.PlayerColor;

/**
 * Created by Ryan on 10/23/2017.
 */

public class PlayerColorData {
	private static PlayerColorData instance;
	
	public static PlayerColorData getInstance() {
		if (instance == null)
			instance = new PlayerColorData();
		return instance;
	}
	
	private Map<PlayerColor, Integer> colorMap;
	
	private PlayerColorData() {
		colorMap = new HashMap<>();
		colorMap.put(PlayerColor.Black, Color.parseColor("#FF424242"));
		colorMap.put(PlayerColor.Blue, Color.parseColor("#FF0F05A2"));
		colorMap.put(PlayerColor.Green, Color.parseColor("#FF05A219"));
		colorMap.put(PlayerColor.None, Color.parseColor("#FF888888"));
		colorMap.put(PlayerColor.Red, Color.parseColor("#FFA20505"));
		colorMap.put(PlayerColor.Yellow, Color.parseColor("#FFCCB800"));
	}
	
	public int getColor(PlayerColor colorEnum) {
		return this.colorMap.get(colorEnum);
	}
}
