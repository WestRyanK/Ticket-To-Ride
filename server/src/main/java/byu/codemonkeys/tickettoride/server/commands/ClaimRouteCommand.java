package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.ClaimRouteCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.results.Result;


public class ClaimRouteCommand extends ClaimRouteCommandData implements ICommand {
    public ClaimRouteCommand(int routeID, CardType type) {
        super(routeID, type);
    }

    @Override
    public Result execute() {
        return ServerFacade.getInstance().claimRoute(getAuthToken(), getRouteID(), getCardType());
    }
}
