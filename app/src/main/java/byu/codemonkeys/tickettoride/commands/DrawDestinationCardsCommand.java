package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.shared.commands.DrawDestinationCardsCommandData;

/**
 * Created by meganrich on 11/17/17.
 */

public class DrawDestinationCardsCommand extends DrawDestinationCardsCommandData implements IClientCommand {

    @Override
    public void execute(){
    }

    @Override
    public String toString(){
        return getUserName() + " drew destination cards";
    }
}
