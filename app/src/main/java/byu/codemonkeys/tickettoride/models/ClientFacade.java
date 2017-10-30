package byu.codemonkeys.tickettoride.models;

import java.util.UUID;
import java.util.List;

import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IClient;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.UpdateHistoryCommandData;
import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;

/**
 * Created by meganrich on 10/5/17.
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

	}

	@Override
    public void updateHistory() throws Exception {
        ModelRoot modelRoot = ModelRoot.getInstance();
        String authToken = modelRoot.getSession().getAuthToken();
        //TODO: Track the last seen and executed command and pass into updateHistory
        HistoryResult result = ServerProxy.getInstance().updateHistory(authToken, UpdateHistoryCommandData.NO_COMMANDS_SEEN_INDEX);
        List<CommandData> commands = result.getHistory();
        for(CommandData command: commands){
            switch(command.getCommandType()){
                //TODO:do appropriate action given the command type
            }
        }
    }
}
