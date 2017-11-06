package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.EndGamePlayerStats;

/**
 * Created by Ryan on 11/4/2017.
 */

public interface EndGameContract {
	interface View{
		void setEndGameStats(List<EndGamePlayerStats> endGameStats);
		
		void setWinner(String winner);
	}
	interface Presenter{
		void navigateLobby();
		
		void loadEndGameResults();
	}
}
