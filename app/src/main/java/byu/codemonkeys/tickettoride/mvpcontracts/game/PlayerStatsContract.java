package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.Player;

/**
 * Created by Ryan on 10/17/2017.
 */

public interface PlayerStatsContract {
	interface View {
		void setPlayerStats(List<Player> players);
		
		void setCurrentTurn(int playerIndex);
	}
	
	interface Presenters {
		
	}
}
