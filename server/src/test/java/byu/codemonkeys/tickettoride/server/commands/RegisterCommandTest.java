package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class RegisterCommandTest {
	
	
	private RegisterCommand command;
	private static final String username = "westryank2";
	private static final String password = "Youthoughtidputmyrealpasswordheredidntyou";
	
	@Before
	public void Init() {
		command = new RegisterCommand(username, password);
	}
	
	@Test
	public void execute() throws Exception {
		// TODO: Make unit tests run independently (flush previous data between tests or make a mock server model)
		Result result = command.execute();
		assertEquals("Should return a result of type LoginResult.",
					 LoginResult.class,
					 result.getClass());
		assertEquals("Command should've succeeded.", true, result.isSuccessful());
	}
}