package byu.codemonkeys.tickettoride.shared.commands;

/**
 * Created by meganrich on 10/18/17.
 */

public class UpdateHistoryCommandData extends CommandData {
    public UpdateHistoryCommandData(String gameID) {
        super(CommandType.UPDATE_HISTORY);
        this.gameID = gameID;
    }
    protected String gameID;
}
