package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

import static org.junit.Assert.*;

public class LeavePendingGameCommandTest {
	
	private LeavePendingGameCommand command;
	
	@Before
	public void Init() {
		command = new LeavePendingGameCommand();
		command.setAuthToken("auth-token");
	}
	
	@Test
	public void execute() throws Exception {
		Result result = command.execute();
		assertEquals("Should return a result of type PendingGamesResult.",
					 PendingGamesResult.class,
					 result.getClass());
	}
}