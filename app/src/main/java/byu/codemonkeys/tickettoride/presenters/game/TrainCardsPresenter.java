package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.IMediaPlayer;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.mvpcontracts.game.TrainCardsContract;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;

public class TrainCardsPresenter extends PresenterBase implements TrainCardsContract.Presenter, Observer {
    private TrainCardsContract.View view;

    public TrainCardsPresenter(TrainCardsContract.View view,
                               INavigator navigator,
                               IDisplaysMessages messageDisplayer,
                               IModelFacade modelFacade,
                               IMediaPlayer mediaPlayer) {
        super(navigator, messageDisplayer, modelFacade, mediaPlayer);
        this.view = view;
        this.modelFacade.addObserver(this);
    }

    @Override
    public void loadHand() {
        if (ModelRoot.getInstance().getGame() != null) {
            this.view.setHand(ModelRoot.getInstance().getGame().getSelf().getHand());
        }
    }
    
    @Override
    public void setActiveCardType(CardType type) {
        ModelRoot.getInstance().getGame().getSelf().setActiveTrainCardType(type);
    }
    
    @Override
    public void update(Observable observable, Object o) {
        if (o == ModelFacade.GAME_UPDATE) {
            // This will be changed to use ModelFacade
            loadHand();
        } else if (o == Player.PLAYER_TRAIN_CARDS_UPDATE) {
            loadHand();
        }
        else if (o == Self.ACTIVE_CARD_TYPE_UPDATE){
            view.setActiveCardType(ModelRoot.getInstance().getGame().getSelf().getActiveTrainCardType());
        }
    }
}
