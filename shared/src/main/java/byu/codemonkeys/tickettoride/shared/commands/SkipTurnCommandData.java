package byu.codemonkeys.tickettoride.shared.commands;

public class SkipTurnCommandData extends CommandData {
    private String username;

    public SkipTurnCommandData(String username) {
        super(CommandType.END_TURN);
        this.username = username;
    }
}
