package org.example.multiDSC.controller.listeners.UserRegistrerView;

import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.controllModels.SqlSentences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

public class ButtonsListener implements ActionListener {
    private final Manager manager;

    public ButtonsListener(Manager manager) {

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
                manager.getMainController().getRegister().dispose();
                manager.getMainController().getLogin().setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (manager.getMainController().getRegister().getCancelButton() == e.getSource()) {
            manager.getMainController().getRegister().dispose();
            manager.getMainController().getLogin().setVisible(true);
        }
    }


    public boolean checkTextfields() {
        boolean hasErrors = false;
        StringBuilder errorMessages = new StringBuilder();

        String email = manager.getUserEmail().trim();
        String dni = manager.getUserDNI().trim();

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
        if (email.isEmpty()) {
            errorMessages.append("Debes completar el campo Email.\n");
            hasErrors = true;
        } else if (!manager.getMainController().getUtils().isValidEmail(email)) {
            errorMessages.append("El formato del Email no es válido.\n");
            hasErrors = true;
        } else if (isEmailRegistered(email)) {
            errorMessages.append("El correo ya está registrado.\n");
            hasErrors = true;
        }
        if (dni.isEmpty()) {
            errorMessages.append("Debes completar el campo DNI.\n");
            hasErrors = true;
        } else if (!manager.getMainController().getUtils().isValidDNI(dni)) {
            errorMessages.append("El formato del DNI no es válido.\n");
            hasErrors = true;
        }
        if (manager.getMainController().getRegister().getNicknameField().getText().trim().isEmpty()) {
            errorMessages.append("Debes completar el campo Nickname.\n");
            hasErrors = true;
        }

        if (hasErrors) {
            Utils.showErrorWindow(null, errorMessages.toString(), "Errores en el formulario");
        }

        return !hasErrors; // Retorna true si no hay errores
    }
    private boolean isEmailRegistered(String email) {
        SqlSentences sqlSentences = new SqlSentences();
        String sentence = sqlSentences.getSencences().get(1);
        try {
            Map<Integer, Object> mails = manager.getConexion().lecturaSQL(sentence);
            for (Object mail : mails.values()) {
                if (email.equals(mail.toString().trim())) {
                    return true; // El correo ya existe
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al validar el correo: " + ex.getMessage(), ex);
        }
        return false; // El correo no existe
    }



}
