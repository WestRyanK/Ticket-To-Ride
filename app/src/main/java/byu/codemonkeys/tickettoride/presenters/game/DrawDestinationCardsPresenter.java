package byu.codemonkeys.tickettoride.presenters.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawDestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;

/**
 * Created by Ryan on 10/21/2017.
 */

public class DrawDestinationCardsPresenter extends PresenterBase implements DrawDestinationCardsContract.Presenter {
	DrawDestinationCardsContract.View view;
	
	public DrawDestinationCardsPresenter(DrawDestinationCardsContract.View view,
										 INavigator navigator,
										 IDisplaysMessages messageDisplayer,
										 IModelFacade modelFacade) {
		super(navigator, messageDisplayer, modelFacade);
		this.view = view;
	}
	
	@Override
	public void acceptSelectedCards() {
		navigator.navigateBack(PresenterEnum.Game);
	}
	
	@Override
	public void loadDestinationCards() {
		Set<DestinationCard> cards = ModelRoot.getInstance().getGame().getSelf().getSelecting();
		List<DestinationCard> cardList = new ArrayList<>();
		cardList.addAll(cards);
		this.view.setCards(cardList);
	}
}
