package byu.codemonkeys.tickettoride.presenters.game;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.game.GameSidebarContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

/**
 * Created by Ryan on 10/16/2017.
 */

public class GameSidebarPresenter extends PresenterBase implements GameSidebarContract.Presenter {
	
	private GameSidebarContract.View view;
	
	public GameSidebarPresenter(GameSidebarContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void navigateDestinationCards() {
		this.navigator.navigate(PresenterEnum.DestinationCards, true);
	}
	
	@Override
	public void navigateDrawTrainCards() {
		this.navigator.navigate(PresenterEnum.DrawTrainCards, true);
	}
	
	@Override
	public void navigateChatHistory() {
		this.navigator.navigate(PresenterEnum.ChatHistory, true);
	}
	
	@Override
	public void navigatePhase2Testing() {
		this.navigator.navigate(PresenterEnum.Phase2Testing, true);
		
	}
}
