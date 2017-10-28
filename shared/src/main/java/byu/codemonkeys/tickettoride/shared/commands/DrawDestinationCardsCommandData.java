package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import java.util.ArrayList;

/**
 * The client sends a DrawDestinationCardsCommand to the server to request three destination cards
 * from which to choose.
 */
public class DrawDestinationCardsCommandData extends CommandData {
    /**
     * Constructs a new DrawDestinationCardsCommandData.
     * @param gameID this will be removed in a future merge request. It is unnecessary because the
     *               auth token will be used to determine the game ID.
     */
    public DrawDestinationCardsCommandData(String gameID) {
        super(CommandType.DRAW_DESTINATION_CARDS);
    }
}
