package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a DrawDestinationCardsCommand to the server to request three destination cards
 * from which to choose.
 */
public class DrawDestinationCardsCommandData extends CommandData {
    private String userName;
    /**
     * Constructs a new DrawDestinationCardsCommandData.
     */
    public DrawDestinationCardsCommandData() {
        super(CommandType.DRAW_DESTINATION_CARDS);
    }

    public DrawDestinationCardsCommandData(String userName) {
        super(CommandType.DRAW_DESTINATION_CARDS);
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }
}
