package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.DestinationCardsDrawnCommandData;

/**
 * Created by meganrich on 11/17/17.
 */

public class DestinationCardsDrawnCommand extends DestinationCardsDrawnCommandData implements IClientCommand {
    
    public DestinationCardsDrawnCommand(String userName, int destinationCardsInDeckCount) {
        super(userName, destinationCardsInDeckCount);
    }
    
    @Override
    public void execute(){
        ModelRoot.getInstance().getGame().getDeck().setDestinationCardsCount(this.destinationCardsInDeckCount);
    }

    @Override
    public String toString(){
        return getUserName() + " drew destination cards";
    }
}
