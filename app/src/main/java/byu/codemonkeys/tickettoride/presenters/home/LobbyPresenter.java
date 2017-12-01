package byu.codemonkeys.tickettoride.presenters.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.home.LobbyContract;
import byu.codemonkeys.tickettoride.networking.LobbyPoller;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.ExistingGame;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/3/2017.
 */

public class LobbyPresenter extends PresenterBase implements LobbyContract.Presenter, Observer {
	
	LobbyContract.View view;
	
	public LobbyPresenter(LobbyContract.View view,
						  INavigator navigator,
						  IDisplaysMessages messageDisplayer,
						  IModelFacade modelFacade,
						  IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
		modelFacade.addObserver(this);
	}
	
	@Override
	public void createGame() {
		this.navigator.navigate(PresenterEnum.CreateGame, true);
	}
	
	@Override
	public void joinPendingGame(GameBase game) {
		
		ICallback joinPendingGameCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					navigator.navigate(PresenterEnum.WaitingRoom, true);
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		modelFacade.joinPendingGameAsync(game, joinPendingGameCallback);
	}
	
	@Override
	public void joinExistingGame(ExistingGame game) {
		ICallback joinExistingGameCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					navigator.navigate(PresenterEnum.Game, true);
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		modelFacade.joinExistingGameAync(game, joinExistingGameCallback);
	}
	
	@Override
	public void logout() {
		this.navigator.navigate(PresenterEnum.Login, false);
	}
	
	@Override
	public void setDefaults() {
		this.loadPendingGames();
		this.loadExistingGames();
	}
	
	@Override
	public void startPolling() {
		LobbyPoller.getInstance().startPolling();
	}
	
	@Override
	public void stopPolling() {
		LobbyPoller.getInstance().stopPolling();
	}
	
	private void loadPendingGames() {
		List<GameBase> games = modelFacade.getPendingGames();
		if (games == null)
			games = new ArrayList<>();
		this.view.setPendingGames(games);
	}
	
	private void loadExistingGames() {
		List<ExistingGame> games = ModelRoot.getInstance().getExistingGames();
		if (games == null)
			games = new ArrayList<>();
		this.view.setExistingGames(games);
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.PENDING_GAMES_UPDATE)
			this.loadPendingGames();
		if (o == ModelFacade.EXISTING_GAMES_UPDATE)
			this.loadExistingGames();
	}
}
