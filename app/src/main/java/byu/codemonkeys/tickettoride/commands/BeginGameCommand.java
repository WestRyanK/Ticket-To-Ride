package byu.codemonkeys.tickettoride.commands;

import java.util.Map;

import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.shared.commands.BeginGameCommandData;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class BeginGameCommand extends BeginGameCommandData implements IClientCommand {
    public BeginGameCommand(Map<UserBase, Integer> numDestinationCards){
        super(numDestinationCards);
    }

    @Override
    public void execute() {
        ModelFacade.getInstance().beginGame(this.numDestinationCards);
    }
}
