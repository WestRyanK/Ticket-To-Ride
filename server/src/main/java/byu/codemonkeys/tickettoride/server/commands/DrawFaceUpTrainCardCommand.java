package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.DrawFaceUpTrainCardCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class DrawFaceUpTrainCardCommand extends DrawFaceUpTrainCardCommandData implements ICommand {
    public DrawFaceUpTrainCardCommand(int index) {
        super(index);
    }

    @Override
    public Result execute() {
        return ServerFacade.getInstance().drawFaceUpTrainCard(getIndex(), getAuthToken());
    }
}
