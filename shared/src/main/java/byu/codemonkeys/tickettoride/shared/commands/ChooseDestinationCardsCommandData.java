package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meganrich on 10/18/17.
 */

public class ChooseDestinationCardsCommandData extends CommandData {
    public ChooseDestinationCardsCommandData(int numSelected, List<DestinationCard> selected, String gameID) {
        super(CommandType.CHOOSE_DESTINATION_CARDS);
        this.numSelected = numSelected;
        this.gameID = gameID;
        this.selected = selected;
    }

    protected int numSelected;
    protected List<DestinationCard> selected;
    protected String gameID;
}
