package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class CreateGameCommandDataTest {
	
	
	public CreateGameCommandData commandData;
	public static final String gameName = "MyGame";
	public static final int minPlayers = 2;
	public static final int maxPlayers = 5;
	
	@Before
	public void Init() {
		commandData = new CreateGameCommandData(gameName, minPlayers, maxPlayers);
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 "CreateGame",
					 commandData.getCommandName());
		
		assertEquals("Should have held onto the gameName from construction.",
					 gameName,
					 commandData.getGameName());
		assertEquals("Should have held onto the minPlayers from construction.",
					 minPlayers,
					 commandData.getMinPlayers());
		assertEquals("Should have held onto the maxPlayers from construction.",
					 maxPlayers,
					 commandData.getMaxPlayers());
	}
	
}