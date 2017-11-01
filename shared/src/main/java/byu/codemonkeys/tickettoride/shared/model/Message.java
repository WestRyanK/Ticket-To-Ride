package byu.codemonkeys.tickettoride.shared.model;

import java.util.Date;

public class Message {
    public Message() {

    }

    public Message(UserBase user, String message) {
        this.user = user;
        this.message = message;
        this.time = new Date();
    }
    protected UserBase user;
    protected String message;
    protected Date time;

    @Override
    public String toString() {
        return String.format("[%s] %s", user.getUsername(), message);
    }
}
