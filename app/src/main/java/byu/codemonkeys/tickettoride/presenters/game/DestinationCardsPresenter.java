package byu.codemonkeys.tickettoride.presenters.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.cards.Deck;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCardsPresenter extends PresenterBase implements DestinationCardsContract.Presenter, Observer {
	DestinationCardsContract.View view;
	
	public DestinationCardsPresenter(DestinationCardsContract.View view,
									 INavigator navigator,
									 IDisplaysMessages messageDisplayer,
									 IModelFacade modelFacade,
									 IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
		modelFacade.addObserver(this);
	}
	
	@Override
	public void navigateBack() {
		this.navigator.navigateBack();
	}
	
	@Override
	public void navigateDrawDestinationCards() {
		if (ModelRoot.getInstance().getGame().getTurn().canDrawDestinationCards()) {
			if (ModelRoot.getInstance().getGame().getDeck().getDestinationCardsCount() > 0) {
				ICallback drawDestinationCardsCallback = new ICallback() {
					@Override
					public void callback(Result result) {
						navigator.navigate(PresenterEnum.DrawDestinationCards, true);
					}
				};
				modelFacade.drawDestinationCardsAsync(drawDestinationCardsCallback);
			} else {
				messageDisplayer.displayMessage("There are no destination cards left to draw");
			}
		} else {
			messageDisplayer.displayMessage("You can't draw a destination card right now");
		}
	}
	
	@Override
	public void loadDestinationCards() {
		Set<DestinationCard> cards = ModelRoot.getInstance().getGame().getSelf().getDestinations();
		List<DestinationCard> cardList = new ArrayList<>();
		cardList.addAll(cards);
		this.view.setDestinationCards(cardList);
		
		loadDestinationCardsDeck();
	}
	
	private void loadDestinationCardsDeck() {
		
		int numDestinationCards = ModelRoot.getInstance()
										   .getGame()
										   .getDeck()
										   .getDestinationCardsCount();
		this.view.setDestinationCardsInDeckCount(numDestinationCards);
		this.view.setCanDrawDestinationCards(numDestinationCards > 0);
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == Deck.DESTINATION_CARDS_UPDATE) {
			loadDestinationCardsDeck();
		}
	}
}
