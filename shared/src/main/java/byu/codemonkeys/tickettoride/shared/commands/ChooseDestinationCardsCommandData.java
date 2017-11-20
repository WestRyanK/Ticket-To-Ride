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
     * @param selected the destination cards the player selected.
     */
    public ChooseDestinationCardsCommandData( List<DestinationCard> selected) {
        super(CommandType.CHOOSE_DESTINATION_CARDS);
        this.selected = selected;
    }

    public ChooseDestinationCardsCommandData(String userName){
        super(CommandType.CHOOSE_DESTINATION_CARDS);
        this.userName = userName;
    }
    protected String userName;
    protected List<DestinationCard> selected;
}
