package byu.codemonkeys.tickettoride.shared;


import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.results.DrawDeckTrainCardResult;
import byu.codemonkeys.tickettoride.shared.results.DrawFaceUpTrainCardResult;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;
import byu.codemonkeys.tickettoride.shared.results.DestinationCardResult;

/**
 * An interface that declares the operations that can be called on the server.
 */
public interface IServer {
	/**
	 * Authenticates a user using the provided username and password
	 *
	 * @param username Unique name identifying the user
	 * @param password Secret to validate the users credentials
	 * @return A LoginResult that contains a user session with an AuthToken if successful, otherwise returns an error message.
	 */
	LoginResult login(String username, String password);
	
	/**
	 * Registers a new user
	 *
	 * @param username Unique name to identify the user
	 * @param password Secret to validate the users credentials
	 * @return A LoginResult that contains a user session with an AuthToken if successful, otherwise returns an error message.
	 */
	LoginResult register(String username, String password);
	
	/**
	 * Logs a user out, clearing their session data.
	 *
	 * @param authToken Unique token used to authenticate with the server.
	 * @return Result indicating success or failure.
	 */
	Result logout(String authToken);
	
	/**
	 * Creates a new game owned by the authenticated user with the specified name.
	 *
	 * @param authToken an authorization token.
	 * @param gameName  the name to give the created game.
	 * @return a {@link PendingGameResult} containing the created game or an error message.
	 */
	PendingGameResult createGame(String authToken, String gameName);
	
	/**
	 * Adds the authenticated user to the game with the specified ID.
	 *
	 * @param authToken an authorization token.
	 * @param gameID    the ID of the game to join.
	 * @return a {@link PendingGame} containing the joined game or an error message.
	 */
	PendingGameResult joinPendingGame(String authToken, String gameID);
	
	/**
	 * Removes the authenticated user from the game associated with the authenticated session.
	 *
	 * @param authToken an authorization token.
	 * @return a {@link PendingGamesResult} containing the lobby.
	 */
	PendingGamesResult leavePendingGame(String authToken);
	
	/**
	 * Cancels the game associated with the authenticated session if the authenticated user is the
	 * owner of the game.
	 *
	 * @param authToken an authorization token.
	 * @return a {@link PendingGamesResult} containing the lobby or an error message.
	 */
	PendingGamesResult cancelGame(String authToken);
	
	/**
	 * Retrieves the lobby.
	 *
	 * @param authToken an authrization token.
	 * @return a {@link PendingGamesResult} containing the lobby.
	 */
	PendingGamesResult getPendingGames(String authToken);
	
	/**
	 * Retieves the pending game associated with the authenticated session.
	 *
	 * @param authToken an authorization token.
	 * @return a {@link PendingGameResult} containing the game.
	 */
	PendingGameResult getPendingGame(String authToken);
	
	/**
	 * Starts the pending game associated with the authenticated session if the authenticated user
	 * is the owner of the game.
	 *
	 * @param authToken an authorization token.
	 * @return a {@link StartGameResult} containing the started game or an error message.
	 */
	StartGameResult startGame(String authToken);
	
	/**
	 * Retrieves all the commands for the authenticated player after the specified index.
	 *
	 * @param authToken            an authorization token.
	 * @param lastSeenCommandIndex the index after which commands are being requested.
	 * @return a {@link HistoryResult} containing the requested commands or an error message.
	 */
	HistoryResult updateHistory(String authToken, int lastSeenCommandIndex);
	
	/**
	 * Draws destination cards from the top of the deck if it is the authenticated player's turn.
	 *
	 * @param authToken an authorization token.
	 * @return a {@link DestinationCardResult} containing the drawn destination cards.
	 */
	DestinationCardResult drawDestinationCards(String authToken);
	
	/**
	 * Selects the specified destination cards if the authenticated player is currently selecting
	 * destination cards.
	 *
	 * @param authToken   an authorization token.
	 * @param selected    the destination cards being selected.
	 * @return a {@link DestinationCardResult} containing the selected destination cards or an error
	 * message.
	 */
	DestinationCardResult chooseDestinationCards(String authToken, List<DestinationCard> selected);
	
	/**
	 * Sends a chat message from the authenticated player to the game associated with the
	 * authenticated session.
	 *
	 * @param authToken an authorization token.
	 * @param message   the chat message.
	 * @return a {@link Result} indicating whether the message was sent successfully.
	 */
	Result sendMessage(String authToken, Message message);
	
	/**
	 * Adds the revealed train card at the specified index to the hand of the authenticated player.
	 *
	 * @param faceUpCardIndex the index of the revealed card to draw.
	 * @param authToken       an authorization token.
	 * @return a {@link DrawFaceUpTrainCardResult} indicating whether the operation was successful.
	 */
	DrawFaceUpTrainCardResult drawFaceUpTrainCard(int faceUpCardIndex, String authToken);
	
	/**
	 * Add the train card from the top of the deck to the hand of the authenticated player.
	 *
	 * @param authToken an authroization token.
	 * @return a {@link DrawDeckTrainCardResult} indicating whether the operation was successful.
	 */
	DrawDeckTrainCardResult drawDeckTrainCard(String authToken);

	/**
	 * Claim a route on the map
	 *
	 * @param authToken an authorization token that indicates which player in which game is claiming a route
	 * @param routeID the id of the route the player is claiming
	 * @param cardType only needed for grey routes - the type of card the player will use to claim the route
	 * @return An acknowledgement that the command was received properly, the server will broadcast the claimed route to all clients.
	 */
	Result claimRoute(String authToken, int routeID, CardType cardType);
}
