package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.NextTurnCommandData;
import byu.codemonkeys.tickettoride.shared.model.ActiveGame;

public class NextTurnCommand extends NextTurnCommandData implements IClientCommand {
    public NextTurnCommand(String username) {
        super(username);
    }

    @Override
    public void execute() {
        ActiveGame game = ModelRoot.getInstance().getGame();
        game.nextTurn();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("It is ");
        sb.append(this.username);
        sb.append("'s turn");

        return sb.toString();
    }
}
