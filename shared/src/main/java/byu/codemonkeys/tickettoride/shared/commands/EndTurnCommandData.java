package byu.codemonkeys.tickettoride.shared.commands;

/**
 * Created by meganrich on 11/17/17.
 */

public class EndTurnCommandData extends CommandData {
    public EndTurnCommandData(String userName){
        super(CommandType.END_TURN);
        this.userName = userName;
    }

    protected String userName;
}
