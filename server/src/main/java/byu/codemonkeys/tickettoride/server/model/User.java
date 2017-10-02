package byu.codemonkeys.tickettoride.server.model;


import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class User extends UserBase {
    private String username;
    private String password;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public  String getPassword() {
        return password;
    }
}
