package byu.codemonkeys.tickettoride.server.model;

import byu.codemonkeys.tickettoride.shared.model.GameBase;


public class PendingGame extends GameBase {
    private int minPlayers, maxPlayers;
    
    public PendingGame(String gameID, String gameName, int minPlayers, int maxPlayers) {
        this.gameName = gameName;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.gameID = gameID;
    }
}
