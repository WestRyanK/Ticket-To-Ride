package byu.codemonkeys.tickettoride.shared.commands;

import java.util.ArrayList;

import byu.codemonkeys.tickettoride.shared.model.Message;

/**
 * The client sends a SendMessageCommand to the server to broadcast a chat message to the other
 * users in the same game. The server sends a SendMessageCommand to the client to inform it of
 * chat messages other users in the same game have broadcast.
 */
public class SendMessageCommandData extends CommandData {
    public SendMessageCommandData(String gameID, Message message) {
        super(CommandType.SEND_MESSAGE);
        this.message = message;
        this.gameID = gameID;
    }
    protected Message message;
    protected String gameID;
}

