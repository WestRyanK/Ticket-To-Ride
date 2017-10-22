package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.DrawDestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

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
		navigator.navigateBack();
	}
}
