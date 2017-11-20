package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.SkipTurnCommandData;

public class SkipTurnCommand extends SkipTurnCommandData implements IClientCommand {
    public SkipTurnCommand(String username) {
        super(username);
    }

    @Override
    public void execute() {
        ModelRoot.getInstance().getGame().nextTurn();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(this.username);
        sb.append(" cannot perform any actions]");

        return sb.toString();
    }
}
