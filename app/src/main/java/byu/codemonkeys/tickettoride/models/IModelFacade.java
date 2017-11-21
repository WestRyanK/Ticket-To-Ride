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
import byu.codemonkeys.tickettoride.shared.results.DestinationCardResult;

public interface IModelFacade {

	void addObserver(Observer observer);
	
	UserBase getUser();
	
	void loginAsync(String username, String password, ICallback callback);
	
	void registerAsync(String username, String password, ICallback registerCallback);
	
	List<GameBase> getPendingGames() throws UnauthorizedException;
	
	void createGameAsync(String gameName, ICallback createGameCallback);
	
	void joinPendingGameAsync(GameBase game, ICallback joinPendingGameCallback);
	
	GameBase getPendingGame() throws UnauthorizedException, NoPendingGameException;
	
	void leavePendingGameAsync(ICallback leavePendingGameCallback);
	
	void startGameAsync(ICallback startGameCallback);
	
	void cancelGame() throws UnauthorizedException, NoPendingGameException;
	
	Session getSession();
	
	void changeConnectionConfiguration(String host, int port);
	
	//The player whose turn it is currently, for the sake of the player to judge when their turn may be coming
	Player playerTurn();
	
	//This will be where one can find player color, score number of train cards, and number of destination cards
	List<Player> getPlayerInfo();
	
	// User actions
	void sendMessageAsync(Message message, ICallback sendMessageCallback);
	
	void drawDestinationCardsAsync(ICallback drawDestinationCardsCallback);
	
	void drawFaceUpTrainCardAsync(int faceUpCardIndex, ICallback drawFaceUpTrainCardCallback);
	
	void drawDeckTrainCardAsync(ICallback drawDeckTrainCardCallback);
	
	void chooseDestinationCardsAsync(List<DestinationCard> cards,
									 ICallback chooseDestinationCardsCallback);
	
	void beginGame(Map<String, Integer> numDestinationCards, int destinationCardDeckCount);
	//TODO: add claimed route, waiting on map
	
	List<CommandHistoryEntry> getGameHistory();

	void endGame();
	
	List<CommandHistoryEntry> getLatestGameHistory();

	void claimRouteAsync(int routeID, ICallback callback);
}
