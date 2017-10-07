package byu.codemonkeys.tickettoride.presenters;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.LobbyContract;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/3/2017.
 */

public class LobbyPresenter extends PresenterBase implements LobbyContract.Presenter {
	
	LobbyContract.View view;
	
	public LobbyPresenter(LobbyContract.View view,
						  INavigator navigator,
						  IDisplaysMessages messageDisplayer,
						  IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void createGame() {
		this.navigator.Navigate(PresenterEnum.CreateGame, true);
	}
	
	@Override
	public void joinGame(GameBase game) {
		
		ICallback joinPendingGameCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					navigator.Navigate(PresenterEnum.WaitingRoom, true);
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		modelFacade.joinPendingGameAsync(game, joinPendingGameCallback);
	}
	
	@Override
	public void logout() {
		this.navigator.Navigate(PresenterEnum.Login, false);
	}
	
	@Override
	public void setDefaults() {
		try {
			List<GameBase> games = modelFacade.getPendingGames();
			if (games == null)
				games = new ArrayList<>();
			this.view.setPendingGames(games);
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			messageDisplayer.displayMessage(e.getMessage());
		}
	}
}
