package byu.codemonkeys.tickettoride.mvpcontracts;

/**
 * Created by Ryan on 10/17/2017.
 */

public interface Phase2TestingContract {
	interface View {
		
	}
	
	interface Presenter {
		void UpdatePlayerPoints();
		
		void AddRemoveTrainCardsThisPlayer();
		
		void AddRemoveDestinationCardsThisPlayer();
		
		void UpdateTrainCardsOtherPlayers();
		
		void UpdateDestinationCardsOtherPlayers();
		
		void UpdateVisibleInvisibleCardsInTrainCardDeck();
		
		void UpdateCardsInDestinationCardDeck();
		
		void AddClaimedRoute();
		
		void AddChatMessage();
		
		void AddGameHistoryEntry();
		
		void navigateBack();
	}
}
