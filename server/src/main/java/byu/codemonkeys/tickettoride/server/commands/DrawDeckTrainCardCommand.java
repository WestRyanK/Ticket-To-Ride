package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.DrawDeckTrainCardCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class DrawDeckTrainCardCommand extends DrawDeckTrainCardCommandData implements ICommand {
    public DrawDeckTrainCardCommand(){
        super();
    }

    @Override
    public Result execute() {
        return ServerFacade.getInstance().drawDeckTrainCard(getAuthToken());
    }
}
