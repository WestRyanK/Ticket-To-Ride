package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.game.ChatHistoryContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;

/**
 * Created by Ryan on 10/21/2017.
 */

public class ChatHistoryPresenter extends PresenterBase implements ChatHistoryContract.Presenter , Observer {
	ChatHistoryContract.View view;
	
	public ChatHistoryPresenter(ChatHistoryContract.View view,
								INavigator navigator,
								IDisplaysMessages messageDisplayer,
								IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void sendMessage(String message) {
		this.view.addMessage(message);
		this.view.setCurrentMessage("");
	}
	
	@Override
	public void navigateBack() {
		this.navigator.navigateBack();
	}
	
	@Override
	public boolean canSendMessage() {
		String currentMessage = this.view.getCurrentMessage();
		return currentMessage != null &&
				currentMessage.length() > 0 &&
				currentMessage.length() < 160;
	}
	
	@Override
	public void update(Observable observable, Object o) {
		
	}
}
