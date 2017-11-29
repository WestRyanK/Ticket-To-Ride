package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.model.ActiveGame;

/**
 * Created by Ryan on 11/29/2017.
 */

public class JoinExistingGameResult extends Result {
	private final ActiveGame restoredGame;
	
	public JoinExistingGameResult(ActiveGame restoredGame) {
		this.restoredGame = restoredGame;
	}
	
	public JoinExistingGameResult(String errorMessage) {
		super(errorMessage);
		this.restoredGame = null;
	}
	
	public ActiveGame getRestoredGame() {
		return restoredGame;
	}
}
