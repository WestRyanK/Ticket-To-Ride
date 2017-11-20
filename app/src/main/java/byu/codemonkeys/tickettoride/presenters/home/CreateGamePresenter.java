package byu.codemonkeys.tickettoride.presenters.home;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.home.CreateGameContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/2/2017.
 */

public class CreateGamePresenter extends PresenterBase implements CreateGameContract.Presenter {
	
	private CreateGameContract.View view;
	
	public CreateGamePresenter(CreateGameContract.View view,
							   INavigator navigator,
							   IDisplaysMessages messageDisplayer,
							   IModelFacade modelFacade,
							   IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
	}
	
	public void createGame() {
		
		ICallback createGameCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					navigator.navigateBack();
					navigator.navigate(PresenterEnum.WaitingRoom, true);
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		
		if (canCreateGame()) {
			modelFacade.createGameAsync(this.view.getGameName(), createGameCallback);
		}
	}
	
	@Override
	public void cancel() {
		this.navigator.navigateBack();
	}
	
	@Override
	public boolean canCreateGame() {
		return GameBase.isValidGameName(this.view.getGameName());
	}
	
	@Override
	public void setDefaults() {
		this.view.setGameName("MnkyBusiness");
	}
}
