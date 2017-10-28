package byu.codemonkeys.tickettoride.presenters.game;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.Phase2TestingContract;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;

/**
 * Created by Ryan on 10/17/2017.
 */

public class Phase2TestingPresenter extends PresenterBase implements Phase2TestingContract.Presenter {
	private Phase2TestingContract.View view;
	
	public Phase2TestingPresenter(Phase2TestingContract.View view,
								  INavigator navigator,
								  IDisplaysMessages messageDisplayer,
								  IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void UpdatePlayerPoints() {
		
	}
	
	@Override
	public void AddRemoveTrainCardsThisPlayer() {
		
	}
	
	@Override
	public void AddRemoveDestinationCardsThisPlayer() {
		
	}
	
	@Override
	public void UpdateTrainCardsOtherPlayers() {
		
	}
	
	@Override
	public void UpdateDestinationCardsOtherPlayers() {
		
	}
	
	@Override
	public void UpdateVisibleInvisibleCardsInTrainCardDeck() {
		
	}
	
	@Override
	public void UpdateCardsInDestinationCardDeck() {
		
	}
	
	@Override
	public void AddClaimedRoute() {
		
	}
	
	@Override
	public void AddChatMessage() {
		
	}
	
	@Override
	public void AddGameHistoryEntry() {
		
	}
	
	@Override
	public void navigateBack() {
		this.navigator.navigateBack();
	}
}
