package byu.codemonkeys.tickettoride.server;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.codemonkeys.tickettoride.server.model.ClientCommands;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;

public class CommandManager {
    private Map<String, ClientCommands> clients;

    public CommandManager() {
        clients = new HashMap<>();
    }

    public void addClient(String authToken) {
        clients.put(authToken, new ClientCommands(authToken));
    }

    public void queueCommand(CommandData command) {
        for (ClientCommands client : clients.values()) {
            client.queueCommand(command);
        }
    }

    public void queueCommandSingleClient(CommandData command, String authToken) {
        ClientCommands client = clients.get(authToken);
        if (client == null) {
            //TODO: make custom exception
            throw new RuntimeException("CommandManager Error: Client not found");
        }
        client.queueCommand(command);
    }

    public List<CommandData>  getCommands(String authToken, int lastReadCommandPosition) {
        ClientCommands client = clients.get(authToken);
        if (client == null) {
            //TODO: make custom exception
            throw new RuntimeException("CommandManager Error: Client not found");
        }
        return client.getLatestCommands(lastReadCommandPosition);
    }

}
