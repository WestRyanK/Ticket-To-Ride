package byu.codemonkeys.tickettoride.networking;

import android.util.Log;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;

/**
 * Created by meganrich on 10/5/17.
 */

public class PendingGamePoller extends Poller {
	private static PendingGamePoller instance;
	
	private PendingGamePoller() {
	}
	
	public static PendingGamePoller getInstance() {
		if (instance == null) {
			instance = new PendingGamePoller();
		}
		return instance;
	}
	
	@Override
	protected TimerTask createTask() {
		final ClientFacade c = ClientFacade.getInstance();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Log.d("POLLER!", "Polled!");
				try {
					c.updatePendingGame();
				} catch (Exception e) {
					//TODO(compy-386): handle this error?
					System.out.println(e.getMessage());
				}
			}
		};
		return task;
	}
}
