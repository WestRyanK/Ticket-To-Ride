package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.LoginContract;

/**
 * Created by Ryan on 10/2/2017.
 */

public class LoginPresenter extends PresenterBase implements LoginContract.Presenter {
	
	private LoginContract.View view;
	
	public LoginPresenter(LoginContract.View view, INavigator navigator) {
		super(navigator);
		this.view = view;
	}
	
	@Override
	public void login() {
		if (canLogin()) {
			this.navigator.Navigate(PresenterEnum.Lobby, false);
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
		if (true) {
			this.view.setCanLogin(canLogin());
		}
	}
	
	@Override
	public void setPassword(String password) {
		if (true) {
			this.view.setCanLogin(canLogin());
		}
	}
	
	private boolean canLogin() {
		return true;
	}
}
