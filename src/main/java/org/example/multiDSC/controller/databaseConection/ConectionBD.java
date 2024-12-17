package org.example.multiDSC.controller.databaseConection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

    public Map<Integer, Object> lecturaSQL(String query) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            Map<Integer, Object> result = new HashMap<>();
            int iteracion = 1;

            while (resultSet.next()) {
                // Se agregan todos los tipos de columna posibles
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    result.put(iteracion, resultSet.getObject(i));
                    iteracion++;
                }
            }
            return result;

        } catch (SQLException e) {
            // Solo se registra el error para fines de logging
            System.err.println("Error en la consulta SQL: " + e.getMessage());

            // Propagar la excepción para que el método que lo llama pueda manejarla
            throw new SQLException("Error en la lectura de datos: ", e.getMessage());
        } finally {
            // Cerramos los recursos de manera segura en el bloque finally
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                System.err.println("Error cerrando los recursos: " + e.getMessage());
            }
        }
    }

    public void sqlModification(String query) throws SQLException {
        Statement statement = null;
        try {
            // Crear la sentencia y ejecutar la consulta
            statement = connection.createStatement();
            int filasAfectadas = statement.executeUpdate(query);

            System.out.println("Inserción completada. Filas afectadas: " + filasAfectadas);


        } catch (SQLException e) {
            // Registro del error y propagación
            System.err.println("Error en la inserción SQL: " + e.getMessage());
            throw new SQLException("Error al insertar datos: ", e);

        } finally {
            // Cerramos los recursos de manera segura
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                System.err.println("Error cerrando los recursos: " + e.getMessage());
            }
        }
    }
    public void getValuesFromTables(){
        Statement statement = null;
        try {
            statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

