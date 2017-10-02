package byu.codemonkeys.tickettoride.server.model;


import java.util.UUID;

public class TokenGenerator {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
