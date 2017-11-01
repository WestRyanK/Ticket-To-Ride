package byu.codemonkeys.tickettoride.models;

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
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;
import byu.codemonkeys.tickettoride.shared.results.DestinationCardResult;

public class ModelFacade implements IModelFacade {
	private static IModelFacade instance;
	private IServer serverProxy = ServerProxy.getInstance();
	private ClientCommunicator communicator = ClientCommunicator.getInstance();
	private ModelRoot models = ModelRoot.getInstance();
	private ITask asyncTask;
	
	public static final String GAME_UPDATE = "GameUpdate";
	public static final String PLAYER_TURN_UPDATE = "PlayerTurnUpdate";
	public static final String PENDING_GAMES_UPDATE = "PendingGamesUpdate";
	public static final String PENDING_GAME_UPDATE = "PendingGameUpdate";
	public static final String PLAYER_STATS_UPDATE = "PlayerStatsUpdate";
	public static final String SCORE_UPDATE = "ScoreUpdate";
	public static final String COLOR_UPDATE = "ColorUpdate";
	public static final String PLAYER_TRAINS_UPDATE = "NumTrainsUpdate";
	public static final String PLAYER_TRAIN_CARDS_UPDATE = "NumTrainCardsUpdate";
	public static final String PLAYER_DESTINATION_CARDS_UPDATE = "NumDestinationCardsUpdate";
	
	private ModelFacade() {
	}
	
	public static IModelFacade getInstance() {
		if (instance == null) {
			instance = new ModelFacade();
		}
		return instance;
	}
	
	@Override
	public void addObserver(Observer observer) {
		models.addObserver(observer);
	}
	
	@Override
	public UserBase getUser() {
		return ModelRoot.getInstance().getUser();
	}
	
	@Override
	public LoginResult login(String username, String password) {
		LoginResult result = serverProxy.login(username, password);
		if (result.isSuccessful()) {
			models.setSession(result.getUserSession());
			models.setUser(new UserBase(username));
		}
		return result;
	}
	
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
	
	@Override
	public void logout() throws UnauthorizedException {
		Result result = serverProxy.logout(models.getSession().getAuthToken());
		if (result.isSuccessful()) {
			models.clear();
		} else
			throw new UnauthorizedException(result.getErrorMessage());
	}
	
	@Override
	public LoginResult register(String username, String password) {
		LoginResult result = serverProxy.register(username, password);
		if (result.isSuccessful()) {
			models.setSession(result.getUserSession());
			models.setUser(new UserBase(username));
		}
		return result;
	}
	
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
	
	@Override
	public List<GameBase> getPendingGames() throws UnauthorizedException {
		if (models.getSession() == null) {
			throw new UnauthorizedException("No user logged in");
		} else {
			return models.getPendingGames();
		}
	}
	
	@Override
	public PendingGameResult createGame(String gameName) {
		PendingGameResult result = serverProxy.createGame(models.getSession().getAuthToken(),
														  gameName);
		if (result.isSuccessful()) {
			models.setPendingGame(result.getGame());
		}
		return result;
	}
	
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
	
	@Override
	public PendingGameResult joinPendingGame(GameBase game) {
		PendingGameResult result = serverProxy.joinPendingGame(models.getSession().getAuthToken(),
															   game.getID());
		if (result.isSuccessful()) {
			models.setPendingGame(result.getGame());
			
		}
		return result;
	}
	
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
	
	@Override
	public PendingGamesResult leavePendingGame() {
		PendingGamesResult result = serverProxy.leavePendingGame(models.getSession()
																	   .getAuthToken());
		if (result.isSuccessful()) {
			models.setPendingGame(null);
			
		}
		return result;
	}
	
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
	
	@Override
	public StartGameResult startGame() {
		return serverProxy.startGame(models.getSession().getAuthToken());
	}
	
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
	
	@Override
	public Session getSession() {
		return models.getSession();
	}
	
	@Override
	public void changeConnectionConfiguration(String host, int port) {
		communicator.changeConfiguration(host, port);
	}
	
	public void setAsyncTask(ITask asyncTask) {
		this.asyncTask = asyncTask;
	}
	
	//The player whose turn it is currently, for the sake of the player to judge when their turn may be coming
	public Player playerTurn() {
		int turn = models.getGame().getTurn();
		return models.getGame().getPlayers().get(turn);
	}
	
	//this will return a list of players,
	public List<Player> getPlayerInfo() {
		return models.getGame().getPlayers();
	}
	
	//Here for the sake of the demonstration, just in case we need them, although these should already be covered in the user actions
	public void addTrainCard(byu.codemonkeys.tickettoride.shared.model.cards.TrainCard card) {
		models.addTrainCard(card);
	}
	
	public void addTrainCards(List<TrainCard> cards) {
		models.addTrainCards(cards);
	}
	
	public void removeTrainCard(TrainCard card) {
		models.removeTrainCard(card);
	}
	
	public void addDestinationCard(DestinationCard card) {
		models.addDestinationCard(card);
	}
	
	public void addDestinationCards(List<DestinationCard> cards) {
		models.addDestinationCards(cards);
	}
	
	public void removeDestinationCard(DestinationCard card) {
		models.removeDestinationCard(card);
	}
	
	public int getDestinationCardDeckSize() {
		return 0;
	}
	
	public int getTrainCardDeckSize() {
		return 0;
	}
	
	// User actions
	public Result sendMessage(Message message) {
		return serverProxy.sendMessage(models.getSession().getAuthToken(), message);
	}
	
	//TODO: implement this in a future phase
	public List<TrainCard> drawTrainCards() {
		return null;
	}
	
	public List<DestinationCard> drawDestinationCards() {
		DestinationCardResult result = serverProxy.drawDestinationCards(models.getSession()
																			  .getAuthToken());
		return result.getDestinationCards();
	}
	
	//TODO: implement this in a future phase
	public void selectTrainCards(List<TrainCard> cards) {
	}
	
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
	
	@Override
	public void beginGame(Map<UserBase, Integer> numDestinationCards) {
		
	}
	//TODO: add claimed route, waiting on map
	
	public List<Message> getMessages() {
		return models.getMessages();
	}
	
	public List<CommandHistoryEntry> getGameHistory() {
		return models.getGameHistory();
	}
	
}
