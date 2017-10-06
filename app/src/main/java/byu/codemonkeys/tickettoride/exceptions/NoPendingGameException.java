package byu.codemonkeys.tickettoride.exceptions;

/**
 * Created by meganrich on 10/6/17.
 */

public class NoPendingGameException extends Exception {
    public NoPendingGameException() {
        super("You have not joined a game yet");
    }
}
