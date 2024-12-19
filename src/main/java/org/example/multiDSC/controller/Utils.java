package org.example.multiDSC.controller;

import org.example.multiDSC.model.controllModels.Manager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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

    public static int showConfirmDialog(Component parentComponent, String message, String title) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, JOptionPane.YES_NO_OPTION);
    }

    public static void LogRegister(Manager manager, String accion, boolean completado){
        LocalDate currentDate = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = dateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS);
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);

        String query= "INSERT INTO \"LOGS\" (\"Fecha\", \"Hora\", \"Accion\", \"IP\", \"Completado\") VALUES "+
                                          "('"+sqlDate+"', '"+time+"', '"+accion+"', '"+manager.getUserIP()+"', '"+ completado+"')";

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
        //LO HE CAMBIADO PORQUE ME DABA ERROR EL ANTERIOR
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (password.matches(passwordRegex)) {
            return true;
        } else {
            showErrorWindow(null, "The password is not enouth strong", "Weak password");
            return false;
        }
}
}
