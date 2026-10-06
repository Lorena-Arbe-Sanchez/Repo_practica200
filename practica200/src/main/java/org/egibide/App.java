package org.egibide;

import org.egibide.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {

        try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection conexion = db.getConnection();

            if (conexion != null && !conexion.isClosed()) {
                System.out.println("Conexión correcta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
