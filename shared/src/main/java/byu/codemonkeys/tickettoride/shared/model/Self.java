package byu.codemonkeys.tickettoride.shared.model;

import java.util.Map;
import java.util.Set;

/**
 * This class is especially incomplete.
 */
public class Self extends Player {
    private Map<CardType, Integer> hand;
    private Set<DestinationCard> destinations;

    public Self(String userName, Map<CardType, Integer> hand,
                Set<DestinationCard> destinations) {
        super(userName, Player.Type.Self);
        this.hand = hand;
        this.destinations = destinations;
    }
}
