package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import java.util.ArrayList;
/**
 * Created by meganrich on 10/18/17.
 */

public class DrawDestinationCardsCommandData extends CommandData {
    public DrawDestinationCardsCommandData(String gameID) {
        super(CommandType.DRAW_DESTINATION_CARDS);
        this.gameID = gameID;
    }

    protected String gameID;
}
