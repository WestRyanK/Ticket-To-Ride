package byu.codemonkeys.tickettoride.shared.model;

/**
 * Created by meganrich on 10/18/17.
 */

public class TrainCard {
    public static enum Type {
        Box,
        Passenger,
        Tanker,
        Reefer,
        Freight,
        Hopper,
        Coal,
        Caboose,
        Locomotive
    }

    private Type type;

    public TrainCard(Type type) {
        this.type = type;
    }
}
