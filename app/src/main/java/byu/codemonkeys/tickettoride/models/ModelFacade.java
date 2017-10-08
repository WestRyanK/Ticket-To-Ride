package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.async.ITask;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.networking.ClientCommunicator;
import byu.codemonkeys.tickettoride.networking.PendingGamesPoller;
import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.Session;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;

/**
 * Created by Megan on 10/3/2017.
 */
//TODO(compy-386): implement poller for game starting, get API endpoint for it
public class ModelFacade implements IModelFacade {
	private static IModelFacade instance;
	private IServer serverProxy = ServerProxy.getInstance();
	private ClientCommunicator communicator = ClientCommunicator.getInstance();
	private ModelRoot models = ModelRoot.getInstance();
	private PendingGamesPoller pendingGamesPoller = PendingGamesPoller.getInstance();
	private ITask asyncTask;
	
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
	public void leavePendingGame() throws UnauthorizedException, NoPendingGameException {
		if (models.getPendingGame() == null) {
			throw new NoPendingGameException();
		} else {
			PendingGamesResult result = serverProxy.leavePendingGame(models.getSession()
																		   .getAuthToken(),
																	 models.getPendingGame()
																		   .getID());
			if (result.getErrorMessage() == null) {
				models.setPendingGame(null);
//				start(pendingGamesPoller);
			} else
				throw new UnauthorizedException(result.getErrorMessage());
		}
	}
	
	@Override
	public GameBase startGame() throws UnauthorizedException, NoPendingGameException {
		if (models.getPendingGame() == null) {
			throw new NoPendingGameException();
		} else {
			StartGameResult result = serverProxy.startGame(models.getSession().getAuthToken(),
														   models.getPendingGame().getID());
			if (result.getErrorMessage() == null) {
				models.setGame(result.getGame());
				return models.getGame();
			} else
				throw new UnauthorizedException(result.getErrorMessage());
		}
	}
	
	@Override
	public void cancelGame() throws UnauthorizedException, NoPendingGameException {
		if (models.getPendingGame() == null) {
			throw new NoPendingGameException();
		} else {
			PendingGamesResult result = serverProxy.cancelGame(models.getSession().getAuthToken(),
															   models.getPendingGame().getID());
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
	
//	private void start(Poller poller) {
//		poller.startPolling();
//	}
//
//	private void stop(Poller poller) {
//		poller.stopPolling();
//	}
//
	public void setAsyncTask(ITask asyncTask) {
		this.asyncTask = asyncTask;
	}
}
