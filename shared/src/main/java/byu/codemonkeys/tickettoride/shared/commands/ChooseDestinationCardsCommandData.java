package byu.codemonkeys.tickettoride.shared.commands;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;

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

    public ChooseDestinationCardsCommandData(String userName){
        super(CommandType.CHOOSE_DESTINATION_CARDS);
        this.userName = userName;
    }
    protected String userName;
    protected int numSelected;
    protected List<DestinationCard> selected;
}
