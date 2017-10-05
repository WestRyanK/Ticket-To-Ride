package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.IReportsErrors;
import byu.codemonkeys.tickettoride.mvpcontracts.LoginContract;

/**
 * Created by Ryan on 10/2/2017.
 */

public class LoginPresenter extends PresenterBase implements LoginContract.Presenter {
	
	private LoginContract.View view;
	private String currentUserName;
	private String currentPassword;
	
	public LoginPresenter(LoginContract.View view,
						  INavigator navigator,
						  IReportsErrors errorReporter) {
		super(navigator, errorReporter);
		this.view = view;
	}
	
	@Override
	public void login() {
		if (canLogin()) {
			if (ModelRoot.getInstance().loginUser(this.currentUserName, this.currentPassword))
				this.navigator.Navigate(PresenterEnum.Lobby, false);
			else
				this.errorReporter.displayError("Unable to login");
		}
	}
	
	@Override
	public void navigateRegisterUser() {
		this.navigator.Navigate(PresenterEnum.Register, true);
	}
	
	@Override
	public void navigateConnectionSettings() {
		this.navigator.Navigate(PresenterEnum.ConnectionSettings, true);
	}
	
	@Override
	public void setUsername(String username) {
		if (username != this.currentUserName) {
			this.currentUserName = username;
			this.view.setCanLogin(canLogin());
		}
	}
	
	@Override
	public void setPassword(String password) {
		if (password != this.currentPassword) {
			this.currentPassword = password;
			this.view.setCanLogin(canLogin());
		}
	}
	
	private boolean canLogin() {
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
