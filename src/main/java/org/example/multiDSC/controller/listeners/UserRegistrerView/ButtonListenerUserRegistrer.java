package org.example.multiDSC.controller.listeners.UserRegistrerView;

import org.example.multiDSC.controller.databaseConection.ConectionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ButtonListenerUserRegistrer implements ActionListener {
    private final JButton cancelButton;
    private final JButton registerButton;
    private final ConectionBD conectionBD =new ConectionBD();;
    private JTextField emailField;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField dniField;
    private JTextField nicknameField;
    private JPasswordField passwordField;
    private JFrame frame;

    public ButtonListenerUserRegistrer(JFrame frame, JTextField emailField, JTextField nameField, JTextField lastNameField, JTextField dniField, JTextField nicknameField, JPasswordField passwordField, JButton registerButton, JButton cancelButton, ConectionBD conectionBD) {
        this.frame = frame;
        this.emailField = emailField;
        this.nameField = nameField;
        this.lastNameField = lastNameField;
        this.dniField = dniField;
        this.nicknameField = nicknameField;
        this.passwordField = passwordField;
        this.registerButton = registerButton;
        this.cancelButton = cancelButton;
      //  this.conectionBD = conectionBD;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (registerButton == e.getSource()) {
            String mail = emailField.getText();
            System.out.println("Correo: " + mail);

            String name = nameField.getText();
            System.out.println("Nombre: " + name);

            String lastName = lastNameField.getText();
            System.out.println("Apellidos: " + lastName);

            String dni = dniField.getText();
            System.out.println("DNI: " + dni);

            String nickname = nicknameField.getText();
            System.out.println("Nickname: " + nickname);

            String password = new String(passwordField.getPassword());
            System.out.println("Contraseña: " + password);

            String insertUserSentence = "INSERT INTO \"public\".\"USUARIO\" (\"Correo\", \"Nombre\", \"Apellidos\", \"DNI\", \"Nickname\", \"Contraseña\", \"id_rol\") " +
                    "VALUES ('" + mail + "', '" + name + "', '" + lastName + "', '" + dni + "', '" + nickname + "', '" + password + "', " + 1 + ");";


            try {
                conectionBD.insertUser(insertUserSentence);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (cancelButton == e.getSource()) {
            frame.dispose();
        }
    }
}
