package byu.codemonkeys.tickettoride.presenters.home;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.home.RegisterContract;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/2/2017.
 */

public class RegisterPresenter extends PresenterBase implements RegisterContract.Presenter {
	
	private RegisterContract.View view;
	
	public RegisterPresenter(RegisterContract.View view,
							 INavigator navigator,
							 IDisplaysMessages messageDisplayer,
							 IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	
	@Override
	public void register() {
		ICallback registerCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					navigator.navigateBack();
					navigator.navigate(PresenterEnum.Lobby, false);
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		
		if (canRegister()) {
			modelFacade.registerAsync(this.view.getUsername(),
									  this.view.getPassword(),
									  registerCallback);
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.navigateBack();
	}
	
	@Override
	public boolean canRegister() {
		return (UserBase.isValidUsername(this.view.getUsername()) &&
				UserBase.isValidPassword(this.view.getPassword()));
	}
	
	@Override
	public void setDefaults() {
		this.view.setUsername("westryank");
		this.view.setPassword("password");
		
	}
}
