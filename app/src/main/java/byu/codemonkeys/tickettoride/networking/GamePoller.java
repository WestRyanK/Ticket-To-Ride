package byu.codemonkeys.tickettoride.networking;

import android.util.Log;

import java.util.TimerTask;

import byu.codemonkeys.tickettoride.models.ClientFacade;

public class GamePoller extends Poller{
    private static GamePoller instance;

    private GamePoller() {}

    public static GamePoller getInstance(){
        if (instance == null) {
            instance = new GamePoller();
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
                    c.updateGame();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        return task;
    }
}
