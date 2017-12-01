package byu.codemonkeys.tickettoride.shared.model;

/**
 * Created by Ryan on 11/29/2017.
 */

public class ExistingGame extends GameBase {
	private final String currentPlayerTurn;
	
	public ExistingGame(String gameID, String gameName, UserBase gameOwner, String currentPlayerTurn) {
		this.currentPlayerTurn = currentPlayerTurn;
		this.gameID = gameID;
		this.gameName = gameName;
		this.gameOwner = gameOwner;
	}
	
	public String getCurrentPlayerTurn() {
		return currentPlayerTurn;
	}
}
