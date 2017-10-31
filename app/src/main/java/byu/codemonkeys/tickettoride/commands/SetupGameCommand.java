package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.shared.commands.SetupGameCommandData;
import byu.codemonkeys.tickettoride.shared.model.ActiveGame;

public class SetupGameCommand extends SetupGameCommandData implements IClientCommand {
    public SetupGameCommand(ActiveGame game) {
        super(game);
    }

    @Override
    public void execute() {

    }
}
