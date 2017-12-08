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
    protected Connection connection;

    protected SQLiteDAO() {

    }

    protected SQLiteDAO(Connection connection) {
        this.connection = connection;
    }

    protected void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tickettoride.sqlite");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection() {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected ResultSet select(String table, String id, String value) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(table);

            if (id != null) {
                sb.append(id);
                sb.append(" = ? ");
            }

            stmt = connection.prepareStatement(sb.toString());

            if (id != null) {
                stmt.setString(1, value);
            }

            rs = stmt.executeQuery();

            return rs;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        }
    }

    protected void clear(String table) {
        try {
            Statement stmt = null;

            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("delete from " + table);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
