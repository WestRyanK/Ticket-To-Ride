package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class LogoutCommandDataTest {
	
	
	public LogoutCommandData commandData;
	
	@Before
	public void Init() {
		commandData = new LogoutCommandData();
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 CommandType.LOGOUT,
					 commandData.getCommandType());
	}
}