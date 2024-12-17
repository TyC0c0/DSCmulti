package org.example.multiDSC.controller.databaseConection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionBD {

    private static final String DB_URL = "jdbc:postgresql://ep-flat-breeze-a2hszb0f.eu-central-1.aws.neon.tech:5432/ProyectMulti";
    private static final String DB_USER = "ProyectMulti_owner";
    private static final String DB_PASS = "pML4Rfu5ngdi";

    private Connection connection;

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                System.out.println("Conexión completada");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC no encontrado.", e);
            }
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public void insertUser(String insert) throws SQLException {
        try {
            System.out.println("no se ha llegado aqui");
            // Crear un Statement para ejecutar el SQL.
            connect();
            connection.createStatement().executeUpdate(insert);
            System.out.println("Usuario insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            throw e; // Rethrow para manejarlo externamente si es necesario.
        }

    }

}

