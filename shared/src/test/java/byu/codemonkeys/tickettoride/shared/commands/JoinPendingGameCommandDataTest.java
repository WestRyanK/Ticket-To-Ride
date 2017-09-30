package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class JoinPendingGameCommandDataTest {
	
	
	public JoinPendingGameCommandData commandData;
	public static final String gameID = "AAAAAA";
	
	@Before
	public void Init() {
		commandData = new JoinPendingGameCommandData(gameID);
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 "JoinPendingGame",
					 commandData.getCommandType());
		
		assertEquals("Should have held onto the gameID from construction.",
					 gameID,
					 commandData.getGameID());
	}
	
}