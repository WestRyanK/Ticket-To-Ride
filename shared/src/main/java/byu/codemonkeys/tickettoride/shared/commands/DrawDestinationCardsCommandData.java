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
     */
    public DrawDestinationCardsCommandData() {
        super(CommandType.DRAW_DESTINATION_CARDS);
    }
}
