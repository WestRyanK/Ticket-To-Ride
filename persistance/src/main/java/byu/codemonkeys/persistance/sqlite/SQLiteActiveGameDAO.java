package byu.codemonkeys.persistance.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.codemonkeys.persistance.IActiveGameDAO;

public class SQLiteActiveGameDAO extends SQLiteDAO implements IActiveGameDAO {
    public SQLiteActiveGameDAO() {
        super("games", "id");
    }

    public SQLiteActiveGameDAO(Connection connection) {
        super("games", "id", connection);
    }

    @Override
    public void saveCommandData(String gameID, String commandJson) {

    }

    @Override
    public List<String> getAllCommandData(String gameID) {
        return null;
    }

    @Override
    public void saveGameData(String gameID, String gameJson) {
        try {
            insert(gameID, gameJson);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getGameData(String gameID) {
        try {
            ResultSet rs = select(gameID);

            if (rs.isBeforeFirst()) {
                return rs.getBlob("data").toString();
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> getAllGameData() {
        try {
            ResultSet results = select();
            Map<String, String> games = new HashMap<>();

            while (results.next()) {
                String id = results.getString("id");
                String data = results.getBlob("data").toString();
                games.put(id, data);
            }

            return games;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            super.clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
