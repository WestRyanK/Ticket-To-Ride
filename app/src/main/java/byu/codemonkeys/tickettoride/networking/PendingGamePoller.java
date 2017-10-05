package byu.codemonkeys.tickettoride.networking;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;

/**
 * Created by meganrich on 10/5/17.
 */

public class PendingGamePoller extends Poller{
    public void poll(){
        final ClientFacade c = this.client.getInstance();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                c.updateGameList();
            }
        };
        this.timer.scheduleAtFixedRate(task, this.pollingInterval, this.pollingInterval);
    }
}
