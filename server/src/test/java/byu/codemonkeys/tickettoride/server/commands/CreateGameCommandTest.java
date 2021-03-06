package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.shared.commands.CommandType;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class CreateGameCommandTest {
	
	private CreateGameCommand command;
	private static final String gameName = "MyGame";
	private static final int minPlayers = 2;
	private static final int maxPlayers = 5;
	
	
	@Before
	public void Init() {
		command = new CreateGameCommand(gameName);
		command.setAuthToken("auth-token");
	}
	
	@Test
	public void executeTest() throws Exception {
		Result result = command.execute();
		assertEquals("Should return a result of type PendingGameResult.",
					 PendingGameResult.class,
					 result.getClass());
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("Command doesn't have correct commandName",
				CommandType.CREATE_GAME,
					 command.getCommandType());
		
		assertEquals("Should have held onto the gameName from construction.",
					 gameName,
					 command.getGameName());
	}
	
}