package byu.codemonkeys.tickettoride.networking;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;

/**
 * Created by meganrich on 10/5/17.
 */

public class PendingGamesPoller extends Poller{
    private static PendingGamesPoller instance;
    private PendingGamesPoller() {}
    public static PendingGamesPoller getInstance() {
        if(instance == null){
            instance = new PendingGamesPoller();
        }
        return instance;
    }
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
