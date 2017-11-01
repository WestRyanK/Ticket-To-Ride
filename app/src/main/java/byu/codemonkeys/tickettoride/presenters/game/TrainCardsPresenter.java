package byu.codemonkeys.tickettoride.presenters.game;

import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.IModelFacade;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.mvpcontracts.IDisplaysMessages;
import byu.codemonkeys.tickettoride.mvpcontracts.INavigator;
import byu.codemonkeys.tickettoride.presenters.PresenterBase;
import byu.codemonkeys.tickettoride.mvpcontracts.game.TrainCardsContract;

public class TrainCardsPresenter extends PresenterBase implements TrainCardsContract.Presenter, Observer {
    private TrainCardsContract.View view;

    public TrainCardsPresenter(TrainCardsContract.View view,
                               INavigator navigator,
                               IDisplaysMessages messageDisplayer,
                               IModelFacade modelFacade) {
        super(navigator, messageDisplayer, modelFacade);
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
    public void update(Observable observable, Object o) {
        if (o == ModelFacade.GAME_UPDATE) {
            // This will be changed to use ModelFacade
            loadHand();
        } else if (o == ModelFacade.PLAYER_TRAIN_CARDS_UPDATE) {
            loadHand();
        }
    }
}
