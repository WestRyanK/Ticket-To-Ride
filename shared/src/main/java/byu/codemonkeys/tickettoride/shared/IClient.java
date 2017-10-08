package byu.codemonkeys.tickettoride.shared;

import byu.codemonkeys.tickettoride.shared.model.Session;

/**
 * Created by meganrich on 10/4/17.
 */

//TODO(compy-386): move this?
public interface IClient {
	void updatePendingGames() throws Exception;
	
	void updatePendingGame() throws Exception;
}
