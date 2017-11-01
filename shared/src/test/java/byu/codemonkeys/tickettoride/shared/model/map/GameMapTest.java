package byu.codemonkeys.tickettoride.shared.model.map;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 10/30/2017.
 */

public class GameMapTest {
	@Test
	public void LoadMapTest()
	{
		GameMap map = new GameMap();
		assertEquals(103, map.getAllRoutes().size());
	}
	
}
