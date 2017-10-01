package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class GetPendingGamesCommandDataTest {
	
	public GetPendingGamesCommandData commandData;
	
	@Before
	public void Init() {
		commandData = new GetPendingGamesCommandData();
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 CommandType.GET_PENDING_GAMES,
					 commandData.getCommandType());
	}
	
}