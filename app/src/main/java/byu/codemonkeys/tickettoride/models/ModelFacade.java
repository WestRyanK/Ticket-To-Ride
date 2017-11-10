package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.async.ITask;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.models.history.CommandHistoryEntry;
import byu.codemonkeys.tickettoride.networking.ClientCommunicator;
import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.*;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;
import byu.codemonkeys.tickettoride.shared.results.DrawDeckTrainCardResult;
import byu.codemonkeys.tickettoride.shared.results.DrawFaceUpTrainCardResult;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;
import byu.codemonkeys.tickettoride.shared.results.DestinationCardResult;

/**
 * Provides a single point for presenters and commands to modify the client models.
 * {@invariant instance != null}
 * {@invariant serverProxy != null}
 * {@invariant models != null}
 * {@invariant communicator != null}
 */
public class ModelFacade implements IModelFacade {
	/**
	 * An instance of the ModelFacade (singleton), for use with singleton
	 */
	private static IModelFacade instance;
	/**
	 * Instance of the server proxy (singleton) for communication with the server
	 */
	private IServer serverProxy = ServerProxy.getInstance();
	/**
	 * Instance of the client communicator (singleton), so we can change the port and host on it.
	 */
	private ClientCommunicator communicator = ClientCommunicator.getInstance();
	/**
	 * Reference to the ModelRoot singleton
	 */
	private ModelRoot models = ModelRoot.getInstance();
	/**
	 * Instance of an asyncTask implementation. This is so that we can inject an
	 * Android specific ITask while running on Android, and a mock java task when running tests
	 */
	private ITask asyncTask;
	
	/**
	 * A tag to notify observers that the game was updated
	 */
	public static final String GAME_UPDATE = "GameUpdate";
	/**
	 * A tag to notify observers that the player's turn has changed
	 */
	public static final String PLAYER_TURN_UPDATE = "PlayerTurnUpdate";
	/**
	 * A tag to notify observers that the pending games has been updated
	 */
	public static final String PENDING_GAMES_UPDATE = "PendingGamesUpdate";
	/**
	 * A tag to notify observers that the current pending game has been updated
	 */
	public static final String PENDING_GAME_UPDATE = "PendingGameUpdate";
	/**
	 * A tag to notify observers that the stats for a player have been updated
	 */
	public static final String PLAYER_STATS_UPDATE = "PlayerStatsUpdate";
	/**
	 * A tag to notify observers that the history of the game has been updated
	 */
	public static final String HISTORY_UPDATE = "HistoryUpdate";
	
	/**
	 * Constructs an instance of ModelFacade, this is private for singleton implementation.
	 */
	private ModelFacade() {
	}
	
	/**
	 * Returns the singleton instance of the ModelFacade
	 *
	 * @return singleton instance of the ModelFacade
	 * {@post instance != null}
	 */
	public static IModelFacade getInstance() {
		if (instance == null) {
			instance = new ModelFacade();
		}
		return instance;
	}
	
	/**
	 * Adds an observer to observe the modelRoot
	 *
	 * @param observer The observer which will listen for changes on the ModelRoot
	 *                 {@pre observer != null}
	 *                 {@post models.obs.size() > 0}   the list of observers on the root isn't empty
	 */
	@Override
	public void addObserver(Observer observer) {
		models.addObserver(observer);
	}
	
	/**
	 * Returns the current user of the app.
	 *
	 * @return The current user of the app.
	 * {@post the current user is unchanged}
	 */
	@Override
	public UserBase getUser() {
		return ModelRoot.getInstance().getUser();
	}
	
	/**
	 * Tries to log a user into the system.
	 *
	 * @param username The username of the user to log in
	 * @param password The password of the user to log in
	 * @return A result specifying whether the login was successful.
	 * {@pre username != null}
	 * {@pre password != null}
	 * {@post if valid user, getUser() != null}
	 */
	@Override
	public LoginResult login(String username, String password) {
		LoginResult result = serverProxy.login(username, password);
		if (result.isSuccessful()) {
			models.setSession(result.getUserSession());
			models.setUser(new UserBase(username));
		}
		return result;
	}
	
