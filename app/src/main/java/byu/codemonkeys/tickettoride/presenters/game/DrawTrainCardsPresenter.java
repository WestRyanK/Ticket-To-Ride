package byu.codemonkeys.tickettoride.presenters.game;

import android.view.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawTrainCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

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
		messageDisplayer.displayMessage("Drew from deck");
		
		List<TrainCard> cards = new ArrayList<>();
		Random rand = new Random();
		cards.add(new TrainCard(CardType.values()[rand.nextInt(9)]));
		cards.add(new TrainCard(CardType.values()[rand.nextInt(9)]));
		cards.add(new TrainCard(CardType.values()[rand.nextInt(9)]));
		cards.add(new TrainCard(CardType.values()[rand.nextInt(9)]));
		cards.add(new TrainCard(CardType.values()[rand.nextInt(9)]));
		
		this.view.setFaceUpCards(cards);
	}
	
	@Override
	public void drawFaceUpCard(int cardIndex) {
		messageDisplayer.displayMessage("Drew card #" + String.valueOf(cardIndex));
	}

	@Override
	public void loadCards() {
		if (ModelRoot.getInstance().getGame() != null) {
			this.view.setFaceUpCards(ModelRoot.getInstance().getGame().getDeck().getRevealed());
		}
	}

	@Override
	public void update(Observable observable, Object o) {
		if (o == ModelFacade.GAME_UPDATE) {
			loadCards();
		}
	}
}
