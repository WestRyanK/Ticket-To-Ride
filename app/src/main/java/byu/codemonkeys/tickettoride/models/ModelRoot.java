package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

import byu.codemonkeys.tickettoride.networking.ServerProxy;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.results.*;

/**
 * Created by Megan on 10/3/2017.
 */
//TODO(compy-386): throw proper errors for invalid input
 public class ModelRoot implements ModelFacade{
    private ModelRoot instance;
    private IServer serverProxy = ServerProxy.getInstance();
    private User user;
    private PendingGame pendingGame;
    private ArrayList<PendingGame> pendingGames;
    private ClientSession session;
    private Game game;

    private ModelRoot(){}

    public ModelRoot getInstance() {
        if(instance == null) {
            instance = new ModelRoot();
        }
        return instance;
    }

     @Override
     public User getUser() {
      return user;
     }

     @Override
     public void loginUser(String username, String password) {
        Result result = serverProxy.login(username, password);

         serverProxy.getPendingGames();
     }

     @Override
     public void logoutUser() {
        serverProxy.logout();
     }

     @Override
     public void registerUser(String username, String password) {
         serverProxy.register(username, password);

     }

     @Override
     public ClientSession getSession() {
         return session;
     }

     @Override
     public void setSession(ClientSession session) {
         this.session = session;
     }

     @Override
     public ArrayList<PendingGame> getPendingGames() {
         return pendingGames;
     }

     @Override
     public PendingGame createPendingGame(PendingGame game) {

         return null;
     }

     @Override
     public PendingGame getPendingGame() {

         return pendingGame;
     }

     @Override
     public PendingGame joinPendingGame(PendingGame game) {
      return null;
     }

     @Override
     public void leavePendingGame() { }

     @Override
     public Game startPendingGame(PendingGame game) {
      return null;
     }
}
