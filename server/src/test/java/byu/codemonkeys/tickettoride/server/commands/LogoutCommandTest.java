package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.shared.results.Result;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class LogoutCommandTest {
	
	public LogoutCommand command;
	
	@Before
	public void Init() {
		command = new LogoutCommand();
	}
	
	@Test
	public void execute() throws Exception {
		Result result = command.execute();
		assertEquals("Should return a result of type Result.", Result.class, result.getClass());
		assertEquals("Command should've succeeded.", true, result.isSuccessful());
	}
}