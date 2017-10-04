package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

/**
 * Created by Ryan on 10/3/2017.
 */

public class PresenterBase {
	
	public PresenterBase(INavigator navigator) {
		this.navigator = navigator;
	}
	
	public void setNavigator(INavigator navigator) {
		this.navigator = navigator;
	}
	
	protected INavigator navigator;
}