	/**
	 * Tries to log a user into the system asynchronously
	 *
	 * @param username      The username of the user to log in
	 * @param password      The password of the user to log in
	 * @param loginCallback The method to call after trying to log in
	 *                      {@pre username != null}
	 *                      {@pre password != null}
	 *                      {@pre loginCallback != null}
	 *                      {@post if valid user, getUser() != null}
	 */
	@Override
	public void loginAsync(final String username,
						   final String password,
						   final ICallback loginCallback) {
		ICommand loginCommand = new ICommand() {
			@Override
			public Result execute() {
				return login(username, password);
			}
		};
		
		this.asyncTask.executeTask(loginCommand, loginCallback);
	}
	
	/**
	 * Tries to register a user for the system.
	 *
	 * @param username The username of the user to log in
	 * @param password The password of the user to log in
	 * @return A result specifying whether the registration was successful.
	 * {@pre username != null}
	 * {@pre password != null}
	 * {@pre loginCallback != null}
	 * {@post if user doesn't exist and username and password are valid, getUser() != null}
	 */
	@Override
	public LoginResult register(String username, String password) {
		LoginResult result = serverProxy.register(username, password);
		if (result.isSuccessful()) {
			models.setSession(result.getUserSession());
			models.setUser(new UserBase(username));
		}
		return result;
	}
	
	/**
	 * Tries to register a user for the system.
	 *
	 * @param username         The username of the user to register
	 * @param password         The password of the user to register
	 * @param registerCallback The method to call after trying to register
	 *                         {@pre username != null}
	 *                         {@pre password != null}
	 *                         {@pre loginCallback != null}
	 *                         {@pre registerCallback != null}
	 *                         {@post if user doesn't exist and username and password are valid, getUser() != null}
	 */
	@Override
	public void registerAsync(final String username,
							  final String password,
							  ICallback registerCallback) {
		ICommand registerCommand = new ICommand() {
			@Override
			public Result execute() {
				return register(username, password);
			}
		};
		
		this.asyncTask.executeTask(registerCommand, registerCallback);
	}
	
	/**
	 * Returns a list of pending games
	 *
	 * @return A list of pending games
	 * @throws UnauthorizedException Thrown if the user is unauthorized
	 *                               {@post The list of pending games is unchanged}
	 */
	@Override
	public List<GameBase> getPendingGames() throws UnauthorizedException {
		if (models.getSession() == null) {
			throw new UnauthorizedException("No user logged in");
		} else {
			return models.getPendingGames();
		}
	}
	
	/**
	 * Attempts to create a new game
	 *
	 * @param gameName The name of the new game to create
	 * @return A PendingGameResult that reports whether creation was successful
	 * {@pre gameName != null}
	 * {@pre gameName is not empty}
	 * {@post If successful, models.pendingGame != null}
	 */
	@Override
	public PendingGameResult createGame(String gameName) {
		PendingGameResult result = serverProxy.createGame(models.getSession().getAuthToken(),
														  gameName);
		if (result.isSuccessful()) {
			models.setPendingGame(result.getGame());
		}
		return result;
	}
	
	/**
	 * Attempts to create a new game
	 *
	 * @param gameName           The name of the new game to create
	 * @param createGameCallback The method to call after trying to create a game
	 *                           {@pre gameName != null}
	 *                           {@pre gameName is not empty}
	 *                           {@post If successful, models.pendingGame != null}
	 */
	@Override
	public void createGameAsync(final String gameName, ICallback createGameCallback) {
		ICommand createGameCommand = new ICommand() {
			@Override
			public Result execute() {
				return createGame(gameName);
			}
		};
		
		this.asyncTask.executeTask(createGameCommand, createGameCallback);
	}
	
