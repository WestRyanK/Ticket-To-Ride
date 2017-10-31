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
	Player playerTurn();

	//This will be where one can find player color, score number of train cards, and number of destination cards
	List<Player> getPlayerInfo();

	//Here for the sake of the demonstration, just in case we need them, although these should already be covered in the user actions
	void addTrainCard(byu.codemonkeys.tickettoride.shared.model.TrainCard card); //should this be a list of cards?
	void addTrainCards(List<byu.codemonkeys.tickettoride.shared.model.TrainCard> cards);
	void removeTrainCard(TrainCard card); //also this

	void addDestinationCard(byu.codemonkeys.tickettoride.shared.model.DestinationCard card);
	void addDestinationCards(List<byu.codemonkeys.tickettoride.shared.model.DestinationCard> cards);
	void removeDestinationCard(byu.codemonkeys.tickettoride.shared.model.DestinationCard card);

	int getDestinationCardDeckSize();
	int getTrainCardDeckSize();

	// User actions
	Result sendMessage(Message message);
	List<TrainCard> drawTrainCards();
	List<DestinationCard> drawDestinationCards();
	void selectTrainCards(List<TrainCard> cards);
	void selectDestinationCards(List<DestinationCard> cards);
	//TODO: add claimed route, waiting on map

	List<Message> getMessages();
	List<GameHistoryEntry> getGameHistory();

}
