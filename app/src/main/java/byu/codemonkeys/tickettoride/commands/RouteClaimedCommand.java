package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.RouteClaimedCommandData;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.map.Route;


public class RouteClaimedCommand extends RouteClaimedCommandData implements IClientCommand {
    public RouteClaimedCommand(int routeID, int numCardsUsed, UserBase owner) {
        super(routeID, numCardsUsed, owner);
    }

    @Override
    public void execute() {
        ModelRoot modelRoot = ModelRoot.getInstance();
        Player player = modelRoot.getGame().getPlayer(this.getOwner());
        player.removeTrainCards(getNumCardsUsed());
        modelRoot.getGame().getMap().claimRoute(getRouteID(), player);
    }

    @Override
    public String toString() {
        Route route = ModelRoot.getInstance().getGame().getMap().getRoute(getRouteID());
        return String.format("[%s Route from %s to %s was Claimed by %s]", route.getRouteType(), route.getSource(),
                route.getDestination(), getOwner().getUsername());
    }
}
