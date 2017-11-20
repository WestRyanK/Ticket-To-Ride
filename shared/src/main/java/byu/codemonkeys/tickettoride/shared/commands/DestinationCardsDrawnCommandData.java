package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a DrawDestinationCardsCommand to the server to request three destination cards
 * from which to choose.
 */
public class DestinationCardsDrawnCommandData extends CommandData {
    protected final String userName;
	
    protected final int destinationCardsInDeckCount;
    /**
     * Constructs a new DestinationCardsDrawnCommandData.
     * @param userName
     * @param destinationCardsInDeckCount
     */
    public DestinationCardsDrawnCommandData(String userName, int destinationCardsInDeckCount) {
        super(CommandType.DESTINATION_CARDS_DRAWN);
        this.userName = userName;
        this.destinationCardsInDeckCount = destinationCardsInDeckCount;
    }

    public String getUserName(){
        return this.userName;
    }
}
