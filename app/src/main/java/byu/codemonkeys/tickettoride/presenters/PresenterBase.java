package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

/**
 * Created by Ryan on 10/3/2017.
 */

public class PresenterBase {
	
	public PresenterBase(INavigator navigator,
						 IDisplaysMessages messageDisplayer,
						 IModelFacade modelFacade) {
		this.navigator = navigator;
		this.messageDisplayer = messageDisplayer;
		this.modelFacade = modelFacade;
	}
	
	public void setNavigator(INavigator navigator) {
		this.navigator = navigator;
	}
	
	protected INavigator navigator;
	protected IDisplaysMessages messageDisplayer;
	protected IModelFacade modelFacade;
}
