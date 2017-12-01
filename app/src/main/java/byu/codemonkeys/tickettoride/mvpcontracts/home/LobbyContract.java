package byu.codemonkeys.tickettoride.mvpcontracts.home;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.ExistingGame;
import byu.codemonkeys.tickettoride.shared.model.GameBase;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface LobbyContract {
	interface View {
		void setPendingGames(List<GameBase> pendingGames);
		
		void setExistingGames(List<ExistingGame> existingGames);
	}
	
	interface Presenter {
		void createGame();
		
		void joinPendingGame(GameBase game);
		
		void joinExistingGame(ExistingGame game);
		
		void logout();
		
		void setDefaults();
		
		void startPolling();
		
		void stopPolling();
	}
}
