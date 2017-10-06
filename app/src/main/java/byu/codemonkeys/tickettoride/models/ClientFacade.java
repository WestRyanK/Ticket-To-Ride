package byu.codemonkeys.tickettoride.models;

import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.models.ModelFacade;
import byu.codemonkeys.tickettoride.shared.IClient;
import byu.codemonkeys.tickettoride.shared.model.Session;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;

/**
 * Created by meganrich on 10/5/17.
 */

public class ClientFacade implements IClient {
    private static ClientFacade instance;

    private ClientFacade(){}
    public static ClientFacade getInstance(){
        if (instance == null) {
            instance = new ClientFacade();
        }
        return instance;
    }
    @Override
    public void updatePendingGames() throws Exception{
        ModelRoot modelRoot = ModelRoot.getInstance();
        Session session = modelRoot.getSession();
        PendingGamesResult result = ServerProxy.getInstance().getPendingGames(session);
        if(result.getErrorMessage() == null) {
            modelRoot.setPendingGames(result.getPendingGames());
        } else throw new Exception(result.getErrorMessage());
    }
}
