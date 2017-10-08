package byu.codemonkeys.tickettoride.server.model;


import byu.codemonkeys.tickettoride.shared.model.UserBase;

public class User extends UserBase {
    private String password;

    public User(String username, String password) {
        super(username);
        this.password = password;
    }

    public  String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
