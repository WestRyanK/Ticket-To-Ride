package byu.codemonkeys.tickettoride.shared.model;

import java.util.ArrayList;
import java.util.List;

public class GameSummary {
    private String winner;
    private List<EndGamePlayerStats> summaries;

    public GameSummary() {
        summaries = new ArrayList<>();
    }

    public List<EndGamePlayerStats> getSummaries() {
        return summaries;
    }

    public void addSummary(EndGamePlayerStats summary) {
        summaries.add(summary);
    }

    public String getWinner() {
        return winner;
    }

    public void calculateWinner() {
        EndGamePlayerStats high = summaries.get(0);
        int numHigh = 1;

        for (EndGamePlayerStats summary : summaries) {
            int summaryTotalScore = summary.getTotalScore();
            int highTotalScore = high.getTotalScore();

            if (summaryTotalScore == highTotalScore) {
                ++numHigh;
                continue;
            }

            if (summaryTotalScore > highTotalScore) {
                high = summary;
                numHigh = 1;
            }
        }

        winner = high.getUsername();
    }
}
