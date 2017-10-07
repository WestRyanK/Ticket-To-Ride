package byu.codemonkeys.tickettoride.presenters;

import java.util.ArrayList;

import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.WaitingRoomContract;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * Created by Ryan on 10/3/2017.
 */

public class WaitingRoomPresenter extends PresenterBase implements WaitingRoomContract.Presenter {
	
	private WaitingRoomContract.View view;
	
	public WaitingRoomPresenter(WaitingRoomContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void startGame() {
		if (canStartGame()) {
			try {
				modelFacade.startGame();
			} catch (Exception e) {
				messageDisplayer.displayMessage(e.getMessage());
			}
		}
	}
	
	@Override
	public void leaveGame() {
		this.navigator.NavigateBack();
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
}
