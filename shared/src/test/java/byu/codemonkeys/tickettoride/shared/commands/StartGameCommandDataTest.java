package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class StartGameCommandDataTest {
	
	public StartGameCommandData commandData;
	public static final String gameID = "AAAAAA";
	
	@Before
	public void Init() {
		commandData = new StartGameCommandData(gameID);
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 "StartGame",
					 commandData.getCommandName());
		
		assertEquals("Should have held onto the gameID from construction.",
					 gameID,
					 commandData.getGameID());
	}
}