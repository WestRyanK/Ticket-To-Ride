package byu.codemonkeys.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import byu.codemonkeys.persistance.IActiveGameDAO;
import byu.codemonkeys.persistance.IPersistanceProvider;
import byu.codemonkeys.persistance.ISessionDAO;
import byu.codemonkeys.persistance.IUserDAO;

public class SQLiteProvider implements IPersistanceProvider {
    @Override
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            createTables();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            Connection connection = openConnection();

            new SQLiteActiveGameDAO().clear(connection);
            new SQLiteSessionDAO().clear(connection);
            new SQLiteUserDAO().clear(connection);

            closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IActiveGameDAO newActiveGameDAO() {
        return new SQLiteActiveGameDAO();
    }

    @Override
    public ISessionDAO newSessionDAO() {
        return new SQLiteSessionDAO();
    }

    @Override
    public IUserDAO newUserDAO() {
        return new SQLiteUserDAO();
    }

    private Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:tickettoride.sqlite");
        connection.setAutoCommit(false);
        return connection;
    }

    private void closeConnection(Connection connection) throws SQLException {
        connection.commit();
        connection.close();
    }

    private void createTables() throws SQLException {
        Connection connection = openConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();

            StringBuilder sb = new StringBuilder();

            sb.append("create table if not exists games (" +
                        "id varchar(255) PRIMARY KEY," +
                        "data blob" +
                        ");\n");
            sb.append("create table if not exists sessions (" +
                        "token varchar(255) PRIMARY KEY," +
                        "data blob" +
                        ");\n");
            sb.append("create table if not exists users (" +
                        "username varchar(255) PRIMARY KEY," +
                        "data blob" +
                        ");\n");
            sb.append("create table if not exists commands (" +
                        "idx integer PRIMARY KEY AUTOINCREMENT," +
                        "game varchar(255)," +
                        "data blob" +
                        ");");

            statement.executeUpdate(sb.toString());
        } finally {
            if (statement != null) {
                statement.close();
            }

            closeConnection(connection);
        }
    }
}
