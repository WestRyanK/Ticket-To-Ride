package byu.codemonkeys.sqlite;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import byu.codemonkeys.persistance.IActiveGameDAO;

public class SQLiteActiveGameDAO extends SQLiteDAO implements IActiveGameDAO {
    private SQLiteCommandDAO commandDAO;

    public SQLiteActiveGameDAO() {
        super("games", "id");
        commandDAO = new SQLiteCommandDAO();
    }

    @Override
    public void saveCommandData(String gameID, String commandJson) {
        commandDAO.save(gameID, commandJson);
    }

    @Override
    public List<String> getAllCommandData(String gameID) {
        return commandDAO.all(gameID);
    }

    @Override
    public void saveGameData(String gameID, String gameJson) {
        try {
            String data = get(gameID);

            if (data == null) {
                insert(gameID, gameJson);
            } else {
                update(gameID, gameJson);
                commandDAO.delete(gameID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getGameData(String gameID) {
        return get(gameID);
    }

    @Override
    public Map<String, String> getAllGameData() {
        return all();
    }

    @Override
    public void deleteGameData(String gameID) {
        try {
            delete(gameID);
            commandDAO.delete(gameID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        super.clear();
        commandDAO.clear();
    }


}
