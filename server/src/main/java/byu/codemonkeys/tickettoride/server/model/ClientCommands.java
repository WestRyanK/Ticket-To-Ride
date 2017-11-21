package byu.codemonkeys.tickettoride.server.model;


import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;

public class ClientCommands {
    private List<CommandData> commandQueue;
    private String clientToken;

    public ClientCommands(String clientToken) {
        this.clientToken = clientToken;
        commandQueue = new ArrayList<>();
    }

    public synchronized void queueCommand(CommandData command) {
        int pos = commandQueue.size();
        command.setQueuedPosition(pos);
        commandQueue.add(command);
    }

    public List<CommandData> getLatestCommands(int lastReadPosition) {
        return commandQueue.subList(lastReadPosition + 1, commandQueue.size());
    }
}
