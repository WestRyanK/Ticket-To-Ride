package byu.codemonkeys.tickettoride.models.history;


import java.util.LinkedList;
import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.Message;

public class ChatManager {
    private List<Message> messages;

    public ChatManager() {
        messages = new LinkedList<>();
    }


    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
