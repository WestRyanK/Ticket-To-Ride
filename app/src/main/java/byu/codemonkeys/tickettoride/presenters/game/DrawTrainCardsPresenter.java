package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawTrainCardsContract;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/17/2017.
 */

public class DrawTrainCardsPresenter extends PresenterBase implements DrawTrainCardsContract.Presenter, Observer {
	
	DrawTrainCardsContract.View view;
	
	public DrawTrainCardsPresenter(DrawTrainCardsContract.View view,
								   INavigator navigator,
								   IDisplaysMessages messageDisplayer,
								   IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void navigateBack() {
		this.navigator.navigateBack();
	}
	
	@Override
	public void drawDeckCard() {
		ICallback drawDeckCardCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				// TODO: Implement this body
				
			}
		};
		
		// TODO: Add CanDrawDeckTrainCard method.
		modelFacade.drawDeckTrainCardAsync(drawDeckCardCallback);
	}
	
	@Override
	public void drawFaceUpCard(int cardIndex) {
		ICallback drawFaceUpCardCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				// TODO: Implement this body
				
			}
		};
		
		// TODO: Add CanDrawFaceUpTrainCard method.
		modelFacade.drawFaceUpTrainCardAsync(cardIndex, drawFaceUpCardCallback);
	}
	
	@Override
	public void loadCards() {
		if (ModelRoot.getInstance().getGame() != null) {
			this.view.setFaceUpCards(ModelRoot.getInstance().getGame().getDeck().getRevealed());
			this.view.setNumHidden(ModelRoot.getInstance().getGame().getDeck().getNumHidden());
		}
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.GAME_UPDATE) {
			loadCards();
		}
	}
}
