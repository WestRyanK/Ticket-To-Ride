package byu.codemonkeys.tickettoride.shared.model;


public class UserBase {
    protected String userName;

    public UserBase(String userName) {
        this.userName = userName;
    }

    public String getUsername() {
        return userName;
    }
    
    public static boolean isValidUsername(String username)
    {
       if (username == null || username.isEmpty() || username.contains(" ") || username.length() < 8)
           return false;
        else
            return true;
    }
    
    public static boolean isValidPassword(String password)
    {
        if (password == null || password.isEmpty() || password.length() < 8)
            return false;
        else
            return true;
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
