package byu.codemonkeys.tickettoride.shared.model.map;

/**
 * Created by Ryan on 11/21/2017.
 */

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.map.longestpathsolver.LongestPathCalculator;

import static org.junit.Assert.*;

public class LongestPathCalculatorTest {
	private UserBase userA;
	private UserBase userB;
	private static final int aangsIceberg_southernAirTempleCentral = 28;
	private static final int whaleTailIsland_southernAirTempleCentral = 35;
	private static final int whaleTailIsland_kyoshiIsland = 37;
	private static final int beiFongEstate_kyoshiIsland = 41;
	private static final int beiFongEstate_tuZinGhostTown = 49;
	private static final int beiFongEstate_wanShiTongLibrary = 48;
	private static final int chameleonBay_wanShiTongLibrary = 54;
	private static final int chameleonBay_tuZinGhostTown = 55;
	
	@Before
	public void init() {
		userA = new UserBase("codeMonkey");
		userB = new UserBase("codeGorilla");
	}
	
	/**
	 * Test longest path with a straight line
	 */
//	@Test
	public void LongestPath1Test() {
		GameMap map = new GameMap();
		map.claimRoute(aangsIceberg_southernAirTempleCentral, userA);
		map.claimRoute(whaleTailIsland_kyoshiIsland, userA);
		map.claimRoute(whaleTailIsland_southernAirTempleCentral, userA);
		map.claimRoute(beiFongEstate_kyoshiIsland, userA);
		map.claimRoute(beiFongEstate_tuZinGhostTown, userA);
		
		int longestPathLength = LongestPathCalculator.findLongestPath(map, userA);
		System.out.print(longestPathLength);
		assertEquals(14, longestPathLength);
	}
	
	/**
	 * Test longest path with a branch
	 */
//	@Test
	public void LongestPath2Test() {
		GameMap map = new GameMap();
		map.claimRoute(aangsIceberg_southernAirTempleCentral, userA);
		map.claimRoute(whaleTailIsland_kyoshiIsland, userA);
		map.claimRoute(whaleTailIsland_southernAirTempleCentral, userA);
		map.claimRoute(beiFongEstate_kyoshiIsland, userA);
		map.claimRoute(beiFongEstate_tuZinGhostTown, userA);
		map.claimRoute(beiFongEstate_wanShiTongLibrary, userA);
		map.claimRoute(chameleonBay_wanShiTongLibrary, userA);
		
		int longestPathLength = LongestPathCalculator.findLongestPath(map, userA);
		System.out.print(longestPathLength);
		assertEquals(18, longestPathLength);
	}
	
	/**
	 * Test longest path with a loop
	 */
//	@Test
	public void LongestPath3Test() {
		GameMap map = new GameMap();
		map.claimRoute(aangsIceberg_southernAirTempleCentral, userA);
		map.claimRoute(whaleTailIsland_kyoshiIsland, userA);
		map.claimRoute(whaleTailIsland_southernAirTempleCentral, userA);
		map.claimRoute(beiFongEstate_kyoshiIsland, userA);
		map.claimRoute(beiFongEstate_tuZinGhostTown, userA);
		map.claimRoute(beiFongEstate_wanShiTongLibrary, userA);
		map.claimRoute(chameleonBay_wanShiTongLibrary, userA);
		map.claimRoute(chameleonBay_tuZinGhostTown, userA);
		
		int longestPathLength = LongestPathCalculator.findLongestPath(map, userA);
		System.out.print(longestPathLength);
		assertEquals(25, longestPathLength);
	}
}
