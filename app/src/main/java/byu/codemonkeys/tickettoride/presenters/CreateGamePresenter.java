package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.PendingGame;
import byu.codemonkeys.tickettoride.mvpcontracts.CreateGameContract;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

/**
 * Created by Ryan on 10/2/2017.
 */

public class CreateGamePresenter extends PresenterBase implements CreateGameContract.Presenter {
	
	private CreateGameContract.View view;
	
	public CreateGamePresenter(CreateGameContract.View view, INavigator navigator) {
		super(navigator);
		this.view = view;
	}
	
	public void createGame() {
		if (true) {
			this.navigator.Navigate(PresenterEnum.WaitingRoom, false);
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.NavigateBack();
	}
	
	@Override
	public void setGameName(String gameName) {
		if (true) {
			this.view.setCanCreateGame(canCreateGame());
		}
	}
	
	@Override
	public void setMinPlayers(int minPlayers) {
		int UIminPlayers = minPlayers;
		if (minPlayers < PendingGame.MIN_ALLOWED_PLAYERS)
			minPlayers = PendingGame.MIN_ALLOWED_PLAYERS;
		if (minPlayers > PendingGame.MAX_ALLOWED_PLAYERS)
			minPlayers = PendingGame.MAX_ALLOWED_PLAYERS;
		if (minPlayers > 3) // TODO: change 3 to the model's current maxPlayers value.
			minPlayers = 3;
		
		if (UIminPlayers != minPlayers)
			this.view.setMinPlayers(minPlayers);
		
		if (true) { // TODO: everywhere we have if true, change it to  minPlayers != model.minPlayers
			this.view.setCanCreateGame(canCreateGame());
		}
	}
	
	@Override
	public void setMaxPlayers(int maxPlayers) {
		int UImaxPlayers = maxPlayers;
		if (maxPlayers < PendingGame.MIN_ALLOWED_PLAYERS)
			maxPlayers = PendingGame.MIN_ALLOWED_PLAYERS;
		if (maxPlayers > PendingGame.MAX_ALLOWED_PLAYERS)
			maxPlayers = PendingGame.MAX_ALLOWED_PLAYERS;
		if (maxPlayers < 2) // TODO: change 2 to the model's current minPlayers value.
			maxPlayers = 2;
		
		if (UImaxPlayers != maxPlayers)
			this.view.setMinPlayers(maxPlayers);
		if (true) { // TODO: everywhere we have if true, change it to  maxPlayers != model.maxPlayers
			this.view.setCanCreateGame(canCreateGame());
		}
	}
	
	private boolean canCreateGame() {
		return true; // TODO: change this to actual logic that determines whether we can create a game
	}
}
