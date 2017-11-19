package byu.codemonkeys.tickettoride.presenters.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCardsPresenter extends PresenterBase implements DestinationCardsContract.Presenter {
	DestinationCardsContract.View view;
	
	public DestinationCardsPresenter(DestinationCardsContract.View view,
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
	public void navigateDrawDestinationCards() {
		if (ModelRoot.getInstance().getGame().getDeck().getDestinationCardsCount() > 0)
			this.navigator.navigate(PresenterEnum.DrawDestinationCards, true);
		else
			messageDisplayer.displayMessage("There are no destination cards left to draw!");
	}
	
	@Override
	public void loadDestinationCards() {
		Set<DestinationCard> cards = ModelRoot.getInstance().getGame().getSelf().getDestinations();
		List<DestinationCard> cardList = new ArrayList<>();
		cardList.addAll(cards);
		this.view.setDestinationCards(cardList);
		
		int numDestinationCards = ModelRoot.getInstance()
										   .getGame()
										   .getDeck()
										   .getDestinationCardsCount();
		this.view.setDestinationCardsInDeckCount(numDestinationCards);
	}
}
