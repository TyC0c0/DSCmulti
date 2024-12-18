package org.example.multiDSC.controller.listeners.UserRegistrerView;

import org.example.multiDSC.controller.MainController;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.model.controllModels.Manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ButtonListener implements ActionListener {
    private final Manager manager;

    public ButtonListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (manager.getMainController().getRegister().getRegisterButton() == e.getSource()) {
            if (!checkTextfields()) {
                return; // Detiene la ejecución si hay errores
            }

            // Si no hay errores, procede con la inserción en la base de datos
            String mail = manager.getMainController().getRegister().getEmailField().getText();
            String name = manager.getMainController().getRegister().getNameField().getText();
            String lastName = manager.getMainController().getRegister().getLastNameField().getText();
            String dni = manager.getMainController().getRegister().getDniField().getText();
            String nickname = manager.getMainController().getRegister().getNicknameField().getText();
            String password = new String(manager.getMainController().getRegister().getPasswordField().getPassword());

            String insertUserSentence = "INSERT INTO \"public\".\"USUARIO\" (\"Correo\", \"Nombre\", \"Apellidos\", \"DNI\", \"Nickname\", \"Contraseña\", \"id_rol\") " +
                    "VALUES ('" + mail + "', '" + name + "', '" + lastName + "', '" + dni + "', '" + nickname + "', '" + password + "', " + 1 + ");";

            try {
                manager.getConexion().sqlModification(insertUserSentence);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (manager.getMainController().getRegister().getCancelButton() == e.getSource()) {
            manager.getMainController().getRegister().dispose();
        }
    }


    public boolean checkTextfields() {
        boolean hasErrors = false;
        StringBuilder errorMessages = new StringBuilder();
        String dni = manager.getMainController().getRegister().getDniField().getText();

        if (manager.getMainController().getRegister().getNameField().getText().trim().isEmpty()) {
            errorMessages.append("Debes completar el campo Nombre.\n");
            hasErrors = true;
        }
        if (manager.getMainController().getRegister().getLastNameField().getText().trim().isEmpty()) {
            errorMessages.append("Debes completar el campo Apellido.\n");
            hasErrors = true;
        }
        if (manager.getMainController().getRegister().getPasswordField().getPassword().length == 0) {
            errorMessages.append("Debes completar el campo Contraseña.\n");
            hasErrors = true;
        }
        String email = manager.getMainController().getRegister().getEmailField().getText();
        if (email.trim().isEmpty()) {
            errorMessages.append("Debes completar el campo Email.\n");
            hasErrors = true;
        }
        if (!manager.getMainController().getUtils().isValidEmail(email)) {
            errorMessages.append("El formato del Email no es válido.\n");
            hasErrors = true;
        }
        if (dni.trim().isEmpty()) {
            errorMessages.append("Debes completar el campo DNI.\n");
            hasErrors = true;
        }
        if (!manager.getMainController().getUtils().isValidDNI(dni)) {
            errorMessages.append("El formato del DNI no es válido.\n");
            hasErrors = true;
        }
        if (manager.getMainController().getRegister().getNicknameField().getText().trim().isEmpty()) {
            errorMessages.append("Debes completar el campo Nickname.\n");
            hasErrors = true;
        }

        // Mostrar todos los errores acumulados en una sola ventana
        if (hasErrors) {
            Utils.showErrorWindow(null, errorMessages.toString(), "Errores en el formulario");
        }

        return !hasErrors; // Retorna true si no hay errores
    }

}
