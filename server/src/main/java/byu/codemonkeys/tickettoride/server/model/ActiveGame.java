package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import byu.codemonkeys.tickettoride.server.broadcast.CommandManager;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.SkipTurnCommandData;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.map.Route;
import byu.codemonkeys.tickettoride.shared.model.turns.ActiveTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;

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

        this.turn = new ActiveTurn(0);

        Turn tempTurn = this.turn;

        for (int i = 1; i < this.players.size(); ++i) {
            Turn nextTurn = new ActiveTurn(i);
            tempTurn.setNextTurn(nextTurn);
            tempTurn = nextTurn;
        }

        tempTurn.setNextTurn(this.turn);

        deal();
    }

    private CommandManager commandManager;

    @Override
    public void nextTurn() {
        turn = turn.getNextTurn();

        if (!isActionPossible()) {
            broadcastCommand(new SkipTurnCommandData(getCurrentPlayer().getUsername()));
            nextTurn();
        }
    }

    /**
     * Determines whether the current player can take any action.
     * @return there is an action the current player can perform.
     */
    private boolean isActionPossible() {
        if (deck.getTrainCardsDeckCount() > 0) return true;
        if (deck.getDestinationCardsCount() > 0) return true;
        if (deck.getFaceUpTrainCards().size() > 0) return true;
        if (canClaimRoute()) return true;

        return false;
    }

    /**
     * Determines whether the current player can claim a route.
     * @return there is an unclaimed route that the current player is able to claim.
     */
    private boolean canClaimRoute() {
        List<Route> unclaimed = new ArrayList<>();

        for (Route route : map.getAllRoutes()) {
            if (!route.isClaimed()) {
                unclaimed.add(route);
            }
        }

        Map<CardType, Integer> hand = ((Self) players.get(turn.getPlayerIndex())).getHand();

        int wilds = hand.containsKey(CardType.Wild) ? hand.get(CardType.Wild) : 0;
        int maxPlayable = wilds;
        CardType maxPlayableType;

        for (Map.Entry<CardType, Integer> entry : hand.entrySet()) {
            int value = entry.getValue();

            if (value > maxPlayable) {
                CardType key = entry.getKey();
                maxPlayable = key == CardType.Wild ? value : value + wilds;
                maxPlayableType = entry.getKey();
            }
        }

        if (maxPlayable == 0) return false;

        for (Route route : unclaimed) {
            CardType routeType = route.getRouteType();

            if (routeType == CardType.Wild) {
                if (route.getLength() <= maxPlayable) return true;
            }

            if (hand.get(routeType) + wilds >= route.getLength()) return true;
        }

        return false;
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
