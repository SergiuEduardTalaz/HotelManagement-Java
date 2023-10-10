package ro.fortech.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        //        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "root");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotelmanagement", "postgres", "sergiu");
    }
}
