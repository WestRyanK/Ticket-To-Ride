package byu.codemonkeys.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            List<Result> results = select();
            Map<String, String> records = new HashMap<>();

            for (Result result : results) {
                records.put(result.id(), result.data());
            }

            return records;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String get(String id) {
        try {
            List<Result> results = select(id);

            if (results.isEmpty()) {
                return null;
            }

            return results.get(0).data();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<Result> select() throws SQLException {
        return query("select * from " + table);
    }

    protected List<Result> select(String id) throws SQLException {
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

    protected void update(String id, String value) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = openConnection();
            statement = connection.prepareStatement(
                    "update " + table + " set data = ? where " + this.id + " = ?"
            );
            statement.setString(1, value);
            statement.setString(2, id);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                closeConnection(connection);
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
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                closeConnection(connection);
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

    protected List<Result> query(String sql, String... args) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Result> results = new ArrayList<>();

        try {
            connection = openConnection();
            statement = connection.prepareStatement(sql);

            int i = 1;
            while (i <= args.length) {
                statement.setString(i, args[i - 1]);
                ++i;
            }

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String id = rs.getString(this.id);
                String data = rs.getString("data");
                results.add(new Result(id, data));
            }

            return results;
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
