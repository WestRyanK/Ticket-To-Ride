package byu.codemonkeys.tickettoride.mvpcontracts.home;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface CreateGameContract {
	interface View {
		void setGameName(String gameName);
		
		String getGameName();
		
		//		void setMinPlayers(int minPlayers);
		//
		//		void setMaxPlayers(int maxPlayers);
		
		void setCanCreateGame(boolean canCreateGame);
	}
	
	interface Presenter {
		void createGame();
		
		void cancel();
		
		void setDefaults();
		
		boolean canCreateGame();
	}
}
