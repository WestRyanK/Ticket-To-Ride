package byu.codemonkeys.tickettoride.shared.commands;


import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class RouteClaimedCommandData extends CommandData {
    private int routeID;
    private UserBase owner;

    public RouteClaimedCommandData(int routeID, UserBase owner) {
        super(CommandType.ROUTE_CLAIMED);
        this.routeID = routeID;
        this.owner = owner;
    }

    public int getRouteID() {
        return routeID;
    }

    public UserBase getOwner() {
        return owner;
    }
}
