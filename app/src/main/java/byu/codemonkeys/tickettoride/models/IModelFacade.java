package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observer;
import java.util.Map;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.shared.model.*;
import byu.codemonkeys.tickettoride.shared.model.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.TrainCard;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;

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
	
	PendingGamesResult leavePendingGame();
	
	void leavePendingGameAsync(ICallback leavePendingGameCallback);
	
	StartGameResult startGame();
	
	void startGameAsync(ICallback startGameCallback);
	
	void cancelGame() throws UnauthorizedException, NoPendingGameException;
	
	Session getSession();
	
	void changeConnectionConfiguration(String host, int port);

	//The player whose turn it is currently, for the sake of the player to judge when their turn may be coming
	UserBase playerTurn();

	//I would rather avoid having this method but I'll have it here for the time being!
	List<byu.codemonkeys.tickettoride.shared.model.Player> getPlayerInfo();

	//Here for the sake of the demonstration, just in case we need them, although these should already be covered in the user actions
	void addTrainCard(byu.codemonkeys.tickettoride.shared.model.TrainCard card); //should this be a list of cards?
	void removeTrainCard(byu.codemonkeys.tickettoride.shared.model.TrainCard card); //also this
	void addDestinationCard(byu.codemonkeys.tickettoride.shared.model.DestinationCard card);
	void removeDestinationCard(byu.codemonkeys.tickettoride.shared.model.DestinationCard card);

	// Public numbers for all to see on the board, player methods include the user
	Map<UserBase, Integer> getPlayerTrainCards();
	Map<UserBase, Integer> getPlayerDestinationCards();
	Map<UserBase, Integer> getPlayerPoints();
	int getDestinationCardDeckSize();
	int getTrainCardDeckSize();

	// User actions
	void sendMessage(Message message);
	List<TrainCard> drawTrainCards();
	List<DestinationCard> drawDestinationCards();
	void selectTrainCards(List<TrainCard> cards);
	void selectDestinationCards(List<DestinationCard> cards);
	//TODO: add claimed route, waiting on map

	List<Message> updateMessages();
	List<GameHistoryEntry> updateGameHistory();

}
