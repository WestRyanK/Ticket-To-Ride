package byu.codemonkeys.tickettoride.networking;

import android.os.Handler;
import android.util.Log;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;

/**
 * Created by meganrich on 10/5/17.
 */

public class PendingGamesPoller extends Poller {
	private static PendingGamesPoller instance;
	
	private PendingGamesPoller() {
	}
	
	public static PendingGamesPoller getInstance() {
		if (instance == null) {
			instance = new PendingGamesPoller();
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
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		};
		return task;
	}
}
