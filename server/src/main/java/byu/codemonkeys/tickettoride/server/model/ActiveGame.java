package byu.codemonkeys.tickettoride.server.model;

import byu.codemonkeys.tickettoride.shared.model.GameBase;

public class ActiveGame extends GameBase {
    public ActiveGame(PendingGame pendingGame) {
        this.gameID = pendingGame.getID();
        this.gameName = pendingGame.getName();
        this.gameOwner = pendingGame.getOwner();
        this.gameUsers = pendingGame.getUsers();
    }
}
