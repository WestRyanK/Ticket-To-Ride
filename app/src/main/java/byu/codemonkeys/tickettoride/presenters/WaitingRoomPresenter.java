package byu.codemonkeys.tickettoride.presenters;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.models.PendingGame;
import byu.codemonkeys.tickettoride.models.User;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IReportsErrors;
import byu.codemonkeys.tickettoride.mvpcontracts.WaitingRoomContract;

/**
 * Created by Ryan on 10/3/2017.
 */

public class WaitingRoomPresenter extends PresenterBase implements WaitingRoomContract.Presenter {
	
	private WaitingRoomContract.View view;
	
	public WaitingRoomPresenter(WaitingRoomContract.View view,
								INavigator navigator,
								IReportsErrors errorReporter) {
		super(navigator, errorReporter);
		this.view = view;
	}
	
	@Override
	public void startGame() {
		if (canStartGame()) {
			
		}
		
	}
	
	@Override
	public void leaveGame() {
		this.navigator.NavigateBack();
	}
	
	private boolean canStartGame() {
		return true;
	}
	
	@Override
	public void setDefaults() {
		this.view.setWaitingUsers(new ArrayList<User>());
		this.view.setPendingGameName("");
		
	}
}
