package byu.codemonkeys.tickettoride.shared;

import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;

public interface IClient {
	void updatePendingGames();
	
	void updatePendingGame();
	
	void updateGame();
}
