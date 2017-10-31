package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import java.util.List;

public class HistoryResult extends Result {
    private List<CommandData> history;

    public HistoryResult(List<CommandData> history) {
        super();
        this.history = history;
    }

    public HistoryResult(String errorMessage) {
        super(errorMessage);
    }
    public List<CommandData> getHistory(){
        return this.history;
    }
}
