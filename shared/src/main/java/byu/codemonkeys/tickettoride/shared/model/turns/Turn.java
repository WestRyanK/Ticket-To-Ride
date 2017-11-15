package byu.codemonkeys.tickettoride.shared.model.turns;


public abstract class Turn {
    protected int playerIndex;
    protected Turn next;

    public abstract boolean canDrawTrainCard();
    public abstract boolean canDrawDestinationCards();
    public abstract boolean canClaimRoute();
    public abstract boolean canDrawWildTrainCard();
    public abstract void limit();
    public abstract void finished();
    public abstract void reset();

    public Turn() {
        // Empty Default constructor necessary for deserialization
    }

    public Turn(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public Turn getNextTurn() {
        return next;
    }

    public void setNextTurn(Turn next) {
        this.next = next;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
