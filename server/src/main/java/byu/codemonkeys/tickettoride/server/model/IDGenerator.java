package byu.codemonkeys.tickettoride.server.model;


import java.util.UUID;

public class IDGenerator {
    public static String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
