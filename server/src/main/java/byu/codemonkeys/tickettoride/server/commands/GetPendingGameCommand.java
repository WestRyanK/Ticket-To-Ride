package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.GetPendingGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;


public class GetPendingGameCommand extends GetPendingGameCommandData implements ICommand {
    @Override
    public Result execute() {
        return ServerFacade.getInstance().getPendingGame(getAuthToken());
    }
}
