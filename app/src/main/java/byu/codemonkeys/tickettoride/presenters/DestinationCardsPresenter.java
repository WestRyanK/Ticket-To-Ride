package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.DestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCardsPresenter extends PresenterBase implements DestinationCardsContract.Presenter {
	DestinationCardsContract.View view;
	
	public DestinationCardsPresenter(DestinationCardsContract.View view,
									 INavigator navigator,
									 IDisplaysMessages messageDisplayer,
									 IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void navigateBack() {
		this.navigator.navigateBack();
		
	}
	
	@Override
	public void navigateDrawDestinationCards() {
		this.navigator.navigate(PresenterEnum.DrawDestinationCards, false);
	}
}
