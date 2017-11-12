package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.model.Session;

public class LoginResult extends Result {
	private final Session userSession;
	
	public LoginResult(Session userSession) {
		super();
		this.userSession = userSession;
	}

	public LoginResult(String errorMessage) {
		super(errorMessage);
		this.userSession = null;
	}

	public Session getUserSession() {
		return userSession;
	}
}
