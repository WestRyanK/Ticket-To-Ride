package byu.codemonkeys.tickettoride.shared.model.turns;


import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class OtherTurn extends Turn {

    public static String OTHER_TURN = "other";

    public OtherTurn() {
        // Empty Default constructor necessary for deserialization
    }

    public OtherTurn(int playerIndex) {
        super(OTHER_TURN, playerIndex);
    }

    @Override
    public boolean canDrawTrainCard() {
        return false;
    }

    @Override
    public boolean canDrawDestinationCards() {
        return false;
    }

    @Override
    public boolean canClaimRoute() {
        return false;
    }

    @Override
    public boolean canDrawWildTrainCard() {
        return false;
    }

    @Override
    public void drawFaceUpTrainCard(TrainCard card) {

    }

    @Override public void drawDeckTrainCard() {

    }

    @Override
    public void limit() {

    }

    @Override
    public void finished() {

    }

    @Override
    public void reset() {

    }
}
