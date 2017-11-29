package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.model.ActiveGame;
import byu.codemonkeys.tickettoride.shared.model.Session;

/**
 * Created by Ryan on 11/29/2017.
 */

public class JoinExistingGameResult extends Result {
	private final ActiveGame restoredGame;
	
	private final Session restoredSession;
	
	public JoinExistingGameResult(ActiveGame restoredGame, Session restoredSession) {
		this.restoredGame = restoredGame;
		this.restoredSession = restoredSession;
	}
	
	public JoinExistingGameResult(String errorMessage) {
		super(errorMessage);
		this.restoredGame = null;
		this.restoredSession = null;
	}
	
	public ActiveGame getRestoredGame() {
		return restoredGame;
	}
	
	public Session getRestoredSession() {
		return restoredSession;
	}
}
