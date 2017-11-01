package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.GameContract;
import byu.codemonkeys.tickettoride.networking.GamePoller;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;

public class GamePresenter extends PresenterBase implements GameContract.Presenter, Observer {
	private GameContract.View view;
	
	public GamePresenter(GameContract.View view,
						 INavigator navigator,
						 IDisplaysMessages messageDisplayer,
						 IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
		this.modelFacade.addObserver(this);
	}
	
	@Override
	public void startPolling() {
		GamePoller.getInstance().startPolling();
	}
	
	@Override
	public void stopPolling() {
		GamePoller.getInstance().startPolling();
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.GAME_UPDATE) {
			this.navigator.navigate(PresenterEnum.DrawDestinationCards, true);
		}
	}
}
