package byu.codemonkeys.tickettoride.mvpcontracts.game;

import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.cards.CardType;

public interface TrainCardsContract {
    interface View {
        public void setHand(Map<CardType, Integer> hand);
    }

    interface Presenter {
        public void loadHand();
    }
}
