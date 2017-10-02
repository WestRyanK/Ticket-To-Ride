package byu.codemonkeys.tickettoride.shared.model;


public class UserBase {
    protected String userName;

    public UserBase(String userName) {
        this.userName = userName;
    }

    public String getUsername() {
        return userName;
    }
}
