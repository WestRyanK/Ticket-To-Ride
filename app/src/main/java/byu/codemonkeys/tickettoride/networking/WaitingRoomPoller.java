package byu.codemonkeys.tickettoride.networking;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;

/**
 * Created by meganrich on 10/5/17.
 */

public class WaitingRoomPoller extends Poller {
	private static WaitingRoomPoller instance;
	
	private WaitingRoomPoller() {
	}
	
	public static WaitingRoomPoller getInstance() {
		if (instance == null) {
			instance = new WaitingRoomPoller();
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
					c.updatePendingGame();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		};
		return task;
	}
}
