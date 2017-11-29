package byu.codemonkeys.tickettoride.shared.results;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.model.ActiveGame;
import byu.codemonkeys.tickettoride.shared.model.Session;

/**
 * Created by Ryan on 11/29/2017.
 */

public class JoinExistingGameResult extends Result {
	private final ActiveGame restoredGame;
	
	private final Session restoredSession;
	
	private final List<CommandData> restoredCommandHistory;
	
	public JoinExistingGameResult(ActiveGame restoredGame,
								  Session restoredSession,
								  List<CommandData> restoredCommandHistory) {
		this.restoredGame = restoredGame;
		this.restoredSession = restoredSession;
		this.restoredCommandHistory = restoredCommandHistory;
	}
	
	public JoinExistingGameResult(String errorMessage) {
		super(errorMessage);
		this.restoredGame = null;
		this.restoredSession = null;
		this.restoredCommandHistory = null;
	}
	
	public ActiveGame getRestoredGame() {
		return restoredGame;
	}
	
	public Session getRestoredSession() {
		return restoredSession;
	}
	
	public List<CommandData> getRestoredCommandHistory() {
		return restoredCommandHistory;
	}
}
