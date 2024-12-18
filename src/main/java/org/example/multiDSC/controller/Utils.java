package org.example.multiDSC.controller;

import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.UserRegistrerView;

import javax.swing.*;
import java.awt.*;

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
