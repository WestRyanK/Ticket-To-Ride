package byu.codemonkeys.tickettoride.mvpcontracts.game;

/**
 * Created by Ryan on 10/21/2017.
 */

public interface ChatHistoryContract {
	interface View {
		void clearMessages();
		
		boolean isActive();
		
		void addMessage(String message);
		
		void setCanSendMessage(boolean canSendMessage);
		
		String getCurrentMessage();
		
		void setCurrentMessage(String message);
	}
	
	interface Presenter {
		void sendMessage(String message);
		
		void navigateBack();
		
		boolean canSendMessage();

		void loadHistory();
		
	}
}
