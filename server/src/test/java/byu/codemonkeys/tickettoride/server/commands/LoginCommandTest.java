package byu.codemonkeys.tickettoride.server.commands;

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.server.model.RootModel;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class LoginCommandTest {
	
	private LoginCommand command;
	private static final String username = "username";
	private static final String password = "password";
	
	@Before
	public void Init() {
		command = new LoginCommand(username, password);
	}
	
	@Test
	public void execute() throws Exception {
		Result result = command.execute();
		assertEquals("Should return a result of type LoginResult.",
					 LoginResult.class,
					 result.getClass());
		assertEquals("Command should've succeeded.", true, result.isSuccessful());
	}
	
}