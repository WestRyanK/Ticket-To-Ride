package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.RouteClaimedCommandData;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.UserBase;


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
}
