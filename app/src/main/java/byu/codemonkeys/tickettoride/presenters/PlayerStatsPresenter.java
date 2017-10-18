package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.PlayerStatsContract;

/**
 * Created by Ryan on 10/17/2017.
 */

public class PlayerStatsPresenter extends PresenterBase implements PlayerStatsContract.Presenters {
	private PlayerStatsContract.View view;
	
	public PlayerStatsPresenter(PlayerStatsContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
}
