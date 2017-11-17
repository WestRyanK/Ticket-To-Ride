package byu.codemonkeys.tickettoride.shared.results;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;

public class DestinationCardResult extends Result {

    private final List<DestinationCard> cards;

    public DestinationCardResult(List<DestinationCard> cards) {
        super();
        this.cards = cards;
    }

    public DestinationCardResult(Set<DestinationCard> cards) {
        super();
        this.cards = new ArrayList<>();
        this.cards.addAll(cards);
    }

    public DestinationCardResult(String errorMessage) {
        super(errorMessage);
        this.cards = null;
    }

    public List<DestinationCard> getDestinationCards() {
        return this.cards;
    }
}
