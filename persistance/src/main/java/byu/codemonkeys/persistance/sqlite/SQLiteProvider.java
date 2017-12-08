package byu.codemonkeys.persistance.sqlite;

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        createTables();
    }

    @Override
    public void clear() {
        Connection connection = openConnection();

        new SQLiteActiveGameDAO(connection).clear();
        new SQLiteSessionDAO(connection).clear();
        new SQLiteUserDAO(connection).clear();

        closeConnection(connection);
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

    private Connection openConnection() {
        Connection connection;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tickettoride.sqlite");
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        Connection connection = openConnection();

        try {
            Statement stmt = null;

            try {
                stmt = connection.createStatement();

                StringBuilder sb = new StringBuilder();

                sb.append("create table if not exists games (" +
                        "id varchar(255) PRIMARY KEY," +
                        "data blob" +
                        ")\n");
                sb.append("create table if not exists sessions (" +
                        "token varchar(255) PRIMARY KEY," +
                        "data blob" +
                        ")\n");
                sb.append("create table if not exists users (" +
                        "username varchar(255) PRIMARY KEY," +
                        "data blob" +
                        ")");

                stmt.executeUpdate(sb.toString());
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
