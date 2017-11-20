package byu.codemonkeys.tickettoride.shared.commands;


import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class RouteClaimedCommandData extends CommandData {
    private int routeID;
    private int numCardsUsed;
    private UserBase owner;

    public RouteClaimedCommandData(int routeID, int numCardsUsed, UserBase owner) {
        super(CommandType.ROUTE_CLAIMED);
        this.routeID = routeID;
        this.numCardsUsed = numCardsUsed;
        this.owner = owner;
    }

    public int getRouteID() {
        return routeID;
    }

    public int getNumCardsUsed() {
        return numCardsUsed;
    }

    public UserBase getOwner() {
        return owner;
    }
}
