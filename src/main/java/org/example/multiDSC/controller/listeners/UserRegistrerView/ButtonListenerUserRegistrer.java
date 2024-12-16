package org.example.multiDSC.controller.listeners.UserRegistrerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerUserRegistrer implements ActionListener {
    private final JButton cancelButton;
    private final JButton registerButton;
    private JTextField emailField;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField dniField;
    private JTextField nicknameField;
    private JPasswordField passwordField;
    private JFrame frame;

    public ButtonListenerUserRegistrer(JFrame frame, JTextField emailField, JTextField nameField, JTextField lastNameField, JTextField dniField, JTextField nicknameField, JPasswordField passwordField, JButton registerButton, JButton cancelButton) {
        this.frame = frame;
        this.emailField = emailField;
        this.nameField = nameField;
        this.lastNameField = lastNameField;
        this.dniField = dniField;
        this.nicknameField = nicknameField;
        this.passwordField = passwordField;
        this.registerButton = registerButton;
        this.cancelButton = cancelButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (registerButton == e.getSource()) {
            System.out.println("Correo: " + emailField.getText());
            System.out.println("Nombre: " + nameField.getText());
            System.out.println("Apellidos: " + lastNameField.getText());
            System.out.println("DNI: " + dniField.getText());
            System.out.println("Nickname: " + nicknameField.getText());
            System.out.println("Contraseña: " + new String(passwordField.getPassword()));
        } else if (cancelButton == e.getSource()) {
            frame.dispose();
        }
    }
}
