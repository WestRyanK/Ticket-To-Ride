package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class StartGameCommandTest {
	
	private StartGameCommand command;
	private static final String gameID = "AAAAA";
	
	@Before
	public void Init() {
		command = new StartGameCommand();
		command.setAuthToken("auth-token");
	}

	@Ignore("No longer returns a StartGameResult")
	@Test
	public void execute() throws Exception {
		Result result = command.execute();
		assertEquals("Should return a result of type StartGameResult.",
					 StartGameResult.class,
					 result.getClass());
	}
	
}