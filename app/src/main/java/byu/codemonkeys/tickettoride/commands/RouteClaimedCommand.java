package byu.codemonkeys.tickettoride.commands;

import java.util.List;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.RouteClaimedCommandData;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;
import byu.codemonkeys.tickettoride.shared.model.map.Route;


public class RouteClaimedCommand extends RouteClaimedCommandData implements IClientCommand {
    public RouteClaimedCommand(int routeID, UserBase owner, int deckCount,
                               List<TrainCard> faceUpTrainCards) {
        super(routeID, owner, deckCount, faceUpTrainCards);
    }

    @Override
    public void execute() {
        ModelRoot modelRoot = ModelRoot.getInstance();
        Player player = modelRoot.getGame().getPlayer(this.getOwner().getUsername());
        Route route = modelRoot.getGame().getMap().getRoute(getRouteID());
        if (player.getClass() == Opponent.class) {
            player.removeTrainCards(route.getLength());
            player.removeTrains(route.getLength());
        }
        player.setScore(player.getScore() + route.getPoints());
        modelRoot.getGame().getMap().claimRoute(getRouteID(), player);
        modelRoot.getGame().getDeck().setFaceUpTrainCards(getFaceUpTrainCards());
        modelRoot.getGame().getDeck().setTrainCardsDeckCount(getDeckCount());
    }

    @Override
    public String toString() {
        Route route = ModelRoot.getInstance().getGame().getMap().getRoute(getRouteID());
        return String.format("[%s Route from %s to %s was Claimed by %s]", route.getRouteType(), route.getSource().getName(),
                route.getDestination().getName(), getOwner().getUsername());
    }
}
