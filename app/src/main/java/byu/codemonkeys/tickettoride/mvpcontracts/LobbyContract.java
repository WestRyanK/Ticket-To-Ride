package byu.codemonkeys.tickettoride.mvpcontracts;

import java.util.List;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface LobbyContract {
	interface View {
		void setPendingGames(List<PendingGame> pendingGames);
	}
	
	interface Presenter {
		void createGame();
		
		void joinGame();
		
		void logout();
	}
}
