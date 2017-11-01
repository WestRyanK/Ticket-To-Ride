package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.game.ChatHistoryContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.results.Result;

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
		modelFacade.addObserver(this);
	}
	
	@Override
	public void sendMessage(String message) {
		Message m = new Message(modelFacade.getUser(), message, new Date());
		Result result = modelFacade.sendMessage(m);
		if (result.isSuccessful()) {
			this.view.setCurrentMessage("");
		} else {
			messageDisplayer.displayMessage(result.getErrorMessage());
		}
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
		if (o == ModelFacade.CHAT_UPDATE) {
			for (Message message : ModelRoot.getInstance().getChatManager().getLatestMessages()) {
				this.view.addMessage(message.toString());
			}
		}
	}
}
