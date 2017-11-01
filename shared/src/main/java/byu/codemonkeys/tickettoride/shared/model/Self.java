package byu.codemonkeys.tickettoride.shared.model;

import com.sun.media.jfxmedia.events.PlayerStateEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

/**
 * A Player with all the information a user has about herself.
 */
public class Self extends Player {
	private Map<CardType, Integer> hand;
	
	public Set<DestinationCard> getDestinations() {
		return destinations;
	}
	
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
		this.selecting = new HashSet<>();
	}

	public Self(String userName, Map<CardType, Integer> hand, Set<DestinationCard> destinations) {
		super(userName, Player.Type.Self);
		this.hand = hand;
		this.destinations = destinations;
	}
	
	public Self(Player player){
		super(player.userName, Type.Self);
		Self playerSelf = (Self) player;
		this.hand = playerSelf.hand;
		this.destinations = playerSelf.destinations;
		this.selecting = ((Self) player).getSelecting();
		this.color = player.color;
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
//		setChanged();
//		notifyObservers(PLAYER_DESTINATION_CARDS_UPDATE);
    }

	@Override
	public void addTrainCard(TrainCard card) {
		if (!hand.containsKey(card.getCardColor())) {
			hand.put(card.getCardColor(), 0);
		}

		hand.put(card.getCardColor(), hand.get(card.getCardColor()) + 1);
//		setChanged();
//		notifyObservers(PLAYER_TRAIN_CARDS_UPDATE);
	}

	public void giveDestinationCards(Set<DestinationCard> cards) {
		this.selecting = cards;
	}

	@Override
	public int getNumTrainCards() {
		int count = 0;

		for (Map.Entry<CardType, Integer> entry : hand.entrySet()) {
			count += entry.getValue();
		}

		return count;
	}

	@Override
	public int getNumDestinationCards() {
		return destinations.size();
	}
}
