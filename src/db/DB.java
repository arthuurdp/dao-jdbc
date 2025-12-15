package db;

import java.sql.*;

public class DB {

    private static final String URL = "jdbc:mysql://localhost:3306/coursejdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "arthur0702";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao MySQL: " + e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeConnection(Connection conn, Statement st, ResultSet rs) {
        closeStatement(st);
        closeResultSet(rs);
        closeConnection(conn);
    }
}
