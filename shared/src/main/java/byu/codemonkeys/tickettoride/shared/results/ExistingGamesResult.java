package byu.codemonkeys.tickettoride.shared.results;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.ExistingGame;

/**
 * Created by Ryan on 11/29/2017.
 */

public class ExistingGamesResult extends Result {
	private final List<ExistingGame> existingGames;
	
	public ExistingGamesResult(List<ExistingGame> existingGames) {
		this.existingGames = existingGames;
	}
	
	public ExistingGamesResult(String errorMessage) {
		super(errorMessage);
		this.existingGames = null;
	}
	
	public List<ExistingGame> getExistingGames() {
		return existingGames;
	}
}
