package byu.codemonkeys.tickettoride.networking;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;

/**
 * Created by meganrich on 10/5/17.
 */

public class PendingGamePoller extends Poller{
    public PendingGamePoller() {}
    public void poll(){
        final ClientFacade c = ClientFacade.getInstance();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    c.updatePendingGames();
                } catch (Exception e ){
                    //TODO(compy-386): handle this error?
                    System.out.println(e.getMessage());
                }
            }
        };
        this.timer.scheduleAtFixedRate(task, this.pollingDelay, this.pollingInterval);
    }
}
