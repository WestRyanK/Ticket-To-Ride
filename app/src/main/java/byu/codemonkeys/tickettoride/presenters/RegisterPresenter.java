package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.RegisterContract;

/**
 * Created by Ryan on 10/2/2017.
 */

public class RegisterPresenter extends PresenterBase implements RegisterContract.Presenter {
	
	private RegisterContract.View view;
	
	public RegisterPresenter(RegisterContract.View view, INavigator navigator) {
		super(navigator);
		this.view = view;
	}
	
	
	@Override
	public void register() {
		if (canRegister()) {
			this.navigator.Navigate(PresenterEnum.Lobby, false);
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.NavigateBack();
	}
	
	@Override
	public void setUsername(String username) {
		if (true) {
			this.view.setCanRegister(canRegister());
		}
	}
	
	@Override
	public void setPassword(String password) {
		if (true) {
			this.view.setCanRegister(canRegister());
		}
	}
	
	private boolean canRegister() {
		return true;
	}
}
