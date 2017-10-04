package byu.codemonkeys.tickettoride.mvpcontracts;


import android.support.v4.app.Fragment;

import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

/**
 * Created by Ryan on 10/2/2017.
 */

public interface INavigator {
	void Navigate(PresenterEnum presenter, boolean allowBack);
	
	void NavigateBack();
}
