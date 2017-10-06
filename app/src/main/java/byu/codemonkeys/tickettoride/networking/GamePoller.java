package byu.codemonkeys.tickettoride.networking;

/**
 * Created by meganrich on 10/5/17.
 */

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;
import byu.codemonkeys.tickettoride.shared.model.Session;

/**
 * Created by meganrich on 10/5/17.
 */

public class GamePoller extends Poller{
    private Session session;
    public GamePoller (Session session) {
        this.session = session;
    }
    public void poll(){
        //final ClientFacade c = this.client.getInstance();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //c.updateGameList();
                //TODO(compy-386): Implement update game on client facade
            }
        };
        this.timer.scheduleAtFixedRate(task, this.pollingInterval, this.pollingInterval);
    }
}
