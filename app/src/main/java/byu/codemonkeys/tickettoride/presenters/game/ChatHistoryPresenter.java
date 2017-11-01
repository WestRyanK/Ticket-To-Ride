package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.models.history.CommandHistoryEntry;
import byu.codemonkeys.tickettoride.mvpcontracts.game.ChatHistoryContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.results.Result;


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
		Message m = new Message(modelFacade.getUser(), message);

		ICallback sendMessageCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					view.setCurrentMessage("");
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		if (canSendMessage()) {
			modelFacade.sendMessageAsync(m, sendMessageCallback);
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
	public void loadHistory() {
		for (CommandHistoryEntry entry : ModelRoot.getInstance().getHistoryManager().getCommandHistory()) {
			this.view.addMessage(entry.toString());
		}
	}

	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.HISTORY_UPDATE) {
			for (CommandHistoryEntry entry : ModelRoot.getInstance().getHistoryManager().getLatestCommandHistory()) {
				this.view.addMessage(entry.toString());
			}
		}
	}
}
