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

    private Queue<TrainCard> hidden;
    private Set<TrainCard> discarded;
    private Queue<DestinationCard> destinations;

    public Deck() {
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

    @Override
    public int getNumHidden() {
        return hidden.size();
    }

    @Override
    public int getNumDestinationCards() {
        return destinations.size();
    }

    private void loadFromResource() {
        // Loads the destination cards from a file
    }
}
