package byu.codemonkeys.tickettoride.presenters.home;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.home.ConnectionSettingsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.networking.ClientCommunicator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;

/**
 * Created by Ryan on 10/2/2017.
 */

public class ConnectionSettingsPresenter extends PresenterBase implements ConnectionSettingsContract.Presenter {
	
	private ConnectionSettingsContract.View view;
	
	public ConnectionSettingsPresenter(ConnectionSettingsContract.View view,
									   INavigator navigator,
									   IDisplaysMessages messageDisplayer,
									   IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	public void saveConnectionSettings() {
		if (canSaveConnectionSettings()) {
			int portNumber = Integer.parseInt(this.view.getPort());
			modelFacade.changeConnectionConfiguration(this.view.getHostName(), portNumber);
			this.navigator.navigateBack();
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.navigateBack();
	}
	
	@Override
	public boolean canSaveConnectionSettings() {
		return ClientCommunicator.isValidHost(this.view.getHostName()) &&
				ClientCommunicator.isValidPort(this.view.getPort());
	}
	
	@Override
	public void setDefaults() {
		this.view.setHostName(ClientCommunicator.getInstance().getHost());
		this.view.setPort(String.valueOf(ClientCommunicator.getInstance().getPort()));
	}
}
