package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.model.GameBase;

public class StartGameResult extends Result {
    private final GameBase game;
	
	public StartGameResult(GameBase game) {
        super();
        this.game = game;
	}

	public StartGameResult(String errorMessage) {
		super(errorMessage);
		this.game = null;
	}

	public GameBase getGame() {
		return this.game;
	}
}
