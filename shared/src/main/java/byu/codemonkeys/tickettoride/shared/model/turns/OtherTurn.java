package byu.codemonkeys.tickettoride.shared.model.turns;


public class OtherTurn extends Turn {

    public OtherTurn() {
        // Empty Default constructor necessary for deserialization
    }

    public OtherTurn(int playerIndex) {
        super(playerIndex);
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
    public void limit() {

    }

    @Override
    public void finished() {

    }

    @Override
    public void reset() {

    }
}
