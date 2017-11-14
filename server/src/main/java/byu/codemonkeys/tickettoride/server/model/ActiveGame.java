package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import byu.codemonkeys.tickettoride.server.broadcast.CommandManager;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

import static byu.codemonkeys.tickettoride.shared.model.Player.Type.Opponent;

public class ActiveGame extends byu.codemonkeys.tickettoride.shared.model.ActiveGame {
    private static int STARTING_CARDS = 4;

    private boolean begun;

    public ActiveGame(PendingGame pendingGame) {
        super(pendingGame);

        this.commandManager = new CommandManager();

        Queue<PlayerColor> colors = new LinkedList<>();

        for (PlayerColor color : PlayerColor.values()) {
            colors.add(color);
        }

        for (UserBase user : pendingGame.getUsers()) {
            this.players.add(new Self(user.getUsername(), colors.poll()));
            commandManager.addClient(user.getUsername());
        }

        setDeck(new Deck());

        deal();
    }

    private CommandManager commandManager;

    @Override
    public void nextTurn() {
        turn = (turn + 1) % players.size();
    }

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
     * Gives each player their starting train cards.
     */
    public void deal() {
        for (Player player : players) {
            Self self = (Self) player;

            for (int i = 0; i < STARTING_CARDS; ++i) {
                self.addTrainCard(deck.drawTrainCard());
            }

            self.giveDestinationCards(deck.drawDestinationCards());
        }
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

    /**
     * Only includes information to which the specified user is privy.
     * @param user the user for whom the data is being prepared.
     * @return the prepared game.
     */
    public byu.codemonkeys.tickettoride.shared.model.ActiveGame prepareForClient(UserBase user) {
        byu.codemonkeys.tickettoride.shared.model.ActiveGame clientGame =
                new byu.codemonkeys.tickettoride.shared.model.ActiveGame(this);

        clientGame.setMap(this.getMap());
        clientGame.setTurn(this.turn);

        List<Player> clientPlayers = new ArrayList<>();

        for (Player player : players) {
            if (!player.getUsername().equals(user.getUsername())) {
                clientPlayers.add(new Opponent(player));
                continue;
            }

            clientPlayers.add(player);
        }

        clientGame.setPlayers(clientPlayers);
        clientGame.setDeck(new byu.codemonkeys.tickettoride.shared.model.cards.Deck(this.getDeck()));

        return clientGame;
    }

    public boolean isBegun() {
        return begun;
    }

    public void begin() {
        begun = true;
    }
}
