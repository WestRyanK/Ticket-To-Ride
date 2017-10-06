package byu.codemonkeys.tickettoride.shared.results;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.GameBase;

/**
 * Created by Ryan on 9/29/2017.
 */

public class PendingGamesResult extends Result {
    private List<GameBase> games;
	
	public PendingGamesResult(List<GameBase> games) {
        super();
        this.games = games;
    }

	public PendingGamesResult(String errorMessage) {
        super(errorMessage);
    }

    public List<GameBase> getPendingGames() {
        return this.games;
    }
}
