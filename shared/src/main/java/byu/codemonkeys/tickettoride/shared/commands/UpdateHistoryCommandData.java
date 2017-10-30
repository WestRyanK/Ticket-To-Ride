package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends an UpdateHistoryCommand to the server to request the commands it has not
 * received yet. This class specifies the data needed for the server to construct the command.
 */
public class UpdateHistoryCommandData extends CommandData {

    /**
     * index used when no commands have been seen such as at the start of a game
     */
    public static final int NO_COMMANDS_SEEN_INDEX = -1;

    /**
     * Constructs a new UpdateHistoryCommandData to fetch all command history since the last seen command
     * @param lastSeenCommandIndex the index of the last seen command. -1 indicates that no commands
     *                             have been seen.
     */
    public UpdateHistoryCommandData(int lastSeenCommandIndex) {
        super(CommandType.UPDATE_HISTORY);
        this.lastSeenCommandIndex = lastSeenCommandIndex;
    }

    /**
     * Constructs a new UpdateHistoryCommandData to fetch all command history
     */
    public UpdateHistoryCommandData() {
        this(NO_COMMANDS_SEEN_INDEX);
    }

    protected int lastSeenCommandIndex;
}
