package byu.codemonkeys.tickettoride.shared.commands;

import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * The server sends a BeginGameCommand to the client after all players have selected their initial
 * destination cards.
 */
public class BeginGameCommandData extends CommandData {
    protected Map<UserBase, Integer> numDestinationCards;

    /**
     * Constructs a new BeginGameCommandData.
     * @param numDestinationCards the number of initial destination cards each player chose.
     */
    public BeginGameCommandData(Map<UserBase, Integer> numDestinationCards) {
        super(CommandType.BEGIN_GAME);
        this.numDestinationCards = numDestinationCards;
    }
}
