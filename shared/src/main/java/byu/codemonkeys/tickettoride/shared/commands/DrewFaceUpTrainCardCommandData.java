package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class DrewFaceUpTrainCardCommandData extends CommandData {
    private String username;
    private int index;
    private TrainCard replacement;

    public DrewFaceUpTrainCardCommandData(String username, int index, TrainCard replacement) {
        super(CommandType.DREW_FACEUP_TRAIN_CARD);
        this.username = username;
        this.index = index;
        this.replacement = replacement;
    }
}
