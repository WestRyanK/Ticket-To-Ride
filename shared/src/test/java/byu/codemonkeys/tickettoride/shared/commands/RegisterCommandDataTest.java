package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class RegisterCommandDataTest {
	
	public RegisterCommandData commandData;
	public static final String username = "westryank";
	public static final String password = "thisisnotmypassword";
	
	@Before
	public void Init() {
		commandData = new RegisterCommandData(username, password);
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 "Register",
					 commandData.getCommandType());
		
		assertEquals("Should have held onto the username from construction.",
					 username,
					 commandData.getUserName());
		
		assertEquals("Should have held onto the password from construction.",
					 password,
					 commandData.getPassword());
	}
}