package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.IDeck;
import byu.codemonkeys.tickettoride.shared.model.TrainCard;

public class Deck extends byu.codemonkeys.tickettoride.shared.model.Deck implements IDeck {
    // The number of each non-wild type the full deck contains
    private static int NUM_STANDARD = 12;
    // The number of locomotives the full deck contains
    private static int NUM_LOCOMOTIVE = 14;
    // The number of destination cards a player must typically draw
    private static int NUM_DESTINATIONS_TO_DRAW = 3;
    // The maximum number of revealed cards
    private static int MAX_REVEALED = 5;

    private Queue<TrainCard> hidden;
    private Set<TrainCard> discarded;
    private Queue<DestinationCard> destinations;

    public Deck() {
        super();

        hidden = new LinkedList<>();
        discarded = new HashSet<>();
        destinations = new LinkedList<>();

        List<TrainCard> shuffler = new ArrayList<>();

        for (TrainCard.Type type : TrainCard.Type.values()) {
            int amount = type == TrainCard.Type.Locomotive ? NUM_LOCOMOTIVE : NUM_STANDARD;

            for (int i = 0; i < amount; ++i) {
                shuffler.add(new TrainCard(type));
            }
        }

        while (!shuffler.isEmpty()) {
            Random random = new Random();

            hidden.add(shuffler.remove(random.nextInt(shuffler.size())));
        }

        reveal(5);

        loadFromResource();

        this.numHidden = hidden.size();
        this.numDestinationCards = destinations.size();
    }

    // TODO: handle the draw pile being depleted.
    /**
     * Take the top card from the face-down draw pile.
     * @return the drawn card.
     */
    @Override
    public TrainCard drawTrainCard() {
        return hidden.poll();
    }

    // TODO: handle the destination cards pile being depleted.
    /**
     * Take the top cards from the destination cards pile.
     * @return the drawn cards.
     */
    @Override
    public Set<DestinationCard> drawDestinationCards() {
        Set<DestinationCard> drawn = new HashSet<>();

        for (int i = 0; i < NUM_DESTINATIONS_TO_DRAW; ++i) {
            drawn.add(destinations.poll());
        }

        return drawn;
    }

    /**
     * Reveals the specified number of cards from the top of the draw pile.
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
    public int getNumHidden() {
        return hidden.size();
    }

    @Override
    public int getNumDestinationCards() {
        return destinations.size();
    }

    private void loadFromResource() {

    }
}
