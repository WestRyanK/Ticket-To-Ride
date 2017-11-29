package byu.codemonkeys.tickettoride.server.broadcast;


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
	
	public void addClient(String username) {
		clients.put(username, new ClientCommands(username));
	}
	
	public void queueCommand(CommandData command) {
		for (ClientCommands client : clients.values()) {
			client.queueCommand(command);
		}
	}
	
	public void queueCommandSingleClient(CommandData command, String username) {
		ClientCommands client = clients.get(username);
		if (client == null) {
			//TODO: make custom exception
			throw new RuntimeException("CommandManager Error: Client not found");
		}
		client.queueCommand(command);
	}
	
	public List<CommandData> getCommands(String username, int lastReadCommandPosition) {
		ClientCommands client = clients.get(username);
		if (client == null) {
			//TODO: make custom exception
			throw new RuntimeException("CommandManager Error: Client not found");
		}
		return client.getLatestCommands(lastReadCommandPosition);
	}
	
	public List<CommandData> getCommands(String username) {
		ClientCommands client = clients.get(username);
		if (client == null) {
			//TODO: make custom exception
			throw new RuntimeException("CommandManager Error: Client not found");
		}
		return client.getAllCommands();
		
	}
}
