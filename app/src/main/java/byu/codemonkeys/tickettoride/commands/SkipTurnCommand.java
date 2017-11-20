package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.SkipTurnCommandData;

public class SkipTurnCommand extends SkipTurnCommandData implements IClientCommand {
    public SkipTurnCommand(String username) {
        super(username);
    }

    @Override
    public void execute() {
        // A NextTurnCommand should follow.
    }

    @Override
    public String toString() {
        return String.format("[%s could not perform any action]", this.username);
    }
}
