package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.Color;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.TrainCard;
import java.util.ArrayList;
import java.util.Map;
/**
 * Created by meganrich on 10/18/17.
 */

public class SetupGameCommandData extends CommandData {
    protected SetupGameCommandData(ArrayList<UserBase> order, Map<Color, UserBase> colors,
                                   int numTrains, ArrayList<TrainCard> trainCards) {
        super(CommandType.SETUP_GAME);
        for(UserBase user: order) this.order.add(user);
        for(TrainCard card: trainCards) this.trainCards.add(card);
        this.colors.putAll(colors);
        this.numTrains = numTrains;

    }

    protected ArrayList<UserBase> order;
    protected Map<Color, UserBase> colors;
    protected int numTrains;
    protected ArrayList<TrainCard> trainCards;

}
