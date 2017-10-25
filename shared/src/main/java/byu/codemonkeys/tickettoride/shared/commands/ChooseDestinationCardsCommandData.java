package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import java.util.ArrayList;

/**
 * Created by meganrich on 10/18/17.
 */

public class ChooseDestinationCardsCommandData extends CommandData {
    public ChooseDestinationCardsCommandData(int numSelected, ArrayList<DestinationCard> selected, String gameID) {
        super(CommandType.CHOOSE_DESTINATION_CARDS);
        this.numSelected = numSelected;
        this.gameID = gameID;
        for(DestinationCard card: selected) this.selected.add(card);
    }

    protected int numSelected;
    protected ArrayList<DestinationCard> selected;
    protected String gameID;
}
