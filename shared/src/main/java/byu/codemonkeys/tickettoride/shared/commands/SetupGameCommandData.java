package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.ActiveGame;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

import java.util.List;
import java.util.Map;

/**
 * The server sends a SetupGameCommand when the client begins polling for game commands. This class
 * contains the data the client needs to construct the command.
 */
public class SetupGameCommandData extends CommandData {
    /**
     * Constructs a new SetupGameCommand.
     * @param game an ActiveGame for the intended Client.
     */
    protected SetupGameCommandData(ActiveGame game) {
        super(CommandType.SETUP_GAME);
        this.game = game;
    }

    /**
     * Constructs a new SetupGameCommand.
     * @param order the players' associated users in order of turn.
     * @param colors each player's train color.
     * @param numTrains the number of trains with which each player begins.
     * @param trainCards the receiving player's starting train cards.
     * @deprecated Replaced by {@link #SetupGameCommandData(ActiveGame)}
     *             Rather than sending pieces of data and requiring the client to construct the
     *             game, simply send the client a game. That way, when the game model changes, we
     *             won't have to refactor this constructor as well.
     */
    @Deprecated
    protected SetupGameCommandData(List<UserBase> order, Map<PlayerColor, UserBase> colors,
                                   int numTrains, List<TrainCard> trainCards) {
        super(CommandType.SETUP_GAME);
        this.order = order;
        this.colors = colors;
        this.numTrains = numTrains;
        this.trainCards = trainCards;
    }

    protected ActiveGame game;

    /**
     * The following fields will be removed once we are sure no one is using the deprecated
     * constructor. We cannot call the new constructor from within the deprecated constructor
     * because the arguments do not indicate the receiving user.
     */
    List<UserBase> order;
    Map<PlayerColor, UserBase> colors;
    int numTrains;
    protected List<TrainCard> trainCards;
}
