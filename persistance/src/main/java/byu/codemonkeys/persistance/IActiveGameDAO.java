package byu.codemonkeys.persistance;


import java.util.List;
import java.util.Map;

public interface IActiveGameDAO {
    void saveCommandData(String gameID, String commandJson);
    List<String> getAllCommandData(String gameID);

    void saveGameData(String gameID, String gameJson);
    String getGameData(String gameID);
    Map<String, String> getAllGameData();

    void clear();
}
