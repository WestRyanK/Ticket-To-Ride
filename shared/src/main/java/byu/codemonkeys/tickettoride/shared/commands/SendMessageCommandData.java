package byu.codemonkeys.tickettoride.shared.commands;

import java.util.ArrayList;

import byu.codemonkeys.tickettoride.shared.model.Message;

/**
 * Created by meganrich on 10/18/17.
 */

public class SendMessageCommandData extends CommandData {
    public SendMessageCommandData(Message message) {
        super(CommandType.SEND_MESSAGE);
        this.message = message;
    }
    protected Message message;
}

