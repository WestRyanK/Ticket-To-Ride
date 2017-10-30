package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.ArrayList;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.async.ITask;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.networking.ClientCommunicator;
import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.*;
import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;
import byu.codemonkeys.tickettoride.shared.results.DestinationCardResult;

/**
 * Created by Megan on 10/3/2017.
 */
//TODO(compy-386): implement poller for game starting, get API endpoint for it
public class ModelFacade implements IModelFacade {
	private static IModelFacade instance;
	private IServer serverProxy = ServerProxy.getInstance();
	private ClientCommunicator communicator = ClientCommunicator.getInstance();
	private ModelRoot models = ModelRoot.getInstance();
	private ITask asyncTask;

	public static final String PENDING_GAMES_UPDATE = "PendingGamesUpdate";
	public static final String PENDING_GAME_UPDATE = "PendingGameUpdate";

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
		if (result.getErrorMessage() == null) {
			models.setSession(result.getUserSession());
			models.setUser(new UserBase(username));
			//			start(pendingGamesPoller);
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
		if (result.getErrorMessage() == null) {
			//clear the models since they are logging out
			//			stop(pendingGamesPoller);
			models.getInstance().clear();
		} else
			throw new UnauthorizedException(result.getErrorMessage());
	}

	@Override
	public LoginResult register(String username, String password) {
		LoginResult result = serverProxy.register(username, password);
		if (result.getErrorMessage() == null) {
			models.setSession(result.getUserSession());
			models.setUser(new UserBase(username));
			//			start(pendingGamesPoller);
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
		if (result.getErrorMessage() == null) {
			models.setPendingGame(result.getGame());
			//			stop(pendingGamesPoller);
			//TODO(compy-386): Poll for game started, waiting so no longer have to get all pending games
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
		//		if (models.getPendingGame() == null) {
		PendingGameResult result = serverProxy.joinPendingGame(models.getSession().getAuthToken(),
				game.getID());
		if (result.getErrorMessage() == null) {
			models.setPendingGame(result.getGame());
			//			start(pendingGamesPoller);
		}
		//		}
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
		//		if (models.getPendingGame() == null) {
		//			throw new NoPendingGameException();
		//		} else {
		PendingGamesResult result = serverProxy.leavePendingGame(models.getSession().getAuthToken());
		if (result.getErrorMessage() == null) {
			models.setPendingGame(null);
			//				start(pendingGamesPoller);
		}
		return result;
		//			} else
		//				throw new UnauthorizedException(result.getErrorMessage());
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

	//TODO: FIX TO WORK WITH NEW POLLER
	@Override
	public StartGameResult startGame() {
		//START POLLER?
		StartGameResult result = serverProxy.startGame(models.getSession().getAuthToken());
		if (result.getErrorMessage() == null) {
			//models.setGame(result.getGame());
		}
		return result;
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
			if (result.getErrorMessage() == null) {
				models.setPendingGame(null);
				models.setPendingGames(result.getGames());
				//				start(pendingGamesPoller);
			}
		}
	}

	@Override
	public Session getSession() {
		return models.getSession();
	}


	//TODO(compy-386): Add error checking for invalid host and port inputs?
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
	public void addTrainCard(byu.codemonkeys.tickettoride.shared.model.TrainCard card) {
		models.addTrainCard(card);
	}

	public void addTrainCards(List<byu.codemonkeys.tickettoride.shared.model.TrainCard> cards){
		models.addTrainCards(cards);
	}

	public void removeTrainCard(byu.codemonkeys.tickettoride.shared.model.TrainCard card) {
		models.removeTrainCard(card);
	}

	; //also this

	public void addDestinationCard(byu.codemonkeys.tickettoride.shared.model.DestinationCard card) {
		models.addDestinationCard(card);
	}

	public void addDestinationCards(List<byu.codemonkeys.tickettoride.shared.model.DestinationCard> cards){
		models.addDestinationCards(cards);
	}

	public void removeDestinationCard(byu.codemonkeys.tickettoride.shared.model.DestinationCard card) {
		models.removeDestinationCard(card);
	}

	public int getDestinationCardDeckSize() {
		return 0;
	}

	public int getTrainCardDeckSize() {
		return 0;
	}

	// User actions
	public void sendMessage(Message message) {
		//Make server call
		Result result = serverProxy.sendMessage(models.getSession().getAuthToken(), message);
		//only add to model on success
		if(result.isSuccessful()) {
			models.addMessage(message);
		}
	}

	//TODO: implement this in a future phase
	public List<byu.codemonkeys.tickettoride.shared.model.TrainCard> drawTrainCards() {
		return null;
	}

	public List<byu.codemonkeys.tickettoride.shared.model.DestinationCard> drawDestinationCards() {
		DestinationCardResult result = serverProxy.drawDestinationCards(models.getSession().getAuthToken());
		return result.getDestinationCards();
	}

	//TODO: implement this in a future phase
	public void selectTrainCards(List<byu.codemonkeys.tickettoride.shared.model.TrainCard> cards) {}

	public void selectDestinationCards(List<DestinationCard> cards) {
		//Make server call
		DestinationCardResult result = serverProxy.chooseDestinationCards(models.getSession().getAuthToken(), cards.size(), cards);
		//On server success, add the cards to the model

	}
	//TODO: add claimed route, waiting on map

	public List<Message> getMessages() {
		return models.getMessages();
	}

	public List<GameHistoryEntry> getGameHistory(){
		return models.getGameHistory();
	}
}
