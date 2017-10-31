package byu.codemonkeys.tickettoride.models;

import java.util.List;

import byu.codemonkeys.tickettoride.commands.IClientCommand;
import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IClient;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;

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
	
	@Override
	public void updatePendingGame() throws Exception {
		ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		PendingGameResult result = ServerProxy.getInstance().getPendingGame(authToken);
//		if (result.getErrorMessage() == null) {
			modelRoot.setPendingGame(result.getGame());
//		} else {
//			throw new Exception(result.getErrorMessage());
//		}
	}

	@Override
	public void updateGame() throws Exception {
		ModelRoot modelRoot = ModelRoot.getInstance();
		String authToken = modelRoot.getSession().getAuthToken();
		int lastReadCommandIndex = modelRoot.getLastReadCommandIndex();

		HistoryResult result = ServerProxy.getInstance().updateHistory(authToken, lastReadCommandIndex);
		List<CommandData> commands = result.getHistory();

		executeCommands(commands);
		modelRoot.getHistoryManager().addHistory(commands);
	}

    private void executeCommands(List<CommandData> commands) {
		for(CommandData command: commands){
			if(command instanceof IClientCommand){
				((IClientCommand) command).execute();
			}
		}
	}
}
