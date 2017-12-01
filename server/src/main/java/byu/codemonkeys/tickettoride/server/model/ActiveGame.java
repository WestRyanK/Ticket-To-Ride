package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import byu.codemonkeys.tickettoride.server.broadcast.CommandManager;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.GameOverCommandData;
import byu.codemonkeys.tickettoride.shared.commands.LastTurnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.NextTurnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.RouteClaimedCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SkipTurnCommandData;
import byu.codemonkeys.tickettoride.shared.model.EndGamePlayerStats;
import byu.codemonkeys.tickettoride.shared.model.GameSummary;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;
import byu.codemonkeys.tickettoride.shared.model.map.City;
import byu.codemonkeys.tickettoride.shared.model.map.Route;
import byu.codemonkeys.tickettoride.shared.model.map.longestpathsolver.LongestPathCalculator;
import byu.codemonkeys.tickettoride.shared.model.turns.ActiveTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;
import byu.codemonkeys.tickettoride.shared.results.ClaimRouteResult;

public class ActiveGame extends byu.codemonkeys.tickettoride.shared.model.ActiveGame {
	private static int STARTING_CARDS = 4;
	private static int LAST_TURN_TRAIN_THRESHOLD = 2;
	private static int LONGEST_ROUTE_BONUS = 10;
	
