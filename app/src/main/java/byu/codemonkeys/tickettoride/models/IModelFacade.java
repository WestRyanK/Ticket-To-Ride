package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Map;
import java.util.Observer;

import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.exceptions.NoPendingGameException;
import byu.codemonkeys.tickettoride.exceptions.UnauthorizedException;
import byu.codemonkeys.tickettoride.models.history.CommandHistoryEntry;
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

/**
 * Created by Megan on 10/3/2017.
 */

public interface IModelFacade {
	
	
	void addObserver(Observer observer);
	
	UserBase getUser();
	
	LoginResult login(String username, String password);
	
	void loginAsync(String username, String password, ICallback callback);
	
	LoginResult register(String username, String password);
	
	void registerAsync(String username, String password, ICallback registerCallback);
	
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

	// User actions
	Result sendMessage(Message message);
	void sendMessageAsync(Message message, ICallback sendMessageCallback);
	
	void drawFaceUpTrainCardAsync(int faceUpCardIndex, ICallback drawFaceUpTrainCardCallback);
	
	void drawDeckTrainCardAsync(ICallback drawDeckTrainCardCallback);
	
	void selectDestinationCards(List<DestinationCard> cards);
	
	void selectDestinationCardsAsync(List<DestinationCard> cards, ICallback selectDestinationCardsCallback);
	
	void beginGame(Map<String, Integer> numDestinationCards);
	//TODO: add claimed route, waiting on map

	List<CommandHistoryEntry> getGameHistory();
}
