package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import byu.codemonkeys.tickettoride.server.broadcast.CommandManager;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.LastTurnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.NextTurnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.RouteClaimedCommandData;
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
import byu.codemonkeys.tickettoride.shared.results.ClaimRouteResult;

public class ActiveGame extends byu.codemonkeys.tickettoride.shared.model.ActiveGame {
    private static int STARTING_CARDS = 4;
    private static int LAST_TURN_TRAIN_THRESHOLD = 2;

    private boolean begun;
    private boolean finalRound;
    private int finalRoundTurns;

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

        finalRound = false;

        deal();
    }

    private CommandManager commandManager;

    @Override
    public void nextTurn() {
        turn.reset();

        turn = turn.getNextTurn();

        if (finalRound) {
            if (finalRoundTurns > 0) {
                finalRoundTurns--;
            }
            else {
                //end the game
            }
        }

        broadcastCommand(new NextTurnCommandData(getCurrentPlayer().getUsername()));

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
        return canClaimRoute();
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
     * broadcasts a command to all players except the excludedPlayer
     * @param command command to be broadcast
     * @param excludedPlayer player to not send the command to
     */
    public void broadcastCommandExclusive(CommandData command, Player excludedPlayer) {
        for (Player player : players) {
            if (!player.equals(excludedPlayer)) {
                commandManager.queueCommandSingleClient(command, player.getUsername());
            }
        }
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

    public ClaimRouteResult claimRoute(int routeID, User user, CardType cardType) {
        Player player = getPlayer(user);
        if (player == null) {
            return new ClaimRouteResult("Could not find the user in the game. This is a server error");
        }

        Self self = (Self) player;

        if (!isPlayersTurn(self.getUsername())) {
            return new ClaimRouteResult("Can only claim routes during your turn");
        }

        Map<CardType, Integer> hand = self.getHand();
        Route route = getMap().getRoute(routeID);
        if (route == null) {
            return new ClaimRouteResult("No such route");
        }

        if (route.isClaimed()) {
            return new ClaimRouteResult("Route is already claimed!");
        }

        if (!route.getRouteType().equals(CardType.Wild)) {
            cardType = route.getRouteType();
        }

        int cardsNeeded = route.getLength();
        int numNormalCards;
        int numWildCards = 0;

        if (hand.get(cardType) >= cardsNeeded) {
            numNormalCards = cardsNeeded;
        }
        else {
            numNormalCards = hand.get(cardType);
            cardsNeeded -= numNormalCards;

            if (hand.get(CardType.Wild) < cardsNeeded) {
                return new ClaimRouteResult("Insufficient cards to claim route");
            }

            numWildCards = cardsNeeded;
        }

        if (self.getNumTrains() < route.getLength()) {
            return new ClaimRouteResult("Insufficient trains to claim route");
        }

        //TODO: Check if a route is a parallel route

        if (route.claim(self)) {
            self.setNumTrains(self.getNumTrainCards() - route.getLength());

            hand.put(cardType, hand.get(cardType) - numNormalCards);
            hand.put(CardType.Wild, hand.get(CardType.Wild) - numWildCards);

            Map<CardType, Integer> cardsRemoved = new HashMap<>();
            cardsRemoved.put(cardType, numNormalCards);
            cardsRemoved.put(CardType.Wild, numWildCards);

            getDeck().discard(cardsRemoved);

            RouteClaimedCommandData claimedCommand = new RouteClaimedCommandData(routeID, route.getLength(), self);
            broadcastCommandExclusive(claimedCommand, self);
            nextTurn();

            if (self.getNumTrainCards() <= LAST_TURN_TRAIN_THRESHOLD) {
                broadcastCommand(new LastTurnCommandData());
                initiateFinalRound();
            }
            return new ClaimRouteResult(cardsRemoved, route.getLength());
        }

        return new ClaimRouteResult("Error claiming route");
    }

    private void initiateFinalRound() {
        finalRound = true;
        finalRoundTurns = players.size();
    }
}
