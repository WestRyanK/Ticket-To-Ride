package byu.codemonkeys.tickettoride.shared.commands;


import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class RouteClaimedCommandData extends CommandData {
    private int routeID;
    private UserBase owner;
    private int deckCount;
    private List<TrainCard> faceUpTrainCards;

    public RouteClaimedCommandData(int routeID, UserBase owner, int deckCount,
                                   List<TrainCard> faceUpTrainCards) {
        super(CommandType.ROUTE_CLAIMED);
        this.routeID = routeID;
        this.owner = owner;
        this.deckCount = deckCount;
        this.faceUpTrainCards = faceUpTrainCards;
    }

    public int getRouteID() {
        return routeID;
    }

    public UserBase getOwner() {
        return owner;
    }

    public int getDeckCount() {
        return deckCount;
    }

    public List<TrainCard> getFaceUpTrainCards() {
        return faceUpTrainCards;
    }
}
