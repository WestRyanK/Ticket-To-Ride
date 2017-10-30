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
     * @param gameID this will be removed in a future merge request. It is unnecessary because the
     *               auth token will be used to determine the game ID.
     */
    public ChooseDestinationCardsCommandData(int numSelected, List<DestinationCard> selected, String gameID) {
        super(CommandType.CHOOSE_DESTINATION_CARDS);
        this.numSelected = numSelected;
        this.selected = selected;
    }

    protected int numSelected;
    protected List<DestinationCard> selected;
}
