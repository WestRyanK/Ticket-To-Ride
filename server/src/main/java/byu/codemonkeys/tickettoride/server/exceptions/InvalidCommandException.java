package byu.codemonkeys.tickettoride.server.exceptions;

public class InvalidCommandException extends RuntimeException {
	private final String type;
	
	public InvalidCommandException(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return String.format("The command type of '$s' was invalid.", this.type);
	}
}
