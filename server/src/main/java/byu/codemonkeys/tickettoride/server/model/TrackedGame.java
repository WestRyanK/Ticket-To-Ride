package byu.codemonkeys.tickettoride.server.model;

public class TrackedGame {
    private ActiveGame game;
    private transient int numStoredCommands;
    private String currentPlayersTurn;

    public TrackedGame(ActiveGame game) {
        this.game = game;
        this.currentPlayersTurn = game.getCurrentPlayer().getUsername();
        numStoredCommands = 0;
    }

    public int getNumStoredCommands() {
        return numStoredCommands;
    }

    public void incrementCommands() {
        numStoredCommands++;
    }

    public ActiveGame getGame() {
        return game;
    }

    public void clearCommands() {
        // TODO: Check race conditions
        numStoredCommands = 0;
    }

    public TrackedGame prepareToBeStored() {
        currentPlayersTurn = game.getCurrentPlayer().getUsername();
        return this;
    }

    public String getCurrentPlayersTurn() {
        return currentPlayersTurn;
    }
}
