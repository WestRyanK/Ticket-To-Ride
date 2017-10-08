package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.Session;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;

/**
 * Created by Megan on 10/3/2017.
 */

public interface IModelFacade {
	
	
	void addObserver(Observer observer);
	
	UserBase getUser() throws UnauthorizedException;
	
	LoginResult login(String username, String password);
	
	void loginAsync(String username, String password, ICallback callback);
	
	LoginResult register(String username, String password);
	
	void registerAsync(String username, String password, ICallback registerCallback);
	
	void logout() throws UnauthorizedException;
	
	List<GameBase> getPendingGames() throws UnauthorizedException;
	
	PendingGameResult createGame(String gameName);
	
	void createGameAsync(String gameName, ICallback createGameCallback);
	
	PendingGameResult joinPendingGame(GameBase game);
	
	void joinPendingGameAsync(GameBase game, ICallback joinPendingGameCallback);
	
	GameBase getPendingGame() throws UnauthorizedException, NoPendingGameException;
	
	void leavePendingGame() throws UnauthorizedException, NoPendingGameException;
	
	GameBase startGame() throws UnauthorizedException, NoPendingGameException;
	
	void cancelGame() throws UnauthorizedException, NoPendingGameException;
	
	Session getSession();
	
	void changeConnectionConfiguration(String host, int port);
	
}
