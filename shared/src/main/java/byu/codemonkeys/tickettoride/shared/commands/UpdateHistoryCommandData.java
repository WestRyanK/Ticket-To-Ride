package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends an UpdateHistoryCommand to the server to request the commands it has not
 * received yet. This class specifies the data needed for the server to construct the command.
 */
public class UpdateHistoryCommandData extends CommandData {
    /**
     * Constructs a new UpdateHistoryCommandData
     * @param gameID this will be removed in a future merge request. It is unnecessary because the
     *               auth token will be used to determine the game ID.
     */
    public UpdateHistoryCommandData(String gameID) {
        super(CommandType.UPDATE_HISTORY);
    }
}
