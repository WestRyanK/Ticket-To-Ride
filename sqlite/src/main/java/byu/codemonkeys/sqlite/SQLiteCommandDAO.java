package byu.codemonkeys.sqlite;

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
            List<Result> results = query(
                    "select * from " +  table + " where " + id + " = ? order by idx asc",
                    game
            );

            for (Result result : results) {
                commands.add(result.data());
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
