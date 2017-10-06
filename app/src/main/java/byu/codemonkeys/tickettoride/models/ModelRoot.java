package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observable;

import byu.codemonkeys.tickettoride.shared.model.Session;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * Created by Megan on 10/3/2017.
 */
//TODO(compy-386): clear models on logout via client and logout from server
//TODO(compy-386): throw proper errors for invalid input on login and register, also invalid authorization
 public class ModelRoot extends Observable {
    private static ModelRoot instance;
    private UserBase user;
    private GameBase pendingGame;
    private List<GameBase> pendingGames;
    private Session session;
    private GameBase game;

    private ModelRoot(){}

    public static ModelRoot getInstance() {
        if(instance == null) {
            instance = new ModelRoot();
        }
        return instance;
    }

     public UserBase getUser() {
         return this.user;
     }

     public void setUser(UserBase user) {
         this.user = user;
     }

     public List<GameBase> getPendingGames() {
         return this.pendingGames;
         //TODO(compy-386): fix to work with poller
     }

     public GameBase getPendingGame() {
         return this.pendingGame;
     }

    public void setPendingGame(GameBase game){
        this.pendingGame = game;
    }

     public void setPendingGames(List<GameBase> games){
         this.pendingGames = games;
         setChanged();
         notifyObservers();
     }

     public Session getSession(){
         return this.session;
     }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setGame(GameBase game) {
        this.game = game;
    }

    public GameBase getGame() {
        return this.game;
    }

}
