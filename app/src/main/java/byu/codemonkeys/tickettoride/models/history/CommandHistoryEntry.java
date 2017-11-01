package byu.codemonkeys.tickettoride.models.history;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;

public class CommandHistoryEntry {
    private String entry;

    public CommandHistoryEntry(CommandData command) {
        //TODO: Implement custom toString() function for all commands executed against the client
        entry = command.toString();
    }

    @Override
    public String toString() {
        return entry;
    }
}
