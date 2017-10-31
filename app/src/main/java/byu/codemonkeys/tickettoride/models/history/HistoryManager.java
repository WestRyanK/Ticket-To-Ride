package byu.codemonkeys.tickettoride.models.history;


import java.util.LinkedList;
import java.util.List;

import byu.codemonkeys.tickettoride.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.UpdateHistoryCommandData;

public class HistoryManager {
    private List<CommandHistoryEntry> commandHistory;
    private int lastReadCommandIndex;

    public HistoryManager() {
        commandHistory = new LinkedList<>();
        lastReadCommandIndex = UpdateHistoryCommandData.NO_COMMANDS_SEEN_INDEX;
    }

    public int getLastReadCommandIndex() {
        return lastReadCommandIndex;
    }

    public void addHistory(List<CommandData> commands) {
        for (CommandData command : commands) {
            if (shouldRecord(command)) {
                commandHistory.add(new CommandHistoryEntry(command));
            }
            lastReadCommandIndex = command.getQueuedPosition();
        }
    }

    public List<CommandHistoryEntry> getCommandHistory() {
        return getCommandHistory();
    }

    private boolean shouldRecord(CommandData command) {
        if (command instanceof SendMessageCommand) {
            return false;
        }
        return true;
    }
}
