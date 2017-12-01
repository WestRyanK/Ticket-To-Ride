package byu.codemonkeys.tickettoride.models.history;


import java.util.LinkedList;
import java.util.List;

import byu.codemonkeys.tickettoride.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.UpdateHistoryCommandData;

public class HistoryManager {
	private List<CommandHistoryEntry> commandHistory;
	private int lastReadCommandIndex;
	private int nextCommandForHistory;
	
	public HistoryManager() {
		commandHistory = new LinkedList<>();
		lastReadCommandIndex = UpdateHistoryCommandData.NO_COMMANDS_SEEN_INDEX;
		nextCommandForHistory = 0;
	}
	
	public int getLastReadCommandIndex() {
		return lastReadCommandIndex;
	}
	
	public void addHistory(List<CommandData> commands) {
		for (CommandData command : commands) {
			if (command.getQueuedPosition() == (lastReadCommandIndex + 1)) {
				commandHistory.add(new CommandHistoryEntry(command));
				lastReadCommandIndex = command.getQueuedPosition();
			}
		}
	}
	
	public void addHistory(CommandData command) {
		if (command.getQueuedPosition() == (lastReadCommandIndex + 1)) {
			commandHistory.add(new CommandHistoryEntry(command));
			lastReadCommandIndex = command.getQueuedPosition();
		}
		
	}
	
	public List<CommandHistoryEntry> getCommandHistory() {
		nextCommandForHistory = commandHistory.size();
		return commandHistory;
	}
	
	public List<CommandHistoryEntry> getLatestCommandHistory() {
		List<CommandHistoryEntry> latest = commandHistory.subList(nextCommandForHistory,
																  commandHistory.size());
		nextCommandForHistory = commandHistory.size();
		return latest;
	}
	
	public void restoreHistory(List<CommandData> restoredCommandHistory) {
		this.commandHistory.clear();
		for (CommandData data : restoredCommandHistory) {
			this.commandHistory.add(new CommandHistoryEntry(data));
		}
		lastReadCommandIndex = restoredCommandHistory.get(restoredCommandHistory.size() - 1)
													 .getQueuedPosition();
		nextCommandForHistory = lastReadCommandIndex;
	}
}
