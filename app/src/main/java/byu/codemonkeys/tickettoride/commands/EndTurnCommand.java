package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.EndTurnCommandData;

/**
 * Created by meganrich on 11/17/17.
 */

public class EndTurnCommand extends EndTurnCommandData implements IClientCommand {
    public EndTurnCommand(String userName){
        super(userName);
    }
    @Override
    public void execute(){
        ModelRoot.getInstance().getGame().nextTurn();
    }

    @Override
    public String toString(){
        return userName + " could not make a move.";
    }
}
