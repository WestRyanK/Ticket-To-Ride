package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.ChatHistoryContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

/**
 * Created by Ryan on 10/21/2017.
 */

public class ChatHistoryPresenter extends PresenterBase implements ChatHistoryContract.Presenter {
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
}
