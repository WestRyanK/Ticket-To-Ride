package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class GetPendingGamesCommandTest {
	
	
	private GetPendingGamesCommand command;
	
	@Before
	public void Init() {
		command = new GetPendingGamesCommand();
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