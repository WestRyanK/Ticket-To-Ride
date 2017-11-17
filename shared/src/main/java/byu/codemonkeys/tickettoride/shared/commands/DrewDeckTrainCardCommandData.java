package byu.codemonkeys.tickettoride.shared.commands;

public class DrewDeckTrainCardCommandData extends CommandData {
    private String username;

    public DrewDeckTrainCardCommandData(String username) {
        super(CommandType.DREW_DECK_TRAIN_CARD);
        this.username = username;
    }
}
