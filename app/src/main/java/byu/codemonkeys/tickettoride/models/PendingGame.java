package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

/**
 * Created by Ryan on 9/29/2017.
 */

public class PendingGame {
    private String gameID;
    private String gameName;
    private ArrayList<User> users;
    private User gameOwner;

    public String getGameID(){
        return gameID;
    }

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String name){
        this.gameName = name;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public User getGameOwner(){
        return gameOwner;
    }
}
