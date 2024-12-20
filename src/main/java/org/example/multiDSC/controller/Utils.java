package org.example.multiDSC.controller;

import org.example.multiDSC.model.controllModels.Manager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils - This is the utilities class.
 *
 * @author Isaac Requena Santiago, Ramón Reina González
 * @version 1.3
 */

public class Utils {

    public String switchLanguage(String language) {
        switch (language) {

            case "espanol":
                return "\"Nom_es\"";

            case "ingles":
                return "\"Nom_en\"";
        }
        return "";
    }

    public static void showErrorWindow(Component parentComponent, String errorMessage, String title) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarningWindow(Component parentComponent, String warningMessage, String title) {
        JOptionPane.showMessageDialog(parentComponent, warningMessage, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfoWindow(Component parentComponent, String infoMessage, String title) {
        JOptionPane.showMessageDialog(parentComponent, infoMessage, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static String showInputDialog(Component parentComponent, String inputMessage, String title) {
        return JOptionPane.showInputDialog(parentComponent, inputMessage , title, JOptionPane.PLAIN_MESSAGE);
    }

    public static int showConfirmDialog(Component parentComponent, String message, String title) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }


    public static void LogRegister(Manager manager, String accion, boolean completado){
        LocalDate currentDate = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = dateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS);
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);

        String query= "INSERT INTO \"LOGS\" (\"Fecha\", \"Hora\", \"Accion\", \"IP\", \"Completado\") VALUES "+
                                          "('"+sqlDate+"', '"+time+"', '"+accion+"', '"+manager.getUserIP()+"', '"+ completado+"')";

        System.out.println("Insertando log...");
        try {
            manager.getConexion().sqlModification(query);
        } catch (SQLException e) {
            System.err.println("Se ha producido un error en la insercion de log "+ e.getMessage());
            Utils.showErrorWindow(null, "Se ha producido un error interno de logs", "Error subiendo log");
        }
    }

    public boolean isValidDNI(String dni) {
        String dniRegex = "^[0-9]{8}[A-Za-z]$";

        return dni.matches(dniRegex);
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (email.matches(emailRegex)) {
            return true;
        } else {
            showErrorWindow(null, "The mail is invalid", "Mail doesn't exist");
            return false;
        }
    }

    public boolean isStrongPassword(Manager manager) {
        String password = new String(manager.getMainController().getRegister().getPasswordField().getPassword());
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (password.matches(passwordRegex)) {
            return true;
        } else {
            showErrorWindow(null, "The password is not enouth strong", "Weak password");
            return false;
        }
    }

    public static void fillUser(Manager manager, String nickname) {
        try {
            String query = "SELECT \"Correo\", \"Contraseña\", \"DNI\", \"id_rol\" FROM \"USUARIO\" WHERE \"Nickname\" = '" + nickname + "'";

            int columnas = 4;  // Número de columnas que vas a seleccionar
            int[] selecciones = {1, 2, 3, 4};  // Seleccionar las columnas DEL QUERY

            HashMap<Object, Object[]> users = manager.getConexion().getValuesFromTables2(query, columnas, selecciones);

            // Ahora puedes acceder al mapa para ver los valores
            for (Map.Entry<Object, Object[]> entry : users.entrySet()) {
                Object key = entry.getKey();
                Object[] values = entry.getValue();

                // Asignar los datos al manager
                manager.setUserNickname(nickname);
                manager.setUserEmail((String) values[0]);
                manager.setUserPassword((String) values[1]);
                //manager.setUserIP(ip);
                manager.setUserDNI((String) values[2]);
                manager.setUserRol((Integer) values[3]);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static void resetUser(Manager manager){
        manager.setUserEmail("");
        manager.setUserPassword("");
        manager.setUserIP("");
        manager.setUserDNI("");
        manager.setUserRol(0);
    }
}
