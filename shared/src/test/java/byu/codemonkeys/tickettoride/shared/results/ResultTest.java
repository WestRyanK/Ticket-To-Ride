package byu.codemonkeys.tickettoride.shared.results;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 9/29/2017.
 */
public class ResultTest {
	
	@Test
	public void ParamlessConstructorTest() {
		Result result = new Result();
		assertEquals("A result should default to successful", true, result.isSuccessful());
		assertNull("A successful result should have no error message.", result.getErrorMessage());
	}
	
	@Test
	public void ConstructorTest() {
		String errorMessage = "Here be an error message!";
		Result result = new Result(errorMessage);
		assertEquals("Should have held onto the failure", false, result.isSuccessful());
		assertEquals("Should have held onto the error message",
					 errorMessage,
					 result.getErrorMessage());
	}
}