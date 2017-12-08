package byu.codemonkeys.persistance.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.codemonkeys.persistance.IActiveGameDAO;


public class MockGameDAO implements IActiveGameDAO {
    Map<String, String> games;
    Map<String, List<String>> commands;

    public MockGameDAO() {
        games = new HashMap<>();
        commands = new HashMap<>();
    }

    @Override
    public void saveCommandData(String gameID, String commandJson) {
        if (commands.containsKey(gameID)) {
            commands.get(gameID).add(commandJson);
        } else {
            List<String> commandList = new ArrayList<>();
            commandList.add(commandJson);
            commands.put(gameID, commandList);
        }
    }

    @Override
    public List<String> getAllCommandData(String gameID) {
        return commands.get(gameID);
    }

    @Override
    public void saveGameData(String gameID, String gameJson) {
        games.put(gameID, gameJson);
    }

    @Override
    public String getGameData(String gameID) {
        return games.get(gameID);
    }

    @Override
    public Map<String, String> getAllGameData() {
        return games;
    }

    @Override
    public void deleteGameData(String gameID) {
        games.remove(gameID);
    }

    @Override
    public void clear() {
        games = new HashMap<>();
        commands = new HashMap<>();
    }
}