	/**
	 * Gets the pending game which the user is waiting to start
	 *
	 * @return The pending game which the user is waiting to start
	 * @throws UnauthorizedException  Thrown if the user does not have permission to get information. AKA when not logged in
	 * @throws NoPendingGameException Thrown if there is no pending game.
	 *                                {@post models.pendingGame is unchanged}
	 */
	@Override
	public GameBase getPendingGame() throws UnauthorizedException, NoPendingGameException {
		if (models.getSession() == null) {
			throw new UnauthorizedException("No user logged in");
		} else if (models.getPendingGame() == null) {
			throw new NoPendingGameException();
		} else {
			return models.getPendingGame();
		}
	}
	
	/**
	 * Attempts to join a pending game
	 *
	 * @param game The game to join
	 * @return A Result specifying whether joining was successful
	 * {@pre game != null}
	 * {@post if successful, models.pendingGame != null}
	 */
	@Override
	public PendingGameResult joinPendingGame(GameBase game) {
		PendingGameResult result = serverProxy.joinPendingGame(models.getSession().getAuthToken(),
															   game.getID());
		if (result.isSuccessful()) {
			models.setPendingGame(result.getGame());
			
		}
		return result;
	}
	
	/**
	 * Attempts to join a pending game
	 *
	 * @param game                    The game to join
	 * @param joinPendingGameCallback The callback to run after attempting to join game
	 *                                {@pre game != null}
	 *                                {@pre joinPendingGameCallback != null}
	 *                                {@post if successful, models.pendingGame != null}
	 */
	@Override
	public void joinPendingGameAsync(final GameBase game, ICallback joinPendingGameCallback) {
		ICommand joinPendingGameCommand = new ICommand() {
			@Override
			public Result execute() {
				return joinPendingGame(game);
			}
		};
		
		this.asyncTask.executeTask(joinPendingGameCommand, joinPendingGameCallback);
	}
	
	/**
	 * Attempts to leave the current pending game.
	 *
	 * @return A result specifying whether the game was left.
	 * {@post if successful, models.pendingGame == null}
	 */
	@Override
	public PendingGamesResult leavePendingGame() {
		PendingGamesResult result = serverProxy.leavePendingGame(models.getSession()
																	   .getAuthToken());
		if (result.isSuccessful()) {
			models.setPendingGame(null);
			
		}
		return result;
	}
	
	/**
	 * Attempts to leave the current pending game.
	 *
	 * @param leavePendingGameCallback Callback to run after attempting to leave PendingGame
	 *                                 {@pre leavePendingGameCallback != null}
	 *                                 {@post if successful, models.pendingGame == null}
	 */
	@Override
	public void leavePendingGameAsync(ICallback leavePendingGameCallback) {
		ICommand leavePendingGameCommand = new ICommand() {
			@Override
			public Result execute() {
				return leavePendingGame();
			}
		};
		
		this.asyncTask.executeTask(leavePendingGameCommand, leavePendingGameCallback);
	}
	
	/**
	 * Attempts to start current pending game
	 *
	 * @return A Result specifying whether the game was successfully started
	 * {@post models.pendingGame.isStarted == true}
	 */
	@Override
	public StartGameResult startGame() {
		return serverProxy.startGame(models.getSession().getAuthToken());
	}
	
	/**
	 * Attempts to start current pending game
	 *
	 * @param startGameCallback Callback to run after starting a game
	 *                          {@post models.pendingGame.isStarted == true}
	 *                          {@pre startGameCallback != null}
	 */
	@Override
	public void startGameAsync(ICallback startGameCallback) {
		ICommand startGameCommand = new ICommand() {
			@Override
			public Result execute() {
				return startGame();
			}
		};
		
		this.asyncTask.executeTask(startGameCommand, startGameCallback);
	}
	
