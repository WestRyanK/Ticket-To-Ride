package byu.codemonkeys.tickettoride.models;

import java.util.List;

import byu.codemonkeys.tickettoride.async.ITask;
import byu.codemonkeys.tickettoride.async.MainThreadTask;
import byu.codemonkeys.tickettoride.async.ICallback;
import byu.codemonkeys.tickettoride.commands.IClientCommand;
import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IClient;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * A set of operations that different pollers will use to update the models in real time.
 *
 * {@invariant will be a static Singleton instance, so ClientFacade.getInstance() will return the ClientFacade object}
 */

public class ClientFacade implements IClient {
	private static ClientFacade instance;

	private static ITask mainThreadTask;

	private ClientFacade() {
	}
	
	public static ClientFacade getInstance() {
		if (instance == null) {
			instance = new ClientFacade();
		}
		return instance;
	}
	
	public static void setMainThreadTask(ITask mainThreadTask) {
		ClientFacade.mainThreadTask = mainThreadTask;
	}

	@Override
	public void updatePendingGames() {
		final ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		final PendingGamesResult result = ServerProxy.getInstance().getPendingGames(authToken);
		ICommand setPendingGamesCommand = new ICommand() {
			@Override
			public Result execute() {
				modelRoot.setPendingGames(result.getGames());
				return null;
			}
		};
		this.mainThreadTask.executeTask(setPendingGamesCommand, null);
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
	public void updatePendingGame() {
		final ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		final PendingGameResult result = ServerProxy.getInstance().getPendingGame(authToken);
		ICommand setPendingGameCommand = new ICommand() {
			@Override
			public Result execute() {
				modelRoot.setPendingGame(result.getGame());
				return null;
			}
		};
		this.mainThreadTask.executeTask(setPendingGameCommand, null);
	}
	
	@Override
	public void updateGame() {
		final ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		int lastReadCommandIndex = modelRoot.getLastReadCommandIndex();
		final HistoryResult result = ServerProxy.getInstance().updateHistory(authToken, lastReadCommandIndex);
		
		ICommand executeHistoryCommand = new ICommand() {
			@Override
			public Result execute() {
				List<CommandData> commands = result.getHistory();
				
				if (commands.size() > 0){
					executeCommands(commands);
					modelRoot.getHistoryManager().addHistory(commands);
					modelRoot.historyUpdated();
				}
				return null;
			}
		};
		this.mainThreadTask.executeTask(executeHistoryCommand, null);
	}
	
	private void executeCommands(List<CommandData> commands) {
		for(CommandData command: commands){
			if(command instanceof IClientCommand){
				((IClientCommand) command).execute();
			}
		}
	}
}
