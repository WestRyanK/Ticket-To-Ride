package byu.codemonkeys.persistance.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class SQLiteCommandDAO extends SQLiteDAO {
    public SQLiteCommandDAO() {
        super("commands", "game");
    }

    public void save(String game, String json) {
        try {
            insert(game, json);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> all(String game) {
        List<String> commands = new ArrayList<>();

        try {
            ResultSet results = query(
                    "select * from " +  table + " where " + id + " = ? order by index asc",
                    game
            );

            while (results.next()) {
                commands.add(results.getString("data"));
            }

            return commands;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        super.clear();
    }
}