	/**
	 * Attempts to cancel the current pending game
	 *
	 * @throws UnauthorizedException  Thrown if the user is not logged in
	 * @throws NoPendingGameException Thrown if there is, in fact, no actual pending game
	 *                                {@post if successful models.pendingGame == null}
	 *                                {@post models.pendingGames is up to date}
	 */
	@Override
	public void cancelGame() throws UnauthorizedException, NoPendingGameException {
		if (models.getPendingGame() == null) {
			throw new NoPendingGameException();
		} else {
			PendingGamesResult result = serverProxy.cancelGame(models.getSession().getAuthToken());
			if (result.isSuccessful()) {
				models.setPendingGame(null);
				models.setPendingGames(result.getGames());
			}
		}
	}
	
	/**
	 * Gets the session of the currently logged in user
	 *
	 * @return The session of the currently logged in user
	 * {@post models.session is not changed}
	 */
	@Override
	public Session getSession() {
		return models.getSession();
	}
	
	/**
	 * Changes the connection settings to the server
	 *
	 * @param host The host's IP address
	 * @param port The host's port
	 *             {@pre host != null}
	 *             {@pre host is not empty}
	 *             {@pre port > 0 && port < 65535}
	 *             {@post communicator.host = host}
	 *             {@post communicator.port = port}
	 */
	@Override
	public void changeConnectionConfiguration(String host, int port) {
		communicator.changeConfiguration(host, port);
	}
	
	/**
	 * Sets the implementation of ITask that the model facade should use in order to execute async calls
	 *
	 * @param asyncTask The instance of ITask that the modelFacade should use to execute async calls
	 *                  {@pre asyncTask != null}
	 *                  {@post this.asyncTask = asyncTask}
	 */
	public void setAsyncTask(ITask asyncTask) {
		this.asyncTask = asyncTask;
	}
	
	/**
	 * Gets the player who's turn it currently is
	 *
	 * @return The player who's turn it currently is
	 * {@post models.activeGame is not changed}
	 * {@pre models.activeGame != null, aka we're actually in a game}
	 */
	public Player playerTurn() {
		int turn = models.getGame().getTurn();
		return models.getGame().getPlayers().get(turn);
	}
	
	/**
	 * Gets all the players in the current game
	 *
	 * @return The players in the current game
	 * {@pre models.activeGame != null}
	 * {@post models.activeGame is not changed}
	 * {@pre models.activeGame != null, aka we're actually in a game}
	 */
	public List<Player> getPlayerInfo() {
		if (models.getGame() == null) {
			return new ArrayList<>();
		}
		return models.getGame().getPlayers();
	}
	
	/**
	 * Broadcasts a chat message to all the players
	 *
	 * @param message The message to broadcast to players
	 * @return A result specifying whether the message was successfully sent
	 * {@pre message != null}
	 * {@pre message is not empty}
	 * {@pre models.activeGame != null, aka we're actually in a game}
	 */
	public Result sendMessage(Message message) {
		return serverProxy.sendMessage(models.getSession().getAuthToken(), message);
	}
	
	/**
	 * Broadcasts a chat message to all the players asynchronously
	 *
	 * @param message             The message to broadcast to players
	 * @param sendMessageCallback A callback to run after broadcasting message
	 *                            {@pre message != null}
	 *                            {@pre message is not empty}
	 *                            {@pre sendMessageCallback != null}
	 *                            {@pre models.activeGame != null, aka we're actually in a game}
	 */
	public void sendMessageAsync(final Message message, ICallback sendMessageCallback) {
		ICommand sendMessageCommand = new ICommand() {
			@Override
			public Result execute() {
				return sendMessage(message);
			}
		};
		
		this.asyncTask.executeTask(sendMessageCommand, sendMessageCallback);
	}
	
	@Override
	public DrawFaceUpTrainCardResult drawFaceUpTrainCard(int faceUpCardIndex) {
		return serverProxy.drawFaceUpTrainCard(faceUpCardIndex, models.getSession().getAuthToken());
	}
	
