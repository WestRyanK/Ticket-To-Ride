package byu.codemonkeys.tickettoride.shared.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Player with all the information a user has about herself.
 */
public class Self extends Player {
    private Map<TrainCard.Type, Integer> hand;
    private Set<DestinationCard> destinations;
    private Set<DestinationCard> selecting;

    public Self(String userName) {
        super(userName, Player.Type.Self);
        this.hand = new HashMap<>();
        this.destinations = new HashSet<>();
    }

    public Self(String userName, PlayerColor color) {
        super(userName, Player.Type.Self, color);
        this.hand = new HashMap<>();
        this.destinations = new HashSet<>();
    }

    public Self(String userName, Map<TrainCard.Type, Integer> hand,
                Set<DestinationCard> destinations) {
        super(userName, Player.Type.Self);
        this.hand = hand;
        this.destinations = destinations;
    }

    @Override
    public void addTrainCard(TrainCard card) {
        if (!hand.containsKey(card.getType())) {
            hand.put(card.getType(), 0);
        }

        hand.put(card.getType(), hand.get(card.getType())+ 1);
    }

    public void giveDestinationCards(Set<DestinationCard> cards) {
        this.selecting = cards;
    }

    public Set<DestinationCard> getSelecting() {
        return selecting;
    }

    /**
     * Moves the specified card from the selecting Set to the owned Set.
     * @param card a DestinationCard in the player's selecting Set.
     */
    public void select(DestinationCard card) {
        selecting.remove(card);
        destinations.add(card);
    }

    @Override
    public int getNumTrainCards() {
        int count = 0;

        for (Map.Entry<TrainCard.Type, Integer> entry : hand.entrySet()) {
            count += entry.getValue();
        }

        return count;
    }

    @Override
    public int getNumDestinationCards() {
        return destinations.size();
    }
}
