package byu.codemonkeys.tickettoride.networking;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.model.cards.CardType;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.results.*;
import byu.codemonkeys.tickettoride.shared.commands.*;
import byu.codemonkeys.tickettoride.shared.model.Message;


public class ServerProxy implements IServer {
	private static ServerProxy instance;
	
	private ClientCommunicator communicator;
	
	private ServerProxy() {
		communicator = ClientCommunicator.getInstance();
	}
	
	public static ServerProxy getInstance() {
		if (instance == null) {
			instance = new ServerProxy();
		}
		return instance;
	}
	
	@Override
	public LoginResult login(String username, String password) {
		LoginCommandData data = new LoginCommandData(username, password);
		return communicator.sendLogin(data);
	}
	
	@Override
	public Result logout(String authToken) {
		LogoutCommandData data = new LogoutCommandData();
		data.setAuthToken(authToken);
		return communicator.sendLogout(data);
	}
	
	@Override
	public LoginResult register(String username, String password) {
		RegisterCommandData data = new RegisterCommandData(username, password);
		return communicator.sendRegister(data);
	}
	
	@Override
	public PendingGameResult createGame(String authToken, String gameName) {
		CreateGameCommandData data = new CreateGameCommandData(gameName);
		data.setAuthToken(authToken);
		return communicator.sendCreateGame(data);
	}
	
	@Override
	public PendingGameResult joinPendingGame(String authToken, String gameID) {
		JoinPendingGameCommandData data = new JoinPendingGameCommandData(gameID);
		data.setAuthToken(authToken);
		return communicator.sendJoinPendingGame(data);
	}
	
	@Override
	public PendingGamesResult leavePendingGame(String authToken) {
		LeavePendingGameCommandData data = new LeavePendingGameCommandData();
		data.setAuthToken(authToken);
		return communicator.sendLeavePendingGame(data);
	}
	
	@Override
	public StartGameResult startGame(String authToken) {
		StartGameCommandData data = new StartGameCommandData();
		data.setAuthToken(authToken);
		return communicator.sendStartGame(data);
	}
	
	@Override
	public HistoryResult updateHistory(String authToken, int lastSeenCommandIndex) {
		UpdateHistoryCommandData data = new UpdateHistoryCommandData(lastSeenCommandIndex);
		data.setAuthToken(authToken);
		return communicator.sendUpdateHistory(data);
	}
	
	@Override
	public DestinationCardResult drawDestinationCards(String authToken) {
		DrawDestinationCardsCommandData data = new DrawDestinationCardsCommandData();
		data.setAuthToken(authToken);
		return communicator.sendDrawDestinationCards(data);
	}
	
	@Override
	public DestinationCardResult chooseDestinationCards(String authToken,
														List<DestinationCard> selected) {
		ChooseDestinationCardsCommandData data = new ChooseDestinationCardsCommandData(selected);
		data.setAuthToken(authToken);
		return communicator.sendChooseInitialDestinationCards(data);
	}
	
	@Override
	public Result sendMessage(String authToken, Message message) {
		SendMessageCommandData data = new SendMessageCommandData(message);
		data.setAuthToken(authToken);
		return communicator.sendSendMessage(data);
	}
	
	@Override
	public DrawFaceUpTrainCardResult drawFaceUpTrainCard(int faceUpCardIndex, String authToken) {
		DrawFaceUpTrainCardCommandData data = new DrawFaceUpTrainCardCommandData(faceUpCardIndex);
		
		data.setAuthToken(authToken);
		return communicator.sendDrawFaceUpTrainCard(data);
	}
	
	@Override
	public DrawDeckTrainCardResult drawDeckTrainCard(String authToken) {
		DrawDeckTrainCardCommandData data = new DrawDeckTrainCardCommandData();
		data.setAuthToken(authToken);
		return communicator.sendDrawDeckTrainCard(data);
	}

	@Override
	public ClaimRouteResult claimRoute(String authToken, int routeID, CardType cardType) {
		ClaimRouteCommandData data = new ClaimRouteCommandData(routeID, cardType);
		data.setAuthToken(authToken);
		return communicator.sendClaimRoute(data);
	}

	@Override
	public PendingGamesResult cancelGame(String authToken) {
		CancelGameCommandData data = new CancelGameCommandData();
		data.setAuthToken(authToken);
		return communicator.sendCancelGame(data);
	}
	
	@Override
	public PendingGamesResult getPendingGames(String authToken) {
		GetPendingGamesCommandData data = new GetPendingGamesCommandData();
		data.setAuthToken(authToken);
		return communicator.sendGetPendingGames(data);
	}
	
	@Override
	public PendingGameResult getPendingGame(String authToken) {
		GetPendingGameCommandData data = new GetPendingGameCommandData();
		data.setAuthToken(authToken);
		return communicator.sendGetPendingGame(data);
	}
}
