package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.shared.commands.ChooseDestinationCardsCommandData;

/**
 * Created by meganrich on 11/17/17.
 */

public class ChooseDestinationCardsCommand extends ChooseDestinationCardsCommandData implements IClientCommand {
    public ChooseDestinationCardsCommand(String userName) {
        super(userName);
    }

    @Override
    public void execute(){

    }

    @Override
    public String toString(){
        return userName + " chose a few destination cards.";
    }
}
