package byu.codemonkeys.tickettoride.exceptions;

/**
 * Created by meganrich on 10/6/17.
 */

public class SingleGameException extends Exception {
    public SingleGameException() {
        super("A user can only be in one game at a time");
    }
}
