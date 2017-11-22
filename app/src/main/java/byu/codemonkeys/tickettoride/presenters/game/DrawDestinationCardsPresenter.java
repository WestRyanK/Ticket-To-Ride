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
import byu.codemonkeys.tickettoride.mvpcontracts.game.CutScenes;
import byu.codemonkeys.tickettoride.mvpcontracts.game.DrawDestinationCardsContract;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.presenters.PresenterEnum;
import byu.codemonkeys.tickettoride.shared.model.ActiveGame;
import byu.codemonkeys.tickettoride.shared.model.cards.Deck;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 10/21/2017.
 */

public class DrawDestinationCardsPresenter extends PresenterBase implements DrawDestinationCardsContract.Presenter, Observer {
	DrawDestinationCardsContract.View view;
	
	public DrawDestinationCardsPresenter(DrawDestinationCardsContract.View view,
										 INavigator navigator,
										 IDisplaysMessages messageDisplayer,
										 IModelFacade modelFacade,
										 IMediaPlayer mediaPlayer) {
		super(navigator, messageDisplayer, modelFacade, mediaPlayer);
		this.view = view;
		modelFacade.addObserver(this);
	}
	
	@Override
	public void acceptSelectedCards() {
		ICallback selectDestinationCardsCallback = new ICallback() {
			@Override
			public void callback(Result result) {
				if (result.isSuccessful()) {
					if (ModelRoot.getInstance().getGame().getMinAllowedDestinationCardsDrawn() ==
							ActiveGame.INITIAL_MIN_ALLOWED_DESTINATION_CARDS_DRAWN)
						//							ModelRoot.getInstance().getGame().setStarted(true);
						mediaPlayer.playCutScene(CutScenes.openingSequence);
				} else {
					messageDisplayer.displayMessage(result.getErrorMessage());
				}
			}
		};
		if (canAccept()) {
			navigator.navigateBack(PresenterEnum.Game);
			this.modelFacade.chooseDestinationCardsAsync(this.view.getSelectedCards(),
														 selectDestinationCardsCallback);
		}
	}
	
	@Override
	public void loadDestinationCards() {
		Set<DestinationCard> cards = ModelRoot.getInstance().getGame().getSelf().getSelecting();
		List<DestinationCard> cardList = new ArrayList<>();
		cardList.addAll(cards);
		this.view.setCards(cardList);
		this.view.setMinCardsCount(ModelRoot.getInstance()
											.getGame()
											.getMinAllowedDestinationCardsDrawn());
		this.view.setCanContinue(canAccept());
	}
	
	@Override
	public boolean canAccept() {
		if (this.view != null && this.view.getSelectedCards() != null)
		return this.view.getSelectedCards().size() >=
				ModelRoot.getInstance().getGame().getMinAllowedDestinationCardsDrawn();
		else
			return false;
	}
	
	@Override
	public void selectionChanged() {
		this.view.setCanContinue(this.canAccept());
	}
	
	@Override
	public void update(Observable observable, Object o) {
		if (o == Deck.DESTINATION_CARDS_UPDATE) {
			this.loadDestinationCards();
		}
	}
}
