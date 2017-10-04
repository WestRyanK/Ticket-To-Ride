package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;


/**
 * Created by Ryan on 9/29/2017.
 */
public class CancelGameCommandTest {
	
	public CancelGameCommand command;
	public static final String gameID = "AAAAAAAA";
	
	@Before
	public void Init() {
		command = new CancelGameCommand(gameID);
	}
	
	@Test
	public void executeTest() throws Exception {
		Result result = command.execute();
		assertEquals("Should return a result of type PendingGamesResult.", PendingGamesResult.class, result.getClass());
//		assertEquals("Command should've succeeded.", true, result.isSuccessful());
	}
	
}