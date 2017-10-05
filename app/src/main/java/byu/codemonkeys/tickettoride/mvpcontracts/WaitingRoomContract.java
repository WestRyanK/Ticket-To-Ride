package byu.codemonkeys.tickettoride.mvpcontracts;

import java.util.List;

import byu.codemonkeys.tickettoride.models.PendingGame;
import byu.codemonkeys.tickettoride.models.User;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface WaitingRoomContract {
	interface View {
		void setPendingGameName(String gameName);
		
		void setWaitingUsers(List<User> users);
		
		void setIsStartGameVisible(boolean isStartGameVisible);
	}
	
	interface Presenter {
		void startGame();
		
		void leaveGame();
		
		void setDefaults();
	}
}
