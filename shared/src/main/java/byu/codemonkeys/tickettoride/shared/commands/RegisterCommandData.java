package byu.codemonkeys.tickettoride.shared.commands;

public class RegisterCommandData extends Command {
    private String userName;
    private String password;

    public RegisterCommandData(String userName, String password) {
        super("Register");
        this.userName = userName;
        this.password = password;
    }
}
