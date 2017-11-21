package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCardLoader;
import byu.codemonkeys.tickettoride.shared.model.cards.IDeck;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class Deck extends byu.codemonkeys.tickettoride.shared.model.cards.Deck implements IDeck {
	// The number of each non-wild type the full deck contains
	private static final int NUM_STANDARD = 2;
	//	 12;
	// The number of locomotives the full deck contains
	private static final int NUM_WILD = 6;
	//	14;
	// The number of destination cards a player must typically draw
	private static final int NUM_DESTINATIONS_TO_DRAW = 3;
	// The maximum number of revealed cards
	private static final int MAX_REVEALED = 5;
	private static final int MAX_WILD_CARDS = 2;
	
	private Queue<TrainCard> hidden;
	private Set<TrainCard> discarded;
	private Queue<DestinationCard> destinations;
	
	public Deck() {
		super();
		
		hidden = new LinkedList<>();
		discarded = new HashSet<>();
		destinations = new LinkedList<>();
		
		List<TrainCard> shuffler = new ArrayList<>();
		
		for (CardType type : CardType.values()) {
			int amount = type == CardType.Wild ? NUM_WILD : NUM_STANDARD;
			
			for (int i = 0; i < amount; ++i) {
				shuffler.add(new TrainCard(type));
			}
		}
		
		while (!shuffler.isEmpty()) {
			Random random = new Random();
			
			hidden.add(shuffler.remove(random.nextInt(shuffler.size())));
		}
		
		reveal(5);
		// Make sure we don't put out > MAX_WILD_CARDS at the start of the game
		// We'd be getting off to a bad start that way...
		tryReshuffle();
		
		loadFromResource();
		
		this.trainCardsDeckCount = hidden.size();
		this.numDestinationCards = destinations.size();
	}
	
	/**
	 * Take the top card from the face-down draw pile.
	 *
	 * @return the drawn card or null if the pile is empty.
	 */
	@Override
	public TrainCard drawTrainCard() {
		// reshuffle in the discarded cards
		tryReplayDiscardedCards();
		return hidden.poll();
	}
	
	private void tryReplayDiscardedCards() {
		if (hidden.size() <= 1) {
			replayDiscardedCards();
		}
	}
	
	/**
	 * Check if reshuffling is necessary, and if so, perform the reshuffle
	 * @return True if the faceup cards were reshuffled
	 */
	private boolean tryReshuffle() {
		boolean mustShuffle = mustReshuffleFaceUpCards();
		boolean shuffled = mustShuffle;
		while (mustShuffle) {
			reshuffle();
			mustShuffle = mustReshuffleFaceUpCards();
		}
		return shuffled;
	}
	
	/**
	 * Discard face up cards and replenish it with cards from the deck.
	 * In the case where we try to add more wilds than MAX_WILD_CARDS,
	 * keep discarding them until we get something that isn't a wild.
	 * If there are only wilds left and we've reached MAX_WILD_CARDS,
	 * leave the face up cards with fewer than normal cards.
	 */
	private void reshuffle() {
		// discard all our face up cards
		this.discarded.addAll(this.revealed);
		this.revealed.clear();
		int wildCount = 0;
		// Fill the Faceup cards with cards from the deck
		for (int i = 0; i < MAX_REVEALED; i++) {
			TrainCard drawn = drawTrainCard();
			if (drawn != null) {
				if (drawn.getCardColor() == CardType.Wild)
					wildCount++;
				// Check if there are already too many wilds in the hand...
				if (drawn.getCardColor() != CardType.Wild || wildCount <= MAX_WILD_CARDS)
					this.revealed.add(drawn);
				else {
					// stick it in the discard instead of adding to the hand
					discarded.add(drawn);
					tryReplayDiscardedCards();
					//  and try again as long as there are nonWilds left
					if (countNonWildsInDeckAndDiscard() > 0)
						i--;
				}
			}
		}
	}
	
	@Override
	public boolean drawFaceUpTrainCard(int index) {
		TrainCard outDrawnCard = getFaceUpTrainCards().get(index);
		TrainCard replacement = drawTrainCard();
		getFaceUpTrainCards().set(index, replacement);
		boolean shuffled = tryReshuffle();
		return shuffled;
	}
	
	//  Even if there are at least 3 nonWilds, they might not ever be placed together so this doen't work
	private int countNonWildsInDeckAndDiscard() {
		int nonWilds = 0;
		//			for (TrainCard card : this.revealed) {
		//				if (card.getCardColor() != CardType.Wild)
		//					nonWilds++;
		//			}
		for (TrainCard card : this.hidden) {
			if (card != null && card.getCardColor() != CardType.Wild)
				nonWilds++;
		}
		for (TrainCard card : this.discarded) {
			if (card != null && card.getCardColor() != CardType.Wild)
				nonWilds++;
		}
		
		return nonWilds;
	}
	
	private boolean mustReshuffleFaceUpCards() {
		//		int nonWilds = countNonWildsInDeckFaceUpAndDiscard();
		int count = 0;
		for (TrainCard card : this.revealed) {
			if (card != null && card.getCardColor() == CardType.Wild)
				count++;
		}
		boolean tooManyWilds = count > MAX_WILD_CARDS;
		//		boolean sufficientNonWilds = nonWilds >= MAX_REVEALED - MAX_WILD_CARDS;
		
		//		return tooManyWilds && sufficientNonWilds;
		return tooManyWilds;
	}
	
	private void replayDiscardedCards() {
		// Try to remove null from the set so we don't add null cards to our deck and faceup
		this.discarded.remove(null);
		hidden.addAll(this.discarded);
		this.discarded.clear();
	}
	
	// TODO: handle the destination cards pile being depleted.
	
	/**
	 * Take the top cards from the destination cards pile.
	 *
	 * @return the drawn cards.
	 */
	@Override
	public Set<DestinationCard> drawDestinationCards() {
		Set<DestinationCard> drawn = new HashSet<>();
		
		for (int i = 0; i < NUM_DESTINATIONS_TO_DRAW; ++i) {
			if (destinations.size() > 0)
				drawn.add(destinations.poll());
		}
		
		return drawn;
	}
	
	public void returnDestinationCardsToDeck(Collection<DestinationCard> cards) {
		for (DestinationCard card : cards) {
			this.destinations.add(card);
		}
	}
	
	/**
	 * Reveals the specified number of cards from the top of the draw pile.
	 *
	 * @param number the number of cards to reveal.
	 * @throws IllegalStateException revealing the specified number of cards would cause there to be
	 *                               too many revealed cards.
	 */
	public void reveal(int number) throws IllegalStateException {
		if ((revealed.size() + number) > MAX_REVEALED) {
			throw new IllegalStateException();
		}
		
		for (int i = 0; i < number; ++i) {
			revealed.add(drawTrainCard());
		}
	}
	
	@Override
	public int getTrainCardsDeckCount() {
		return hidden.size();
	}
	
	@Override
	public int getDestinationCardsCount() {
		return destinations.size();
	}
	
	@Override
	public void discard(Map<CardType, Integer> toDiscard) {
		for (Map.Entry<CardType, Integer> entry : toDiscard.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				discarded.add(new TrainCard(entry.getKey()));
			}
		}
		tryReplayDiscardedCards();
	}
	
	private void loadFromResource() {
		List<DestinationCard> destinations = DestinationCardLoader.getInstance()
																  .loadDestinationCardsFromResources();
		
		Collections.shuffle(destinations);
		
		for (DestinationCard destination : destinations) {
			this.destinations.add(destination);
		}
	}
}
