package byu.codemonkeys.tickettoride.server.commands;

import java.util.List;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.ChooseDestinationCardsCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class ChooseDestinationCardsCommand extends ChooseDestinationCardsCommandData implements ICommand {
    public ChooseDestinationCardsCommand(List<DestinationCard> selected) {
        super(selected);
    }

    @Override
    public Result execute() {
        return ServerFacade.getInstance().chooseDestinationCards(getAuthToken(), selected);
    }
}
