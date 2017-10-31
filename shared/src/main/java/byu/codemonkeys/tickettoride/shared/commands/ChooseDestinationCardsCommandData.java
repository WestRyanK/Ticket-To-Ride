package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import java.util.ArrayList;
import java.util.List;

/**
 * The client sends a ChooseDestinationCardsCommand to the server to indicate which destination
 * cards the player selected after drawing three.
 */
public class ChooseDestinationCardsCommandData extends CommandData {
    /**
     * Constructs a new ChooseDestinationCardsCommandData
     * @param numSelected the number of destination cards the player selected. This might be used
     *                    in a future phase or it might be removed.
     * @param selected the destination cards the player selected.
     */
    public ChooseDestinationCardsCommandData(int numSelected, List<DestinationCard> selected) {
        super(CommandType.CHOOSE_DESTINATION_CARDS);
        this.numSelected = numSelected;
        this.selected = selected;
    }

    protected int numSelected;
    protected List<DestinationCard> selected;
}
