package byu.codemonkeys.tickettoride.presenters;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.mvpcontracts.CreateGameContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
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
							   IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	public void createGame() {
		
		ICallback createGameCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					navigator.NavigateBack();
					navigator.Navigate(PresenterEnum.WaitingRoom, true);
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
		this.navigator.NavigateBack();
	}
	
	@Override
	public boolean canCreateGame() {
		return GameBase.isValidGameName(this.view.getGameName());
	}
	
	@Override
	public void setDefaults() {
		this.view.setGameName("");
	}
}
