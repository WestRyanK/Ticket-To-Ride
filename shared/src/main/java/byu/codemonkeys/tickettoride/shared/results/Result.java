package byu.codemonkeys.tickettoride.shared.results;

public class Result {
	private boolean successful;
	private String errorMessage;
	
	public boolean isSuccessful() {
		return successful;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public Result(String errorMessage) {
		this.successful = false;
		this.errorMessage = errorMessage;
	}
	
	public Result() {
		this.successful = true;
		this.errorMessage = null;
	}

	public static Result success() {
		return new Result();
	}
}
