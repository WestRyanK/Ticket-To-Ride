package byu.codemonkeys.tickettoride.presenters.home;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
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
							 IModelFacade modelFacade,
							 IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
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
		else
		{
			if (!UserBase.isValidUsername(this.view.getUsername()))
				messageDisplayer.displayMessage("Username must be between 6-12 characters");
			if (!UserBase.isValidPassword(this.view.getPassword()))
				messageDisplayer.displayMessage("Password must be between 8-20 characters");
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
		this.view.setUsername("codemonkey");
		this.view.setPassword("password");
		
	}
}
