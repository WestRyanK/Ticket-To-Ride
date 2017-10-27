package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.Color;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.TrainCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The server sends a SetupGame command when the client begins polling for game commands. This class
 * contains the data the client needs to construct the command.
 */
public class SetupGameCommandData extends CommandData {
    protected SetupGameCommandData(List<UserBase> order, Map<Color, UserBase> colors,
                                   int numTrains, List<TrainCard> trainCards) {
        super(CommandType.SETUP_GAME);
        this.order = order;
        this.trainCards = trainCards;
        this.colors = colors;
        this.numTrains = numTrains;
    }

    protected List<UserBase> order;
    protected Map<Color, UserBase> colors;
    protected int numTrains;
    protected List<TrainCard> trainCards;

}
