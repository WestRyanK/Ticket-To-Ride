package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import java.util.List;

/**
 * Created by meganrich on 10/18/17.
 */

public class HistoryResult extends Result {
    private List<CommandData> history;
    public HistoryResult(List<CommandData> history) {
        super();
        for(CommandData command: history) this.history.add(command);
    }
    public HistoryResult(String errorMessage) {
        super(errorMessage);
    }
    public List<CommandData> getHistory(){
        return this.history;
    }
}
