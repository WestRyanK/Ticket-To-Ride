package byu.codemonkeys.persistance.sqlite;

import java.sql.Connection;

class SQLiteCommandDAO extends SQLiteDAO {
    public SQLiteCommandDAO() {
        super("commands", "index");
    }

    public SQLiteCommandDAO(Connection connection) {
        super("commands", "index", connection);
    }
}
