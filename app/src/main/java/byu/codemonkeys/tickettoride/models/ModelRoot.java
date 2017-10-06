package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observable;

import byu.codemonkeys.tickettoride.shared.model.Session;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * Created by Megan on 10/3/2017.
 */
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

    public void clear() {
        instance = null;
    }

     public UserBase getUser() {
         return this.user;
     }

     public void setUser(UserBase user) {
         this.user = user;
     }

     public List<GameBase> getPendingGames() {
         return pendingGames;
     }

     public GameBase getPendingGame() {
         return pendingGame;
     }

    public void setPendingGame(GameBase game){
        pendingGame = game;
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
