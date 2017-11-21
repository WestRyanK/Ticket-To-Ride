package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.GameSummary;

public class GameOverCommandData extends CommandData {
    protected GameSummary summary;

    public GameOverCommandData(GameSummary summary) {
        super(CommandType.GAME_OVER);
        this.summary = summary;
    }
}
