package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.DestinationCardsDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.DrawDestinationCardsCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by meganrich on 11/17/17.
 */

public class DrawDestinationCardsCommand extends DrawDestinationCardsCommandData implements ICommand{
    public DrawDestinationCardsCommand() {
        super();
    }

    @Override
    public Result execute() {
        return ServerFacade.getInstance().drawDestinationCards(getAuthToken());
    }
}
