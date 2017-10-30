package byu.codemonkeys.tickettoride.server.model;

import java.util.List;

import byu.codemonkeys.tickettoride.server.broadcast.CommandManager;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class ActiveGame extends GameBase {
    public ActiveGame(PendingGame pendingGame) {
        this.gameID = pendingGame.getID();
        this.gameName = pendingGame.getName();
        this.gameOwner = pendingGame.getOwner();
        this.gameUsers = pendingGame.getUsers();
        this.started = true;
        this.commandManager = new CommandManager();

        for (UserBase user : gameUsers) {
            commandManager.addClient(user.getUsername());
        }
    }

    private CommandManager commandManager;

    /**
     * Broadcasts a command to all players in the game
     * @param command to be broadcast
     */
    public void broadcastCommand(CommandData command) {
        commandManager.queueCommand(command);
    }

    /**
     * Sends a command to a specific player
     * @param command command to be executed
     * @param username username of the player to send the command to
     */
    public void sendCommand(CommandData command, String username) {
        commandManager.queueCommandSingleClient(command, username);
    }

    /**
     * Fetches the game history of a client since the last read command in the history
     * @param username the player to fetch the history for
     * @param lastReadCommandIndex the index of last command the player received
     * @return A list of CommandData objects to be sent to the client
     */
    public List<CommandData> getGameHistory(String username, int lastReadCommandIndex) {
        return commandManager.getCommands(username, lastReadCommandIndex);
    }
}
