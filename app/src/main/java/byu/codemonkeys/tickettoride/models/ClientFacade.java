package byu.codemonkeys.tickettoride.models;

import java.util.List;

import byu.codemonkeys.tickettoride.commands.IClientCommand;
import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IClient;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;

/**
 * A set of operations that different pollers will use to update the models in real time.
 *
 * {@invariant will be a static Singleton instance, so ClientFacade.getInstance() will return the ClientFacade object}
 */

public class ClientFacade implements IClient {
	private static ClientFacade instance;

	private ClientFacade() {
	}
	
	public static ClientFacade getInstance() {
		if (instance == null) {
			instance = new ClientFacade();
		}
		return instance;
	}

	/**
	 * Gets an updated list of pending games from the server, updates those in the ModelRoot
	 *
	 * {@pre modelRoot.getSession() != null}
	 * {@pre modelRoot.getSession().getAuthToken() is a valid auth token}
	 * {@post modelRoot will contain the updated list of pending games}
	 * @exception Exception no auth token, or other server error
	 */
	@Override
	public void updatePendingGames() throws Exception {
		ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		PendingGamesResult result = ServerProxy.getInstance().getPendingGames(authToken);
		if (result.getErrorMessage() == null) {
			modelRoot.setPendingGames(result.getGames());
		} else {
			throw new Exception(result.getErrorMessage());
		}
	}

	/**
	 * Gets the user's selected pending game from the server, and updates its information in the ModelRoot
	 *
	 * {@pre modelRoot.getSession() != null}
	 * {@pre modelRoot.getSession().getAuthToken() is a valid auth token}
	 * {@pre the user has selected a pending game}
	 * {@post modelRoot will contain the updated game information for the user's selected pending game}
	 * @exception Exception no auth token, the user has not selected a game, or other server error
	 */
	@Override
	public void updatePendingGame() throws Exception {
		ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		PendingGameResult result = ServerProxy.getInstance().getPendingGame(authToken);
			modelRoot.setPendingGame(result.getGame());
	}

	/**
	 * Gets the user's selected pending game from the server, and updates its information in the ModelRoot
	 *
	 * {@pre modelRoot.getSession() != null}
	 * {@pre modelRoot.getSession().getAuthToken() is a valid auth token}
	 * {@pre the user is associated with a running game}
	 * {@post modelRoot will have the updated game information, including messages and player stats}
	 * @exception Exception no auth token, the user is not apart of a running game, or other server error
	 */
	@Override
	public void updateGame() throws Exception {
		//TODO(compy-386): what if one of the commands fails to execute mid execution, will the others also be added to the history?
		ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		int lastReadCommandIndex = modelRoot.getLastReadCommandIndex();

		HistoryResult result = ServerProxy.getInstance().updateHistory(authToken, lastReadCommandIndex);
		List<CommandData> commands = result.getHistory();

		if (commands.size() > 0){
			executeCommands(commands);
			modelRoot.getHistoryManager().addHistory(commands);
			modelRoot.historyUpdated();
		}
	}

    private void executeCommands(List<CommandData> commands) {
		for(CommandData command: commands){
			if(command instanceof IClientCommand){
				((IClientCommand) command).execute();
			}
		}
	}
}
