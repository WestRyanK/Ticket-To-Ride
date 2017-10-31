package byu.codemonkeys.tickettoride.shared.model;

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

    public Type getType() {
        return type;
    }
}
