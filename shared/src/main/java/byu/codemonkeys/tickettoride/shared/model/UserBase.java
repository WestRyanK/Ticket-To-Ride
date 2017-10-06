package byu.codemonkeys.tickettoride.shared.model;


public class UserBase {
    protected String userName;

    public UserBase(String userName) {
        this.userName = userName;
    }

    public String getUsername() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (this == o) return true;

        if (getClass() != o.getClass()) return false;

        UserBase other = (UserBase) o;

        return userName.equals(other.getUsername());
    }
}
