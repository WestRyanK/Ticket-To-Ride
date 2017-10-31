package byu.codemonkeys.tickettoride.shared.model;

public abstract class Player extends UserBase {
    public enum Type {
        Self,
        Opponent
    }

    private Type type;
    private int score;
    private PlayerColor color;
    private int numTrainCards;
    private int numDestinationCards;

    protected Player(String userName, Type type) {
        super(userName);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) throws IllegalArgumentException {
        if (score < 0) {
            throw new IllegalArgumentException("A player's score may not be negative.");
        }

        this.score = score;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public int getNumTrainCards(){
        return numTrainCards;
    }

    public int getNumDestinationCards(){
        return numDestinationCards;
    }
}
