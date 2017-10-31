package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.SendMessageCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.results.Result;


public class SendMessageCommand extends SendMessageCommandData implements IClientCommand {
    public SendMessageCommand(Message message) {
        super(message);
    }

    @Override
    public void execute() {
        ModelRoot.getInstance().addMessage(message);
    }
}
