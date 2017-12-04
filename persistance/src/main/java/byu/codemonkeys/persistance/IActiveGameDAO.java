package byu.codemonkeys.persistance;


import java.util.List;
import java.util.Map;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.model.ActiveGame;

public interface IActiveGameDAO {
    void saveCommand(String gameID, CommandData command);
    List<CommandData> getCommands(String gameID);

    void saveGame(String gameID, ActiveGame game);
    ActiveGame getGame(String gameID);
    Map<String, ActiveGame> getAllGames();

    void clear();
}
