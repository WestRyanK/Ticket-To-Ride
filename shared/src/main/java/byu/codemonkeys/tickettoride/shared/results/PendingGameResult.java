package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.model.GameBase;

public class PendingGameResult extends Result {
    private final GameBase game;

    public PendingGameResult(GameBase game) {
        super();
        this.game = game;
    }

    public PendingGameResult(String errorMessage) {
        super(errorMessage);
        this.game = null;
    }

    public GameBase getGame() {
        return game;
    }
}