	@Override
	public void drawFaceUpTrainCardAsync(final int faceUpCardIndex,
										 ICallback drawFaceUpTrainCardCallback) {
		ICommand drawFaceUpTrainCardCommand = new ICommand() {
			@Override
			public Result execute() {
				return drawFaceUpTrainCard(faceUpCardIndex);
			}
		};
		
		this.asyncTask.executeTask(drawFaceUpTrainCardCommand, drawFaceUpTrainCardCallback);
	}
	
	@Override
	public DrawDeckTrainCardResult drawDeckTrainCard() {
		return serverProxy.drawDeckTrainCard(models.getSession().getAuthToken());
	}
	
	@Override
	public void drawDeckTrainCardAsync(ICallback drawDeckTrainCardCallback) {
		ICommand drawDeckTrainCardCommand = new ICommand() {
			@Override
			public Result execute() {
				return drawDeckTrainCard();
			}
		};
		
		this.asyncTask.executeTask(drawDeckTrainCardCommand, drawDeckTrainCardCallback);
	}
	
	//TODO: implement this in a future phase
	
	/**
	 * Tells the server which destination cards of the three given were kept
	 *
	 * @param cards A list of destination cards that the user selected
	 *              {@pre cards != null}
	 *              {@pre cards.size() <= 3}
	 *              {@pre cards.size() > 0}
	 *              {@post player has cards added to hand}
	 *              {@pre models.activeGame != null, aka we're actually in a game}
	 *              {@pre it's your turn}
	 */
	public void selectDestinationCards(List<DestinationCard> cards) {
		//Make server call
		DestinationCardResult result = serverProxy.chooseDestinationCards(models.getSession()
																				.getAuthToken(),
																		  cards.size(),
																		  cards);
		//On server success, add the cards to the model
		if (result.isSuccessful()) {
			for (DestinationCard card : cards) {
				ModelRoot.getInstance().getGame().getSelf().select(card);
			}
			ModelRoot.getInstance().getGame().getSelf().getSelecting().clear();
		}
	}
	
	/**
	 * Tells the server which destination cards of the three given were kept, run asynchronously
	 *
	 * @param cards                          A list of destination cards that the user selected
	 * @param selectDestinationCardsCallback The callback to run after telling server
	 *                                       {@pre selectDestinationCardsCallback != null}
	 *                                       {@pre cards != null}
	 *                                       {@pre cards.size() <= 3}
	 *                                       {@pre cards.size() > 0}
	 *                                       {@post player has cards added to hand}
	 *                                       {@pre models.activeGame != null, aka we're actually in a game}
	 *                                       {@pre it's your turn}
	 */
	public void selectDestinationCardsAsync(final List<DestinationCard> cards,
											ICallback selectDestinationCardsCallback) {
		ICommand selectDestinationCardsCommand = new ICommand() {
			@Override
			public Result execute() {
				selectDestinationCards(cards);
				return new Result();
			}
		};
		
		this.asyncTask.executeTask(selectDestinationCardsCommand, selectDestinationCardsCallback);
	}
	
	/**
	 * Sets up the game further after all players have selected destination cards
	 *
	 * @param numDestinationCards a map specifying how many cards each player selected
	 *                            {@pre numDestinationCards != null}
	 *                            {@pre numDestinationCards has an entry for each player's username}
	 *                            {@pre each entry has a value between 2 and 3}
	 *                            {@post the number of destination cards each player has is set to their value in the map}
	 */
	@Override
	public void beginGame(Map<String, Integer> numDestinationCards) {
		for (Map.Entry<String, Integer> entry : numDestinationCards.entrySet()) {
			Player player = (Player) models.getGame().getPlayer(new UserBase(entry.getKey()));
			if (player.getClass() == Opponent.class) {
				Opponent opponent = (Opponent) player;
				opponent.setNumDestinationCards(entry.getValue());
			}
		}
	}
	
	/**
	 * Returns the history of the game up to this point
	 *
	 * @return The history of the game up to this point
	 * {@post nothing in the models is changed}
	 */
	public List<CommandHistoryEntry> getGameHistory() {
		return models.getGameHistory();
	}
	
}
