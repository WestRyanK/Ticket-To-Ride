package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.mvpcontracts.ConnectionSettingsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;

/**
 * Created by Ryan on 10/2/2017.
 */

public class ConnectionSettingsPresenter extends PresenterBase implements ConnectionSettingsContract.Presenter {
	
	private ConnectionSettingsContract.View view;
	
	public ConnectionSettingsPresenter(ConnectionSettingsContract.View view, INavigator navigator) {
		super(navigator);
		this.view = view;
	}
	
	public void saveConnectionSettings() {
		if (canSaveConnectionSettings()) {
			this.navigator.NavigateBack();
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.NavigateBack();
	}
	
	@Override
	public void setHost(String host) {
		
		if (true) {
			this.view.setCanSave(canSaveConnectionSettings());
		}
	}
	
	@Override
	public void setPort(String port) {
		if (true) {
			this.view.setCanSave(canSaveConnectionSettings());
		}
	}
	
	private boolean canSaveConnectionSettings() {
		return true;
	}
}
