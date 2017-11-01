package byu.codemonkeys.tickettoride.shared.model;

public abstract class Player extends UserBase {
    public static class Type {
        public static final String Self = "self";
        public static final String Opponent = "opponent";
    }

    protected String type;
    protected int score;
    protected PlayerColor color;
    protected int numTrainCards;
    protected int numDestinationCards;
    protected int numTrains;

    protected Player(String userName, String type) {
        super(userName);
        this.type = type;
        this.numTrains = ActiveGame.MAX_TRAINS;
    }

    protected Player(String userName, String type, PlayerColor color) {
        super(userName);
        this.type = type;
        this.color = color;
        this.numTrains = ActiveGame.MAX_TRAINS;
    }

    public String getType() {
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

    public void addTrainCard(TrainCard card) {
        ++numTrainCards;
    }

    public int getNumDestinationCards(){
        return numDestinationCards;
    }

    public int getNumTrains() {
        return numTrains;
    }

    public void setNumTrains(int numTrains) {
        this.numTrains = numTrains;
    }
}
