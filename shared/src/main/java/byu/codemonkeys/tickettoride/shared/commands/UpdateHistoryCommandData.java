package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends an UpdateHistoryCommand to the server to request the commands it has not
 * received yet. This class specifies the data needed for the server to construct the command.
 */
public class UpdateHistoryCommandData extends CommandData {
    /**
     * Constructs a new UpdateHistoryCommandData
     */
    public UpdateHistoryCommandData() {
        super(CommandType.UPDATE_HISTORY);
    }
}
