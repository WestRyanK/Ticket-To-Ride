package byu.codemonkeys.tickettoride.shared.results;


import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.cards.CardType;

public class ClaimRouteResult extends Result {

    Map<CardType, Integer> cardsToRemove;
    int numTrainsToRemove;

    public ClaimRouteResult(Map<CardType, Integer> cardsToRemove, int numTrainsToRemove) {
        super();
        this.cardsToRemove = cardsToRemove;
        this.numTrainsToRemove = numTrainsToRemove;
    }

    public ClaimRouteResult(String errorMessage) {
        super(errorMessage);
    }

    public Map<CardType, Integer> getCardsToRemove() {
        return cardsToRemove;
    }

    public int getNumTrainsToRemove() {
        return numTrainsToRemove;
    }
}
