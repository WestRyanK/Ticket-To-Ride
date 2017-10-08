package byu.codemonkeys.tickettoride.networking;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by meganrich on 10/4/17.
 */

public abstract class Poller {
	protected int pollingInterval = 1000;
	protected int pollingDelay = 0;
	protected Timer timer;
	
	
	protected abstract TimerTask createTask();
	//	public abstract void poll();
	
	public void startPolling() {
		if (this.timer == null) {
			this.timer = new Timer();
			TimerTask task = createTask();
			this.timer.scheduleAtFixedRate(task, this.pollingDelay, this.pollingInterval);
		}
	}
	
	public void stopPolling() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
}
