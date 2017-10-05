package byu.codemonkeys.tickettoride.networking;

import byu.codemonkeys.tickettoride.models.ClientFacade;
import java.util.Timer;


/**
 * Created by meganrich on 10/4/17.
 */

public abstract class Poller {
    protected static final int pollingInterval = 1000;
    protected ClientFacade client;
    protected Timer timer = new Timer();


    public abstract void poll();
    public void startPolling(){
        poll();
    };
    public void stopPolling(){
        timer.cancel();
    };
}
