package byu.codemonkeys.tickettoride.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import byu.codemonkeys.tickettoride.server.exceptions.AlreadyExistsException;
import byu.codemonkeys.tickettoride.server.exceptions.EmptyGameException;
import byu.codemonkeys.tickettoride.server.exceptions.FullGameException;
import byu.codemonkeys.tickettoride.server.model.ActiveGame;
import byu.codemonkeys.tickettoride.server.model.IRootModel;
import byu.codemonkeys.tickettoride.server.model.PendingGame;
import byu.codemonkeys.tickettoride.server.model.RootModel;
import byu.codemonkeys.tickettoride.server.model.ServerSession;
import byu.codemonkeys.tickettoride.server.model.User;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.commands.BeginGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.DeckTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.DestinationCardsChosenCommandData;
import byu.codemonkeys.tickettoride.shared.commands.DestinationCardsDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.FaceUpTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.FaceUpTrainCardsReshuffledCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SendMessageCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SetupGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SkipTurnCommandData;
import byu.codemonkeys.tickettoride.shared.model.ExistingGame;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.Session;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.Deck;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;
import byu.codemonkeys.tickettoride.shared.results.ClaimRouteResult;
import byu.codemonkeys.tickettoride.shared.results.DestinationCardResult;
import byu.codemonkeys.tickettoride.shared.results.DrawDeckTrainCardResult;
import byu.codemonkeys.tickettoride.shared.results.DrawFaceUpTrainCardResult;
import byu.codemonkeys.tickettoride.shared.results.ExistingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;
import byu.codemonkeys.tickettoride.shared.results.JoinExistingGameResult;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;


public class ServerFacade implements IServer {
	private static ServerFacade ourInstance;
	private IRootModel rootModel;
	
	private ServerFacade() {
		this(RootModel.getInstance());
	}
	
	private ServerFacade(IRootModel model) {
		rootModel = model;
	}
	
	public static void initialize(IRootModel model) {
		ourInstance = new ServerFacade(model);
	}
	
	public static ServerFacade getInstance() {
		if (ourInstance == null) {
			ourInstance = new ServerFacade();
		}
		
		return ourInstance;
	}
	
	@Override
	public LoginResult login(String username, String password) {
		boolean valid = rootModel.verifyLogin(username, password);
		if (valid) {
			return executeLogin(username);
		}
		return new LoginResult("invalid login credentials");
	}
	
	@Override
	public LoginResult register(String username, String password) {
		try {
			rootModel.registerNewUser(username, password);
		} catch (AlreadyExistsException e) {
			return new LoginResult("username already exists");
		} catch (IllegalArgumentException e) {
			return new LoginResult("username may not be null");
		}

		PersistenceFacade.getInstance().saveUser(rootModel.getUser(username));
		return executeLogin(username);
	}
	
	private LoginResult executeLogin(String username) {
		User user = rootModel.getUser(username);
		String authToken = rootModel.generateUniqueID();
		ServerSession session = new ServerSession(user, authToken);
		rootModel.addSession(session);
		return new LoginResult(session);
	}
	
	@Override
	public Result logout(String authToken) {
		rootModel.removeSession(authToken);
		return new Result();
	}
	
