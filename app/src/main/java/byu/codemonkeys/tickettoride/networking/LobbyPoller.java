package byu.codemonkeys.tickettoride.networking;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;

/**
 * Created by meganrich on 10/5/17.
 */

public class LobbyPoller extends Poller {
	private static LobbyPoller instance;
	
	private LobbyPoller() {
	}
	
	public static LobbyPoller getInstance() {
		if (instance == null) {
			instance = new LobbyPoller();
		}
		return instance;
	}
	
	@Override
	protected TimerTask createTask() {
		final ClientFacade c = ClientFacade.getInstance();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				try {
					c.updatePendingGames();
					c.updateExistingGames();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		};
		return task;
	}
}
