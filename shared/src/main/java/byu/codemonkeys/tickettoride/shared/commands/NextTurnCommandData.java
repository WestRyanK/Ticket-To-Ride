package byu.codemonkeys.tickettoride.shared.commands;

public class NextTurnCommandData extends CommandData {
    protected String username;

    public NextTurnCommandData(String username) {
        super(CommandType.NEXT_TURN);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