	@Override
	public PendingGameResult createGame(String authToken, String gameName) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new PendingGameResult("Authentication Error");
		}
		
		User user = session.getUser();
		
		PendingGame game = new PendingGame(rootModel.generateUniqueID(), gameName, user);
		
		rootModel.addPendingGame(game);
		
		session.setGameID(game.getID());
		
		return new PendingGameResult(game);
	}
	
	@Override
	public PendingGameResult joinPendingGame(String authToken, String gameID) {
		if (!rootModel.authorize(authToken)) {
			return new PendingGameResult("Authentication Error");
		}
		
		PendingGame game = rootModel.getPendingGame(gameID);
		
		if (game == null) {
			return new PendingGameResult("Invalid Game ID");
		}
		
		ServerSession session = rootModel.getSession(authToken);
		
		User user = session.getUser();
		
		if (game.hasUser(user)) {
			return new PendingGameResult("You already joined the game");
		}
		
		try {
			game.addUser(user);
			session.setGameID(gameID);
		} catch (FullGameException e) {
			return new PendingGameResult("The game is full");
		}
		
		return new PendingGameResult(game);
	}
	
	@Override
	public PendingGamesResult leavePendingGame(String authToken) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new PendingGamesResult("Authentication Error");
		}
		
		String gameID = session.getGameID();
		
		PendingGame game = rootModel.getPendingGame(gameID);
		
		if (game == null) {
			return new PendingGamesResult("Invalid Game ID");
		}
		
		User user = session.getUser();
		
		if (game.getOwner().equals(user)) {
			rootModel.removePendingGame(gameID);
		}
		
		try {
			game.removeUser(user);
		} catch (EmptyGameException e) {
			rootModel.removePendingGame(gameID);
		}
		
		return new PendingGamesResult(toGameBaseList(rootModel.getPendingGames()));
	}
	
	@Override
	public PendingGamesResult cancelGame(String authToken) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new PendingGamesResult("Authentication Error");
		}
		
		String gameID = session.getGameID();
		
		PendingGame game = rootModel.getPendingGame(gameID);
		
		if (game == null) {
			return new PendingGamesResult("Invalid Game ID");
		}
		
		User user = session.getUser();
		
		if (!game.getOwner().equals(user)) {
			return new PendingGamesResult("Only the game owner may cancel the game");
		}
		
		rootModel.removePendingGame(gameID);
		
		return new PendingGamesResult(toGameBaseList(rootModel.getPendingGames()));
	}
	
	@Override
	public PendingGamesResult getPendingGames(String authToken) {
		if (rootModel.authorize(authToken)) {
			return new PendingGamesResult(toGameBaseList(rootModel.getPendingGames()));
		}
		
		return new PendingGamesResult("Not Authorized");
	}
	
	/**
	 * Converts a List<PendingGame> to a List<GameBase>.
	 *
	 * @param pendingGames a List<PendingGame>.
	 * @return the converted List.
	 */
	private List<GameBase> toGameBaseList(List<PendingGame> pendingGames) {
		List<GameBase> games = new ArrayList<>();
		games.addAll(pendingGames);
		
		return games;
	}
	
	@Override
	public PendingGameResult getPendingGame(String authToken) {
		ServerSession session = rootModel.getSession(authToken);
		if (session == null) {
			return new PendingGameResult("invalid authentication");
		}
		PendingGame game = rootModel.getPendingGame(session.getGameID());
		if (game == null) {
			return new PendingGameResult("Invalid Game ID");
		}
		return new PendingGameResult(game);
	}
	
	@Override
	public StartGameResult startGame(String authToken) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new StartGameResult("Authentication Error");
		}
		
		String gameID = session.getGameID();
		
		PendingGame game = rootModel.getPendingGame(gameID);
		
		if (game == null) {
			return new StartGameResult("Invalid Game ID");
		}
		
		User user = session.getUser();
		
		if (!user.equals(game.getOwner())) {
			return new StartGameResult("Only the game owner may start the game");
		}
		
		if (!game.isStartable()) {
			return new StartGameResult("Too Few Players");
		}
		
		ActiveGame activeGame = rootModel.activateGame(gameID);

		for (Player player : activeGame.getPlayers()) {
			ServerSession s = rootModel.getSessionByUsernameAndGameID(player.getUsername(), activeGame.getID());
			PersistenceFacade.getInstance().saveSession(s);
		}
		
		for (Player player : activeGame.getPlayers()) {
			byu.codemonkeys.tickettoride.shared.model.ActiveGame preparedGame = activeGame.prepareForClient(
					player);
			activeGame.sendCommand(new SetupGameCommandData(preparedGame), player.getUsername());
		}

		PersistenceFacade.getInstance().trackGame(activeGame);
		
		return new StartGameResult(activeGame);
	}
	
	@Override
	public HistoryResult updateHistory(String authToken, int lastSeenCommandIndex) {
		ServerSession session = rootModel.getSession(authToken);
		if (session != null) {
			ActiveGame game = rootModel.getActiveGame(session.getGameID());
			List<CommandData> history = game.getGameHistory(session.getUser().getUsername(),
															lastSeenCommandIndex);
			return new HistoryResult(history);
		} else
			return new HistoryResult("Session is null?");
	}
	
	@Override
	public DestinationCardResult drawDestinationCards(String authToken) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new DestinationCardResult("Authentication Error");
		}
		
		ActiveGame game = rootModel.getActiveGame(session.getGameID());
		
		if (game == null) {
			return new DestinationCardResult("Player is not part of an active game");
		}
		
		User user = session.getUser();
		Player player = game.getPlayer(user);
		
		if (player == null) {
			return new DestinationCardResult(
					"Could not find the user in the game. This is a server error");
		}
		
		Self self = (Self) player;
		
		if (player == null) {
			return new DestinationCardResult(
					"Could not find the user in the game. This is a server error");
		}
		
		if (!game.getTurn().canDrawDestinationCards()) {
			return new DestinationCardResult("You may not draw destination cards now");
		}
		
		Set<DestinationCard> cards = game.getDeck().drawDestinationCards();
		((Self) player).giveDestinationCards(cards);
		
		List<DestinationCard> cardsList = new ArrayList<>(cards);
		DestinationCardResult result = new DestinationCardResult(cardsList);
		
		game.broadcastCommand(new DestinationCardsDrawnCommandData(player.getUsername(),
																   game.getDeck()
																	   .getDestinationCardsCount()));
		
		return result;
	}
	
	@Override
	public Result sendMessage(String authToken, Message message) {
		ServerSession session = rootModel.getSession(authToken);
		if (session == null) {
			return Result.failed("Authentication Error");
		}
		String gameID = session.getGameID();
		SendMessageCommandData messageCommand = new SendMessageCommandData(message);
		ActiveGame game = rootModel.getActiveGame(gameID);
		if (game == null) {
			return Result.failed("Player is not part of an active game");
		}
		game.broadcastCommand(messageCommand);
		return Result.success();
	}
	
	@Override
	public DrawFaceUpTrainCardResult drawFaceUpTrainCard(int faceUpCardIndex, String authToken) {
		if (faceUpCardIndex < 0 || faceUpCardIndex >= Deck.NUM_REVEALED) {
			return new DrawFaceUpTrainCardResult("Invalid Index");
		}
		
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new DrawFaceUpTrainCardResult("Not Authorized");
		}
		
		ActiveGame game = rootModel.getActiveGame(session.getGameID());
		
		if (game == null) {
			return new DrawFaceUpTrainCardResult("You are not part of an active game");
		}
		
		User user = session.getUser();
		
		if (!game.isPlayersTurn(user.getUsername())) {
			return new DrawFaceUpTrainCardResult("It is not your turn");
		}
		
		Turn turn = game.getTurn();
		
		if (!turn.canDrawTrainCard()) {
			return new DrawFaceUpTrainCardResult("You cannot draw a train card");
		}
		
		TrainCard card = game.getDeck().getFaceUpTrainCards().get(faceUpCardIndex);
		
		if (!turn.canDrawWildTrainCard() && card.getCardColor() == CardType.Wild) {
			return new DrawFaceUpTrainCardResult("You cannot draw a wild card");
		}
		
		if (card == null) {
			return new DrawFaceUpTrainCardResult("There is no card at position " + faceUpCardIndex);
		}
		
		Self player = (Self) game.getPlayer(user);
		
		boolean wasShuffled = game.getDeck().drawFaceUpTrainCard(faceUpCardIndex);
		player.addTrainCard(card);
		
		turn.drawFaceUpTrainCard(card);
		// Tell everyone that the face up train cards were shuffled
		if (wasShuffled)
			game.broadcastCommand(new FaceUpTrainCardsReshuffledCommandData());
		
		game.broadcastCommand(new FaceUpTrainCardDrawnCommandData(player.getUsername(),
																  card,
																  // I return the list of all the face up cards because all of them could change if there are 3 or more wilds
																  game.getDeck()
																	  .getFaceUpTrainCards(),
																  game.getDeck()
																	  .getTrainCardsDeckCount(),
																  player.getNumTrainCards()));
		
		if (!turn.canDrawTrainCard()) {
			game.nextTurn();
		} else if (!game.isActionPossible()) {
			game.broadcastCommand(new SkipTurnCommandData(player.getUsername()));
			game.nextTurn();
		}
		
		return new DrawFaceUpTrainCardResult(card);
	}
	
	@Override
	public DrawDeckTrainCardResult drawDeckTrainCard(String authToken) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new DrawDeckTrainCardResult("Not Authorized");
		}
		
		ActiveGame game = rootModel.getActiveGame(session.getGameID());
		
		if (game == null) {
			return new DrawDeckTrainCardResult("You are not part of an active game");
		}
		
		User user = session.getUser();
		
		if (!game.isPlayersTurn(user.getUsername())) {
			return new DrawDeckTrainCardResult("It is not your turn");
		}
		
		Turn turn = game.getTurn();
		
		if (!turn.canDrawTrainCard()) {
			return new DrawDeckTrainCardResult("You cannot draw a train card");
		}
		
		TrainCard card = game.getDeck().drawTrainCard();
		
		if (card == null) {
			return new DrawDeckTrainCardResult("The deck is empty");
		}
		
		Self player = (Self) game.getPlayer(user);
		
		player.addTrainCard(card);
		
		turn.drawDeckTrainCard();
		
		game.broadcastCommand(new DeckTrainCardDrawnCommandData(player.getUsername(),
																game.getDeck()
																	.getTrainCardsDeckCount(),
																player.getNumTrainCards()));
		
		if (!turn.canDrawTrainCard()) {
			game.nextTurn();
		} else if (!game.isActionPossible()) {
			game.broadcastCommand(new SkipTurnCommandData(player.getUsername()));
			game.nextTurn();
		}
		
		return new DrawDeckTrainCardResult(card);
	}
	
	@Override
	public ClaimRouteResult claimRoute(String authToken, int routeID, CardType cardType) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new ClaimRouteResult("Authentication Error");
		}
		
		String gameID = session.getGameID();
		
		ActiveGame game = rootModel.getActiveGame(gameID);
		if (game == null) {
			return new ClaimRouteResult("Player is not part of an active game");
		}
		
		User user = session.getUser();
		
		return game.claimRoute(routeID, user, cardType);
	}
	
	@Override
	public JoinExistingGameResult joinExistingGame(String authToken, String gameId) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new JoinExistingGameResult("Authentication Error");
		}
		
		User user = session.getUser();
		Session restoredSession = this.rootModel.getSessionByUsernameAndGameID(user.getUsername(),
																			   gameId);
		if (restoredSession == null)
			return new JoinExistingGameResult("Could not find a matching session");
		
		ActiveGame game = this.rootModel.getActiveGame(gameId);
		
		if (game == null)
			return new JoinExistingGameResult("Could not find a game with gameID " + gameId);
		byu.codemonkeys.tickettoride.shared.model.ActiveGame restoredGame = game.prepareForClient(
				game.getPlayer(user));
		
		List<CommandData> restoredCommandHistory = game.getAllGameHistory(user.getUsername());
		
		return new JoinExistingGameResult(restoredGame, restoredSession, restoredCommandHistory);
	}
	
	@Override
	public ExistingGamesResult getExistingGames(String authToken) {
		if (!rootModel.authorize(authToken))
			return new ExistingGamesResult("Not Authorized");
		
		ServerSession session = rootModel.getSession(authToken);
		User user = session.getUser();
		List<ExistingGame> existingGames = rootModel.getExistingGames(user);
		return new ExistingGamesResult(existingGames);
		
	}
	
	@Override
	public DestinationCardResult chooseDestinationCards(String authToken,
														List<DestinationCard> cards) {
		ServerSession session = rootModel.getSession(authToken);
		
		if (session == null) {
			return new DestinationCardResult("Authentication Error");
		}
		
		String gameID = session.getGameID();
		
		ActiveGame game = rootModel.getActiveGame(gameID);
		
		if (game == null) {
			return new DestinationCardResult("Player is not part of an active game");
		}
		
		User user = session.getUser();
		
		Player player = game.getPlayer(user);
		
		if (player == null) {
			return new DestinationCardResult(
					"Could not find the user in the game. This is a server error");
		}
		
		Self self = (Self) player;
		
		// Ensure that the user only picks cards from their hand
		boolean containsAll = true;
		for (DestinationCard card : cards) {
			boolean contains = false;
			for (DestinationCard selectingCard : self.getSelecting()) {
				if (selectingCard.getId() == card.getId())
					contains = true;
			}
			if (contains == false)
				containsAll = false;
		}
		if (!containsAll) {
			return new DestinationCardResult("You tried to select a card you haven't drawn.");
		}
		
		// Put cards in player's hand
		for (DestinationCard card : cards) {
			self.select(card);
		}
		// Return unselected cards to deck
		Set<DestinationCard> drawnCards = self.getSelecting();
		Set<DestinationCard> returnedCards = new HashSet<>();
		for (DestinationCard drawnCard : drawnCards) {
			boolean contains = false;
			for (DestinationCard chosenCard : cards) {
				if (drawnCard.getId() == chosenCard.getId())
					contains = true;
			}
			if (!contains)
				returnedCards.add(drawnCard);
		}
		((byu.codemonkeys.tickettoride.server.model.Deck) game.getDeck()).returnDestinationCardsToDeck(
				returnedCards);
		self.getSelecting().clear();
		
		if (game.isBegun()) {
			game.broadcastCommand(new DestinationCardsChosenCommandData(player.getUsername(),
																		cards.size(),
																		game.getDeck()
																			.getDestinationCardsCount(),
																		player.getNumDestinationCards()));
			game.nextTurn();
		}
		
		beginGame(game);
		
		return new DestinationCardResult(cards);
	}
	
	private void beginGame(ActiveGame game) {
		if (game.isBegun()) {
			return;
		}
		
		Map<String, Integer> numDestinations = new HashMap<>();
		
		for (Player player : game.getPlayers()) {
			Self self = (Self) player;
			
			int numDestinationCards = self.getNumDestinationCards();
			
			if (numDestinationCards == 0) {
				return;
			}
			
			numDestinations.put(player.getUsername(), numDestinationCards);
		}
		
		game.begin();
		
		game.broadcastCommand(new BeginGameCommandData(numDestinations,
													   game.getDeck().getDestinationCardsCount()));
	}
}
