package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnexion {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_paiements";
    private static final String USER = "root";
    private static final String PASSWORD = "Latifa11";
    private static Connection connection = null;

    private DatabaseConnexion() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("erreur de connexion : " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("connection farmee");
            } catch (SQLException e) {
                System.err.println("erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }
}
