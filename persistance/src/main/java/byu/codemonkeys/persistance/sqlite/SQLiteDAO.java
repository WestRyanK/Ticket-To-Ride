package byu.codemonkeys.persistance.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class SQLiteDAO {
    protected Connection connection;
    protected String table;
    protected String id;

    protected SQLiteDAO(String table, String id) {
        this.table = table;
        this.id = id;
    }

    protected SQLiteDAO(String table, String id, Connection connection) {
        this(table, id);
        this.connection = connection;
    }

    protected void openConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:tickettoride.sqlite");
        connection.setAutoCommit(false);
    }

    protected void closeConnection() throws SQLException {
        connection.commit();
        connection.close();
    }

    protected ResultSet select() throws SQLException {
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            statement = connection.prepareStatement("select * from " + table);
            return statement.executeQuery();
        } finally {
            if (results != null) {
                results.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    protected ResultSet select(String value) throws SQLException {
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            statement = connection.prepareStatement(
                    "select * from " + table + " where " + id + "=?"
            );
            statement.setString(1, value);
            return statement.executeQuery();
        } finally {
            if (results != null) {
                results.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    protected int insert(String id, String value) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                    "insert into " + table + " values (?, ?)"
            );
            statement.setString(1, id);
            statement.setString(2, value);
            return statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    protected void clear() throws SQLException {
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
}
