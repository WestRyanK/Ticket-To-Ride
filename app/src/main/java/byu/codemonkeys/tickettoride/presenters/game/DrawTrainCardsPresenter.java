package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
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
								   IModelFacade modelFacade,
								   IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
	}
	
	@Override
	public void navigateBack() {
		this.navigator.navigateBack();
	}
	
	@Override
	public void drawDeckCard() {
		if (ModelRoot.getInstance().getGame().getTurn().canDrawTrainCard()) {
			modelFacade.drawDeckTrainCardAsync(null);
		}
		messageDisplayer.displayMessage("Cannot draw Train Card at this time");
	}
	
	@Override
	public void drawFaceUpCard(int cardIndex) {

		if (ModelRoot.getInstance().getGame().getTurn().canDrawTrainCard()) {
			//TODO: check if the face up card is a wild card and call canDrawWildTrainCard if it is
			modelFacade.drawFaceUpTrainCardAsync(cardIndex, null);
		}
		messageDisplayer.displayMessage("Cannot draw Train Card at this time");
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
