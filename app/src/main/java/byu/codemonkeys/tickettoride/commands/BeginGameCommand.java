package byu.codemonkeys.tickettoride.commands;

import java.util.Map;

import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.shared.commands.BeginGameCommandData;

public class BeginGameCommand extends BeginGameCommandData implements IClientCommand {
    public BeginGameCommand(Map<String, Integer> numDestinationCards, int destinationCardDeckCount){
        super(numDestinationCards, destinationCardDeckCount);
    }

    @Override
    public void execute() {
        ModelFacade.getInstance().beginGame(this.numDestinationCards, destinationCardDeckCount);
    }

    @Override
    public String toString() {
        return "[GAME STARTED]";
    }
}
