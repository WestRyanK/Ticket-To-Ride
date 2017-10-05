package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IClient;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by meganrich on 10/5/17.
 */

public class ClientFacade implements IClient {
    private static ClientFacade instance;

    private ClientFacade(){}
    public ClientFacade getInstance(){
        if (instance == null) {
            instance = new ClientFacade();
        }
        return instance;
    }
    @Override
    public void updateGameList() {
        Result result = ServerProxy.getInstance().getPendingGames();
        //TODO(compy-386): Update model root
    }
}
