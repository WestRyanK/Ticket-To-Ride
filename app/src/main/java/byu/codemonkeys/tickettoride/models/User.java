package byu.codemonkeys.tickettoride.models;

/**
 * Created by Megan on 10/3/2017.
 */

 public class User {
    private String userName;
    private String userID;

    public String getUserName() {
        return userName;
    }

    public String getUserID() {
        return userID;
    }
    
    public void setUserName(String userName) {
        
        this.userName = userName;
    }
}
