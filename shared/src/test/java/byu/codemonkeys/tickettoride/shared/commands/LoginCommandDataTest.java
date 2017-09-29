package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class LoginCommandDataTest {
	
	public LoginCommandData commandData;
	public static final String username = "westryank";
	public static final String password = "thisisnotmypassword";
	
	@Before
	public void Init() {
		commandData = new LoginCommandData(username, password);
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 "Login",
					 commandData.getCommandName());
		
		assertEquals("Should have held onto the username from construction.",
					 username,
					 commandData.getUserName());
		
		assertEquals("Should have held onto the password from construction.",
					 password,
					 commandData.getPassword());
	}
}