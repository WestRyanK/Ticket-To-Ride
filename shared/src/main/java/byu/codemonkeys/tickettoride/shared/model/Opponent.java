package byu.codemonkeys.tickettoride.shared.model;

public class Opponent extends Player {
    public Opponent(String userName) {
        super(userName, Type.Opponent);
    }

    public Opponent(Player player) {
        this(player.getUsername());
        this.numTrainCards = player.getNumTrainCards();
        this.numDestinationCards = player.getNumDestinationCards();
        this.color = player.getColor();
    }
}
