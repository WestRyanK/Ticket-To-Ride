package byu.codemonkeys.tickettoride.presenters.home;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.home.LoginContract;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/2/2017.
 */

public class LoginPresenter extends PresenterBase implements LoginContract.Presenter {
	
	private LoginContract.View view;
	
	public LoginPresenter(LoginContract.View view,
						  INavigator navigator,
						  IDisplaysMessages messageDisplayer,
						  IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void login() {
		
		ICallback loginCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					navigator.navigate(PresenterEnum.Lobby, false);
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		if (canLogin()) {
			modelFacade.loginAsync(this.view.getUsername(), this.view.getPassword(), loginCallback);
		}
	}
	
	
	@Override
	public void navigateRegisterUser() {
		this.navigator.navigate(PresenterEnum.Register, true);
	}
	
	@Override
	public void navigateConnectionSettings() {
		this.navigator.navigate(PresenterEnum.ConnectionSettings, true);
	}
	
	@Override
	public boolean canLogin() {
		return (UserBase.isValidUsername(this.view.getUsername()) &&
				UserBase.isValidPassword(this.view.getPassword()));
	}
	
	@Override
	public void setDefaults() {
		this.view.setUsername("jacobe");
		this.view.setPassword("password");
	}
}
