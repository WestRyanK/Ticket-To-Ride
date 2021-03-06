package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;

import java.util.ArrayList;
import java.util.List;
public class HistoryResult extends Result {
    private final List<CommandData> history;

    public HistoryResult(List<CommandData> history) {
        super();
        this.history = history;
    }

    public HistoryResult(String errorMessage) {
        super(errorMessage);
        this.history = null;
    }
    public List<CommandData> getHistory(){
        return this.history;
    }
}
