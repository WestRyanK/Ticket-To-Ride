package byu.codemonkeys.tickettoride.presenters;

import android.app.Notification;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.WaitingRoomContract;
import byu.codemonkeys.tickettoride.networking.PendingGamePoller;
import byu.codemonkeys.tickettoride.networking.PendingGamesPoller;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/3/2017.
 */

public class WaitingRoomPresenter extends PresenterBase implements WaitingRoomContract.Presenter, Observer {
	
	private WaitingRoomContract.View view;
	
	public WaitingRoomPresenter(WaitingRoomContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
		modelFacade.addObserver(this);
	}
	
	@Override
	public void startGame() {
		if (canStartGame()) {
			ICallback startGameCallback = new ICallback() {
				@Override
				public void callback(Result result) {
					if (result.isSuccessful()) {
						messageDisplayer.displayMessage("Game Started");
					} else {
						messageDisplayer.displayMessage(result.getErrorMessage());
					}
				}
			};
			modelFacade.startGameAsync(startGameCallback);
		}
	}
	
	@Override
	public void leaveGame() {
		
		ICallback leaveGameCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					PendingGamePoller.getInstance().stopPolling();
					navigator.navigateBack();
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		modelFacade.leavePendingGameAsync(leaveGameCallback);
	}
	
	private boolean canStartGame() {
		GameBase game = null;
		try {
			game = modelFacade.getPendingGame();
		} catch (UnauthorizedException e) {
			e.printStackTrace();
		} catch (NoPendingGameException e) {
			e.printStackTrace();
		}
		
		return GameBase.canStartGame(game);
	}
	
	@Override
	public void setDefaults() {
		loadWaitingPlayers();
	}
	
	@Override
	public void startPolling() {
		PendingGamePoller.getInstance().startPolling();
	}
	
	@Override
	public void stopPolling() {
		PendingGamePoller.getInstance().stopPolling();
	}
	
	private void loadWaitingPlayers() {
		try {
			this.view.setWaitingUsers(modelFacade.getPendingGame().getUsers());
			this.view.setPendingGameName(modelFacade.getPendingGame().getName());
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			messageDisplayer.displayMessage(e.getMessage());
		} catch (NoPendingGameException e) {
			e.printStackTrace();
			messageDisplayer.displayMessage(e.getMessage());
		}
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.PENDING_GAME_UPDATE) {
			try {
				if (modelFacade.getPendingGame().isStarted())
					messageDisplayer.displayMessage("Game started!");
				this.loadWaitingPlayers();
			} catch (UnauthorizedException e) {
				e.printStackTrace();
			} catch (NoPendingGameException e) {
				stopPolling();
				this.navigator.navigateBack();
			}
		}
	}
}
