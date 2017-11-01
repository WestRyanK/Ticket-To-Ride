package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.GameContract;
import byu.codemonkeys.tickettoride.networking.GamePoller;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

/**
 * Created by Ryan on 10/30/2017.
 */

public class GamePresenter extends PresenterBase implements GameContract.Presenter, Observer {
	private GameContract.View view;
	
	public GamePresenter(GameContract.View view,
						 INavigator navigator,
						 IDisplaysMessages messageDisplayer,
						 IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void startPoller() {
		GamePoller.getInstance().startPolling();
	}
	
	@Override
	public void stopPoller() {
		GamePoller.getInstance().startPolling();
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.GAME_UPDATE) {
			this.navigator.navigate(PresenterEnum.DrawDestinationCards, true);
		}
	}
}
