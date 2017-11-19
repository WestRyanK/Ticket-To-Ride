package byu.codemonkeys.tickettoride.presenters.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawDestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/21/2017.
 */

public class DrawDestinationCardsPresenter extends PresenterBase implements DrawDestinationCardsContract.Presenter {
	DrawDestinationCardsContract.View view;
	
	public DrawDestinationCardsPresenter(DrawDestinationCardsContract.View view,
										 INavigator navigator,
										 IDisplaysMessages messageDisplayer,
										 IModelFacade modelFacade,
										 IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
	}
	
	@Override
	public void acceptSelectedCards() {
		ICallback selectDestinationCardsCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		if (canAccept()) {
			navigator.navigateBack(PresenterEnum.Game);
			this.modelFacade.chooseInitialDestinationCardsAsync(this.view.getSelectedCards(),
																selectDestinationCardsCallback);
		}
	}
	
	@Override
	public void loadDestinationCards() {
		Set<DestinationCard> cards = ModelRoot.getInstance().getGame().getSelf().getSelecting();
		List<DestinationCard> cardList = new ArrayList<>();
		cardList.addAll(cards);
		this.view.setCards(cardList);
		this.view.setMinCardsCount(ModelRoot.getInstance().getGame().getMinAllowedDestinationCardsDrawn());
	}
	
	@Override
	public boolean canAccept() {
		return this.view.getSelectedCards().size() >= 2;
	}
}
