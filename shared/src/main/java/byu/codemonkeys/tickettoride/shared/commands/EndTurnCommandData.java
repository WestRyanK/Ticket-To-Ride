package byu.codemonkeys.tickettoride.shared.commands;

public class EndTurnCommandData extends CommandData {
    private String username;

    public EndTurnCommandData(String username) {
        super(CommandType.END_TURN);
        this.username = username;
    }
}
