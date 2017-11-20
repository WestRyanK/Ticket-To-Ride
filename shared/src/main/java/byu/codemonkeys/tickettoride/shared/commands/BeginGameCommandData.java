package byu.codemonkeys.tickettoride.shared.commands;

import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * The server sends a BeginGameCommand to the client after all players have selected their initial
 * destination cards.
 */
public class BeginGameCommandData extends CommandData {
    protected final Map<String, Integer> numDestinationCards;
    
    protected final int destinationCardDeckCount;

    /**
     * Constructs a new BeginGameCommandData.
     * @param numDestinationCards the number of initial destination cards each player chose.
     * @param destinationCardDeckCount
     */
    public BeginGameCommandData(Map<String, Integer> numDestinationCards,
                                int destinationCardDeckCount) {
        super(CommandType.BEGIN_GAME);
        this.numDestinationCards = numDestinationCards;
        this.destinationCardDeckCount = destinationCardDeckCount;
    }
}
