package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IReportsErrors;
import byu.codemonkeys.tickettoride.mvpcontracts.RegisterContract;

/**
 * Created by Ryan on 10/2/2017.
 */

public class RegisterPresenter extends PresenterBase implements RegisterContract.Presenter {
	
	private RegisterContract.View view;
	private String currentUserName;
	private String currentPassword;
	
	public RegisterPresenter(RegisterContract.View view,
							 INavigator navigator,
							 IReportsErrors errorReporter) {
		super(navigator, errorReporter);
		this.view = view;
	}
	
	
	@Override
	public void register() {
		if (canRegister()) {
			if (ModelRoot.getInstance().registerUser(this.currentUserName, this.currentPassword))
				this.navigator.Navigate(PresenterEnum.Lobby, false);
			else
				errorReporter.displayError("Could not register user");
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.NavigateBack();
	}
	
	@Override
	public void setUsername(String username) {
		if (username != this.currentUserName) {
			this.currentUserName = username;
			this.view.setCanRegister(canRegister());
		}
	}
	
	@Override
	public void setPassword(String password) {
		if (password != this.currentPassword) {
			this.currentPassword = password;
			this.view.setCanRegister(canRegister());
		}
	}
	
	private boolean canRegister() {
		return (this.currentUserName != null &&
				!this.currentUserName.isEmpty() &&
				this.currentPassword != null &&
				!this.currentPassword.isEmpty());
	}
	
	@Override
	public void setDefaults() {
		this.view.setUsername("");
		this.view.setPassword("");
		
	}
}
