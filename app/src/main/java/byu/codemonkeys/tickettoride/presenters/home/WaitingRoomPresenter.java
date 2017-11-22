package byu.codemonkeys.tickettoride.presenters.home;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.home.WaitingRoomContract;
import byu.codemonkeys.tickettoride.networking.PendingGamePoller;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/3/2017.
 */

public class WaitingRoomPresenter extends PresenterBase implements WaitingRoomContract.Presenter, Observer {
	
	private WaitingRoomContract.View view;
	
	public WaitingRoomPresenter(WaitingRoomContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade,
								IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
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
					} else {
						messageDisplayer.displayMessage(result.getErrorMessage());
					}
				}
			};
			modelFacade.startGameAsync(startGameCallback);
		} else {
			messageDisplayer.displayMessage("There are not enough players to start the game");
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
			this.view.setCanStartGame(canStartGame());
			this.view.setIsStartGameVisible(modelFacade.getPendingGame()
													   .getOwner()
													   .getUsername()
													   .equals(ModelRoot.getInstance()
																		.getUser()
																		.getUsername()));
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
				if (modelFacade.getPendingGame().isStarted()) {
					stopPolling();
					
					this.navigator.navigate(PresenterEnum.Game, false);
				} else {
					this.loadWaitingPlayers();
				}
			} catch (UnauthorizedException e) {
				e.printStackTrace();
			} catch (NoPendingGameException e) {
				stopPolling();
				this.navigator.navigateBack();
			}
		}
	}
}
