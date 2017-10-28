package byu.codemonkeys.tickettoride.presenters.game;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawDestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

/**
 * Created by Ryan on 10/21/2017.
 */

public class DrawDestinationCardsPresenter extends PresenterBase implements DrawDestinationCardsContract.Presenter {
	DrawDestinationCardsContract.View view;
	
	public DrawDestinationCardsPresenter(DrawDestinationCardsContract.View view,
										 INavigator navigator,
										 IDisplaysMessages messageDisplayer,
										 IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void acceptSelectedCards() {
		navigator.navigateBack(PresenterEnum.Game);
	}
}
