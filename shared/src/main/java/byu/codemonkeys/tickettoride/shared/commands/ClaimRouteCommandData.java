package byu.codemonkeys.tickettoride.shared.commands;


import byu.codemonkeys.tickettoride.shared.model.cards.CardType;

public class ClaimRouteCommandData extends CommandData {
    private int routeID;
    private CardType type;

    public ClaimRouteCommandData(int routeID, CardType type) {
        super(CommandType.CLAIM_ROUTE);
        this.routeID = routeID;
        this.type = type;
    }

    public int getRouteID() {
        return routeID;
    }

    public CardType getCardType() {
        return type;
    }
}