	private boolean begun;
	private boolean ended;
	private Player firstSkippedPlayer;
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
			if (finalRoundTurns > 1) {
				finalRoundTurns--;
			} else {
				endGame();
				return;
			}
		}
		
		broadcastCommand(new NextTurnCommandData(getCurrentPlayer().getUsername()));
		
		if (!isActionPossible())
			skipTurn();
		else
			firstSkippedPlayer = null;
	}
	
	public void skipTurn() {
		if (firstSkippedPlayer == null) {
			firstSkippedPlayer = getCurrentPlayer();
		} else if (getCurrentPlayer().getUsername().equals(firstSkippedPlayer.getUsername())) {
			endGame();
			return;
		}
		
		broadcastCommand(new SkipTurnCommandData(getCurrentPlayer().getUsername()));
		
		nextTurn();
	}
	
	/**
	 * Determines whether the current player can take any action.
	 *
	 * @return there is an action the current player can perform.
	 */
	public boolean isActionPossible() {
		if (deck.getTrainCardsDeckCount() > 0) {
			return true;
		}
		if (deck.getDestinationCardsCount() > 0) {
			return true;
		}
		if (canDrawFaceUpTrainCard()) {
			return true;
		}
		//        if (canClaimRoute()) {
		//            return true;
		//        }
		
		return false;
	}
	
	private boolean canDrawFaceUpTrainCard() {
		for (TrainCard card : deck.getFaceUpTrainCards()) {
			if (card == null)
				continue;
			
			if (card.getCardColor() != CardType.Wild)
				return true;
			
			if (turn.canDrawWildTrainCard())
				return true;
		}
		
		return false;
	}
	
	/**
	 * Determines whether the current player can claim a route.
	 *
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
		
		if (maxPlayable == 0)
			return false;
		
		for (Route route : unclaimed) {
			CardType routeType = route.getRouteType();
			
			if (routeType == CardType.Wild) {
				if (route.getLength() <= maxPlayable)
					return true;
			}
			
			if (hand.get(routeType) + wilds >= route.getLength())
				return true;
		}
		
		return false;
	}
	
	/**
	 * Broadcasts a command to all players in the game
	 *
	 * @param command to be broadcast
	 */
	public void broadcastCommand(CommandData command) {
		commandManager.queueCommand(command);
	}
	
	/**
	 * Sends a command to a specific player
	 *
	 * @param command  command to be executed
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
	 *
	 * @param username             the player to fetch the history for
	 * @param lastReadCommandIndex the index of last command the player received
	 * @return A list of CommandData objects to be sent to the client
	 */
	public List<CommandData> getGameHistory(String username, int lastReadCommandIndex) {
		return commandManager.getCommands(username, lastReadCommandIndex);
	}
	
	/**
	 * Only includes information to which the specified user is privy.
	 *
	 * @param user the user for whom the data is being prepared.
	 * @return the prepared game.
	 */
	public byu.codemonkeys.tickettoride.shared.model.ActiveGame prepareForClient(UserBase user) {
		byu.codemonkeys.tickettoride.shared.model.ActiveGame clientGame = new byu.codemonkeys.tickettoride.shared.model.ActiveGame(
				this);
		
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
	
	public void endGame() {
		GameSummary gameSummary = new GameSummary();
		
		Map<String, Integer> pointsFromRoutes = pointsFromRoutes();
		
		Player longestRoutePlayer = null;
		int longestRoute = 0;
		for (Player player : players) {
			int playersLongestRoute = LongestPathCalculator.findLongestPath(this.map, player);
			if (playersLongestRoute > longestRoute) {
				longestRoute = playersLongestRoute;
				longestRoutePlayer = player;
			}
		}
		
		for (Player player : players) {
			Self self = (Self) player;
			int completedDestinationBonus = 0;
			int incompleteDestinationPenalty = 0;
			
			for (DestinationCard card : ((Self) player).getDestinations()) {
				if (destinationIsComplete(player, card))
					completedDestinationBonus += card.getPointValue();
				else
					incompleteDestinationPenalty -= card.getPointValue();
			}
			
			int longestRouteBonus = 0;
			if (player != null && player.getUsername().equals(longestRoutePlayer.getUsername()))
				longestRouteBonus = LONGEST_ROUTE_BONUS;
			
			EndGamePlayerStats playerSummary = new EndGamePlayerStats(player.getUsername(),
																	  player.getColor(),
																	  longestRouteBonus,
																	  pointsFromRoutes.get(player.getUsername()),
																	  completedDestinationBonus,
																	  incompleteDestinationPenalty);
			
			gameSummary.addSummary(playerSummary);
		}
		
		gameSummary.calculateWinner();
		
		broadcastCommand(new GameOverCommandData(gameSummary));
		
		ended = true;
	}
	
	private Map<String, Integer> pointsFromRoutes() {
		Map<String, Integer> points = new HashMap<>();
		
		for (Player player : players) {
			points.put(player.getUsername(), 0);
		}
		
		for (Route route : map.getAllRoutes()) {
			UserBase owner = route.getOwner();
			
			if (owner == null)
				continue;
			
			String username = owner.getUsername();
			
			try {
				points.put(username, points.get(username) + Route.getPointValue(route.getLength()));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(String.format(
						"Length: %d, Source: %s, Destination: %s",
						route.getLength(),
						route.getSource(),
						route.getDestination()));
			}
		}
		
		return points;
	}
	
	private boolean destinationIsComplete(Player player, DestinationCard destination) {
		Queue<City> cityQueue = new LinkedList<>();
		cityQueue.add(map.getCity(destination.getDestinationA()));
		Set<City> visited = new HashSet<>();
		
		while (!cityQueue.isEmpty()) {
			City visiting = cityQueue.poll();
			
			if (visited.contains(visiting))
				continue;
			
			visited.add(visiting);
			
			for (Route route : map.getRoutesForCity(visiting)) {
				if (!route.isClaimed())
					continue;
				
				if (player.getUsername().equals(route.getOwner().getUsername())) {
					City other = route.getOtherCity(visiting);
					
					if (other.getID() == destination.getDestinationB())
						return true;
					
					cityQueue.add(other);
				}
			}
		}
		
		return false;
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
		
		if (route.getRouteType().equals(CardType.Wild)) {
			if (cardType == null) {
				return new ClaimRouteResult("To claim a wild route, first select a card type from your hand.");
			}
		} else {
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
		
		if (route.isParallel()) {
			Route parallelRoute = map.getRoute(route.getParallelRouteID());
			
			if (players.size() <= 3) {
				if (parallelRoute.isClaimed()) {
					return new ClaimRouteResult("Parallel route already claimed.");
				}
			} else {
				if (parallelRoute.isClaimed() &&
						self.equals(parallelRoute.getOwner())) {
					return new ClaimRouteResult("Cannot claim parallel routes.");
				}
			}
		}
		
		if (route.claim(self)) {
			self.removeTrains(route.getLength());
			
			Map<CardType, Integer> cardsRemoved = new HashMap<>();
			cardsRemoved.put(cardType, numNormalCards);
			cardsRemoved.put(CardType.Wild, numWildCards);
			
			self.discardTrainCards(cardsRemoved);
			getDeck().discard(cardsRemoved);
			
			self.setScore(self.getScore() + route.getPoints());
			
			RouteClaimedCommandData claimedCommand = new RouteClaimedCommandData(routeID, self);
			broadcastCommand(claimedCommand);
			nextTurn();
			
			if (self.getNumTrains() <= LAST_TURN_TRAIN_THRESHOLD) {
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
	
	public boolean isEnded() {
		return ended;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		
		if (this == o)
			return true;
		
		if (getClass() != o.getClass())
			return false;
		
		ActiveGame other = (ActiveGame) o;
		
		return getID().equals(other.getID());
	}
	
	public List<CommandData> getAllGameHistory(String username) {
		return commandManager.getCommands(username);
	}
}
