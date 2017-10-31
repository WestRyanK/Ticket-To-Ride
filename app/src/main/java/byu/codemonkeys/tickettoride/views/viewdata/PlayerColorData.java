package byu.codemonkeys.tickettoride.views.viewdata;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

import byu.codemonkeys.tickettoride.models.player.PlayerColorEnum;

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
	
	private Map<PlayerColorEnum, Integer> colorMap;
	
	private PlayerColorData() {
		colorMap = new HashMap<>();
		colorMap.put(PlayerColorEnum.Black, Color.parseColor("#FF424242"));
		colorMap.put(PlayerColorEnum.Blue, Color.parseColor("#FF0F05A2"));
		colorMap.put(PlayerColorEnum.Green, Color.parseColor("#FF05A219"));
		colorMap.put(PlayerColorEnum.None, Color.parseColor("#FF888888"));
		colorMap.put(PlayerColorEnum.Red, Color.parseColor("#FFA20505"));
		colorMap.put(PlayerColorEnum.Yellow, Color.parseColor("#FFCCB800"));
	}
	
	public int getColor(PlayerColorEnum colorEnum) {
		return this.colorMap.get(colorEnum);
	}
}
