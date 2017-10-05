package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IReportsErrors;

/**
 * Created by Ryan on 10/3/2017.
 */

public class PresenterBase {
	
	public PresenterBase(INavigator navigator, IReportsErrors errorReporter) {
		this.navigator = navigator;
		this.errorReporter = errorReporter;
	}
	
	public void setNavigator(INavigator navigator) {
		this.navigator = navigator;
	}
	
	protected INavigator navigator;
	protected IReportsErrors errorReporter;
}
