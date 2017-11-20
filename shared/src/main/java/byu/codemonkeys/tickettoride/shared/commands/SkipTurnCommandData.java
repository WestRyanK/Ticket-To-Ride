package byu.codemonkeys.tickettoride.shared.commands;

public class SkipTurnCommandData extends CommandData {
    protected String username;

    public SkipTurnCommandData(String username) {
        super(CommandType.SKIP_TURN);
        this.username = username;
    }
}
