package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.SendMessageCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.results.Result;


public class SendMessageCommand extends SendMessageCommandData implements ICommand {

    public SendMessageCommand(String sender, Message message) {
        super(sender, message);
    }

    @Override
    public Result execute() {
        return ServerFacade.getInstance().sendMessage(this.getAuthToken(), this.message);
    }
}
