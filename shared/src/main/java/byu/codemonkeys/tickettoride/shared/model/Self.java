package byu.codemonkeys.tickettoride.shared.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

/**
 * A Player with all the information a user has about themself.
 */
public class Self extends Player {
	/**
	 * Represents all of the train cards that the player has in their hand.
	 */
	private Map<CardType, Integer> hand;
	
	/**
	 * Returns a set of the destination cards that the Player
	 * @return
	 */
	public Set<DestinationCard> getDestinations() {
		return destinations;
	}
	
	private Set<DestinationCard> destinations;
	private Set<DestinationCard> selecting;
	private CardType activeTrainCardType;
	public static final String ACTIVE_CARD_TYPE_UPDATE = "activeCardTypeUpdate";

	public Self(String userName) {
		super(userName, Player.Type.Self);
		this.destinations = new HashSet<>();
		initHand();
	}

	public Self(String userName, PlayerColor color) {
		super(userName, Player.Type.Self, color);
		this.destinations = new HashSet<>();
		this.selecting = new HashSet<>();
		initHand();
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

	private void initHand() {
		hand = new HashMap<>();
		for (CardType type : CardType.values()) {
			hand.put(type, 0);
		}
	}

	public Map<CardType, Integer> getHand() {
	    return hand;
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
		setChanged();
		notifyObservers(PLAYER_TRAIN_CARDS_UPDATE);
	}

	public void setHand(Collection<TrainCard> cards) {
    	initHand();
    	for (TrainCard card : cards) {
			hand.put(card.getCardColor(), hand.get(card.getCardColor()) + 1);
		}

		setChanged();
		notifyObservers(PLAYER_TRAIN_CARDS_UPDATE);
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

	public void discardTrainCards(Map<CardType, Integer> toDiscard) {
    	if (toDiscard != null) {
			for (Map.Entry<CardType, Integer> entry : toDiscard.entrySet()) {
				hand.put(entry.getKey(), hand.get(entry.getKey()) - entry.getValue());
			}
		}
		setChanged();
		notifyObservers(PLAYER_TRAIN_CARDS_UPDATE);
	}

	@Override
	public int getNumDestinationCards() {
		return destinations.size();
	}
	
	public CardType getActiveTrainCardType() {
		return activeTrainCardType;
	}
	
	public void setActiveTrainCardType(CardType activeTrainCardType) {
		this.activeTrainCardType = activeTrainCardType;
		setChanged();
		notifyObservers(ACTIVE_CARD_TYPE_UPDATE);
	}
}
