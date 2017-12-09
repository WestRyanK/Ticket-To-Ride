package byu.codemonkeys.persistance.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

abstract class SQLiteDAO {
    protected String table;
    protected String id;

    protected SQLiteDAO(String table, String id) {
        this.table = table;
        this.id = id;
    }

    protected Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:tickettoride.sqlite");
        connection.setAutoCommit(false);
        return connection;
    }

    protected void closeConnection(Connection connection, boolean commit) throws SQLException {
        if (commit) {
            connection.commit();
        } else {
            connection.rollback();
        }

        connection.close();
    }

    protected void closeConnection(Connection connection) throws SQLException {
        closeConnection(connection, true);
    }

    protected Map<String, String> all() {
        try {
            ResultSet results = select();
            Map<String, String> records = new HashMap<>();

            while (results.next()) {
                String id = results.getString(this.id);
                String object = results.getString("data");
                records.put(id, object);
            }

            return records;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String get(String id) {
        try {
            ResultSet results = select(id);

            if (!results.isBeforeFirst()) {
                return null;
            }

            return results.getString("data");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultSet select() throws SQLException {
        return query("select * from " + table);
    }

    protected ResultSet select(String id) throws SQLException {
        return query("select * from " + table + " where " + this.id + " = ?", id);
    }

    protected int insert(String id, String value) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean failed = false;

        try {
            connection = openConnection();
            statement = connection.prepareStatement(
                    "insert into " +  table + " (" + this.id + ", data" + ") values (?, ?)"
            );
            statement.setString(1, id);
            statement.setString(2, value);

            return statement.executeUpdate();
        } catch (SQLException e) {
            failed = true;
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                closeConnection(connection, !failed);
            }
        }
    }

    protected int delete(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = openConnection();
            statement = connection.prepareStatement(
                    "delete from " + table + " where " + this.id + " = ?"
            );
            statement.setString(1, id);
            return statement.executeUpdate();
        } finally {
            if (connection != null) {
                closeConnection(connection);
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    protected void clear(Connection connection) throws SQLException {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("delete from " + table);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    protected void clear() {
        Connection connection = null;

        try {
            connection = openConnection();
            clear(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    closeConnection(connection);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected ResultSet query(String sql, String... args) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = openConnection();
            statement = connection.prepareStatement(sql);

            int i = 1;
            while (i <= args.length) {
                statement.setString(i, args[i - 1]);
                ++i;
            }

            return statement.executeQuery();
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                closeConnection(connection);
            }
        }
    }
}
