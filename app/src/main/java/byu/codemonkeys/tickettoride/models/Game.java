package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

/**
 * Created by Megan on 10/3/2017.
 */

 public class Game {
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
