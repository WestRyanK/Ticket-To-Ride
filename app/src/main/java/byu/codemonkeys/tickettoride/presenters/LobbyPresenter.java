package byu.codemonkeys.tickettoride.presenters;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.models.PendingGame;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IReportsErrors;
import byu.codemonkeys.tickettoride.mvpcontracts.LobbyContract;

/**
 * Created by Ryan on 10/3/2017.
 */

public class LobbyPresenter extends PresenterBase implements LobbyContract.Presenter {
	
	LobbyContract.View view;
	
	public LobbyPresenter(LobbyContract.View view, INavigator navigator, IReportsErrors errorReporter) {
		super(navigator, errorReporter);
		this.view = view;
		
//		List<PendingGame> pendingGames = new ArrayList<>();
//		pendingGames.add(new PendingGame("MyGame", 4,5));
//		pendingGames.add(new PendingGame("YourGame", 2,3));
//
//		this.view.setPendingGames(pendingGames);
	}
	
	@Override
	public void createGame() {
		this.navigator.Navigate(PresenterEnum.CreateGame, true);
	}
	
	@Override
	public void joinGame() {
		this.navigator.Navigate(PresenterEnum.WaitingRoom, true);
	}
	
	@Override
	public void logout() {
		this.navigator.Navigate(PresenterEnum.Login, false);
	}
	
	@Override
	public void setDefaults() {
		this.view.setPendingGames(new ArrayList<PendingGame>());
		
	}
}
