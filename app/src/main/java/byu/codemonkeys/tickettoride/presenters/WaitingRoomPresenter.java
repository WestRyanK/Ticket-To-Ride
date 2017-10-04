package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.WaitingRoomContract;

/**
 * Created by Ryan on 10/3/2017.
 */

public class WaitingRoomPresenter extends PresenterBase implements WaitingRoomContract.Presenter {
	
	private WaitingRoomContract.View view;
	
	public WaitingRoomPresenter(WaitingRoomContract.View view, INavigator navigator) {
		super(navigator);
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
}
