package byu.codemonkeys.tickettoride.shared.model.turns;


import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class ActiveTurn extends Turn {
    private boolean isLimited;
    private boolean isFinished;

    public ActiveTurn() {
        // Empty Default constructor necessary for deserialization
    }

    public ActiveTurn(int playerIndex) {
        super(playerIndex);
        isLimited = false;
    }

    @Override
    public boolean canDrawTrainCard() {
        return !isFinished;
    }

    @Override
    public boolean canDrawDestinationCards() {
        return !isLimited && !isFinished;
    }

    @Override
    public boolean canClaimRoute() {
        return !isLimited && !isFinished;
    }

    @Override
    public boolean canDrawWildTrainCard() {
        return !isLimited && !isFinished;
    }

    @Override
    public void drawFaceUpTrainCard(TrainCard card) {
        CardType color = card.getCardColor();

        if (color == CardType.Wild) {
            finished();
            return;
        }

        if (isLimited) {
            finished();
            return;
        }

        limit();
    }

    @Override
    public void drawDeckTrainCard() {
        if (isLimited) {
            finished();
            return;
        }

        limit();
    }

    @Override
    public void limit() {
        isLimited = true;
    }

    @Override
    public void finished() {
        isFinished = true;
    }

    @Override
    public void reset() {
        isLimited = false;
        isFinished = false;
    }
}
