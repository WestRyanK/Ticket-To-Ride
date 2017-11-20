package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.shared.commands.LastTurnCommandData;


public class LastTurnCommand extends LastTurnCommandData implements IClientCommand {
    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "[Final Round]";
    }
}
