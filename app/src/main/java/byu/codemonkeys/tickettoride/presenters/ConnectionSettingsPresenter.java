package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.ClientSession;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.ConnectionSettingsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IReportsErrors;
import byu.codemonkeys.tickettoride.networking.ClientCommunicator;

/**
 * Created by Ryan on 10/2/2017.
 */

public class ConnectionSettingsPresenter extends PresenterBase implements ConnectionSettingsContract.Presenter {
	
	private ConnectionSettingsContract.View view;
	private String currentHost;
	private String currentPort;
	
	public ConnectionSettingsPresenter(ConnectionSettingsContract.View view,
									   INavigator navigator,
									   IReportsErrors errorReporter) {
		super(navigator, errorReporter);
		this.view = view;
	}
	
	public void saveConnectionSettings() {
		if (canSaveConnectionSettings()) {
			ClientCommunicator.getInstance().setHost(this.currentHost);
			int portNumber = Integer.parseInt(this.currentPort);
			ClientCommunicator.getInstance().setPort(portNumber);
			this.navigator.NavigateBack();
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.NavigateBack();
	}
	
	@Override
	public void setHost(String host) {
		
		if (this.currentHost != host) {
			this.currentHost = host;
			this.view.setCanSave(canSaveConnectionSettings());
		}
	}
	
	@Override
	public void setPort(String port) {
		if (this.currentPort != port) {
			this.currentPort = port;
			this.view.setCanSave(canSaveConnectionSettings());
		}
	}
	
	private boolean canSaveConnectionSettings() {
		return (this.currentHost != null &&
				!this.currentHost.isEmpty() &&
				isStringPositiveInteger(this.currentPort));
	}
	
	private boolean isStringPositiveInteger(String string) {
		try {
			int a = Integer.parseInt(string);
			if (a > 0)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public void setDefaults() {
		this.view.setHost(ClientCommunicator.getInstance().getHost());
		this.view.setPort(String.valueOf(ClientCommunicator.getInstance().getPort()));
	}
}
