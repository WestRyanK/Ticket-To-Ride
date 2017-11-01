package byu.codemonkeys.tickettoride.shared.results;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;

/**
 * Created by meganrich on 10/18/17.
 */

public class DestinationCardResult extends Result {
    private List<DestinationCard> cards;
    public DestinationCardResult(List<DestinationCard> cards) {
        super();
        for(DestinationCard card: cards)this.cards.add(card);
    }

    public DestinationCardResult(String errorMessage) {
        super(errorMessage);
    }

    public List<DestinationCard> getDestinationCards() {
        return this.cards;
    }
}
