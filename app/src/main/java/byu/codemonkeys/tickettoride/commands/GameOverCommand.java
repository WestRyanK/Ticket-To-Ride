package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.GameOverCommandData;
import byu.codemonkeys.tickettoride.shared.model.GameSummary;

public class GameOverCommand extends GameOverCommandData implements IClientCommand {
    public GameOverCommand(GameSummary summary) {
        super(summary);
    }

    @Override
    public void execute() {
        ModelRoot.getInstance().setSummary(summary);
    }

    @Override
    public String toString() {
        return String.format("[Game Over]");
    }
}
