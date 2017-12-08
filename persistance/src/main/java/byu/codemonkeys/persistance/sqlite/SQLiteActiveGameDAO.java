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
        super();
    }

    public SQLiteActiveGameDAO(Connection connection) {
        super(connection);
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

    }

    @Override
    public String getGameData(String gameID) {
        try {
            ResultSet rs = select("games", "id", gameID);

            if (rs.isBeforeFirst()) {
                return rs.getBlob("data").toString();
            }

            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Map<String, String> getAllGameData() {
        try {
            ResultSet rs = select("games", null, null);
            Map<String, String> games = new HashMap<>();

            while (rs.next()) {
                String id = rs.getString("id");
                String data = rs.getString("data");
                games.put(id, data);
            }

            return games;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void clear() {
        super.clear("games");
    }
}
