package byu.codemonkeys.tickettoride.shared.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class LeavePendingGameCommandDataTest {
	
	
	public LeavePendingGameCommandData commandData;
	public static final String gameID = "AAAAAA";
	
	@Before
	public void Init() {
		commandData = new LeavePendingGameCommandData();
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals("CommandData doesn't have correct commandName",
					 CommandType.LEAVE_PENDING_GAME,
					 commandData.getCommandType());
	}
}