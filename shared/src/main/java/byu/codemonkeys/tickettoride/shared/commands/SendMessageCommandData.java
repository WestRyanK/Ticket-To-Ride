package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.Message;

/**
 * The client sends a SendMessageCommand to the server to broadcast a chat message to the other
 * users in the same game. The server sends a SendMessageCommand to the client to inform it of
 * chat messages other users in the same game have broadcast.
 */
public class SendMessageCommandData extends CommandData {
    public SendMessageCommandData(String sender, Message message) {
        super(CommandType.SEND_MESSAGE);
        this.message = message;
        this.sender = sender;
    }

    /**
     * Holds a message without specifying a sender. Used by the client, the server will determine
     * the sender via the AuthToken.
     * @param message
     */
    public SendMessageCommandData(Message message) {
        this(null, message);
    }

    public Message getMessage(){
        return message;
    }
    
    /**
     * The sender's username
     */
    protected String sender;
    protected Message message;
}

