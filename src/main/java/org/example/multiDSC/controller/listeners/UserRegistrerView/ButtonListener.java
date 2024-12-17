package org.example.multiDSC.controller.listeners.UserRegistrerView;

import org.example.multiDSC.controller.MainController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ButtonListener implements ActionListener {
    private final MainController mainController;

    public ButtonListener(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainController.getRegister().getRegisterButton() == e.getSource()) {

            String mail = mainController.getRegister().getEmailField().getText();
            System.out.println("Correo: " + mail);

            String name = mainController.getRegister().getNameField().getText();
            System.out.println("Nombre: " + name);

            String lastName = mainController.getRegister().getLastNameField().getText();
            System.out.println("Apellidos: " + lastName);

            String dni = mainController.getRegister().getDniField().getText();
            System.out.println("DNI: " + dni);

            String nickname = mainController.getRegister().getNicknameField().getText();
            System.out.println("Nickname: " + nickname);

            String password = new String(mainController.getRegister().getPasswordField().getPassword());
            System.out.println("Contraseña: " + password);

            String insertUserSentence = "INSERT INTO \"public\".\"USUARIO\" (\"Correo\", \"Nombre\", \"Apellidos\", \"DNI\", \"Nickname\", \"Contraseña\", \"id_rol\") " +
                    "VALUES ('" + mail + "', '" + name + "', '" + lastName + "', '" + dni + "', '" + nickname + "', '" + password + "', " + 1 + ");";


            try {
                mainController.getManager().getConexion();
                mainController.getManager().getConexion().sqlModification(insertUserSentence);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (mainController.getRegister().getCancelButton()== e.getSource()) {
            mainController.getRegister().dispose();
        }
    }
}
