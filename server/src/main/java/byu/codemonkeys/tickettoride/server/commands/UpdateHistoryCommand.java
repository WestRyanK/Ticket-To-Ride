package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.UpdateHistoryCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;


public class UpdateHistoryCommand extends UpdateHistoryCommandData implements ICommand {
    public UpdateHistoryCommand(String authToken, int lastReadCommandPosition) {
        super(lastReadCommandPosition);
        this.setAuthToken(authToken);
    }

    @Override
    public Result execute() {
        return ServerFacade.getInstance().updateHistory(getAuthToken(), this.lastSeenCommandIndex);
    }
}
