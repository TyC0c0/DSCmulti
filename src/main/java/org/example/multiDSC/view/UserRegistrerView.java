package org.example.multiDSC.view;

import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.UserRegisterModel;

import javax.swing.*;
import java.awt.*;

/*
    Class to registrer someone who dont have acount
    @autor Alvaro Garcia Lopez, Isaac Requena Santiago
    @version 2.0
 */

public class UserRegistrerView extends JFrame {

    private UserRegisterModel UserRegisterModel;
    private JTextField emailField;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField dniField;
    private JTextField nicknameField;
    private JPasswordField passwordField;
    private JButton cancelButton;
    private JButton registerButton;

    public UserRegistrerView(Manager manager, UserRegisterModel userRegisterModel) {
        
        this.UserRegisterModel = userRegisterModel;
        
        setTitle("Registro de Usuario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.DARK_GRAY); // Fondo gris oscuro

        JLabel titulo = new JLabel(userRegisterModel.getText().get(0));
        titulo.setFont(new Font("Arial", Font.BOLD, 20)); // Aumenta el tamaño del título
        titulo.setForeground(Color.WHITE); // Color del texto del título

        panel.add(titulo);

        emailField = new JTextField(15);
        nameField = new JTextField(15);
        lastNameField = new JTextField(15);
        dniField = new JTextField(15);
        nicknameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        panel.add(createFormRow(userRegisterModel.getText().get(1), emailField));
        panel.add(createFormRow(userRegisterModel.getText().get(2), nameField));
        panel.add(createFormRow(userRegisterModel.getText().get(3), lastNameField));
        panel.add(createFormRow(userRegisterModel.getText().get(4), dniField));
        panel.add(createFormRow(userRegisterModel.getText().get(5), nicknameField));
        panel.add(createFormRow(userRegisterModel.getText().get(6), passwordField));

        registerButton = new JButton(userRegisterModel.getText().get(7));
        registerButton.setBackground(Color.WHITE); // Fondo blanco
        registerButton.setForeground(Color.BLACK); // Letras negras


        cancelButton = new JButton(userRegisterModel.getText().get(8));
        cancelButton.setBackground(Color.WHITE); // Fondo blanco
        cancelButton.setForeground(Color.BLACK); // Letras negras
        //registerButton.addActionListener(new ButtonListenerUserRegistrer(this, emailField, nameField, lastNameField, dniField, nicknameField, passwordField,registerButton,cancelButton));
        //cancelButton.addActionListener(new ButtonListenerUserRegistrer(this, emailField, nameField, lastNameField, dniField, nicknameField, passwordField,registerButton,cancelButton)); // Cierra la ventana al ser pulsado

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY); // Fondo gris oscuro
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton); // Añade el botón de cancelar
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }

    private JPanel createFormRow(String labelText, JTextField textField) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setBackground(Color.DARK_GRAY); // Fondo gris oscuro
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 20));
        label.setForeground(Color.WHITE); // Letras blancas
        textField.setBackground(Color.LIGHT_GRAY); // Fondo gris claro
        textField.setForeground(Color.BLACK); // Letras negras
        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(textField, BorderLayout.CENTER);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return rowPanel;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public void setLastNameField(JTextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public JTextField getDniField() {
        return dniField;
    }

    public void setDniField(JTextField dniField) {
        this.dniField = dniField;
    }

    public JTextField getNicknameField() {
        return nicknameField;
    }

    public void setNicknameField(JTextField nicknameField) {
        this.nicknameField = nicknameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(JButton registerButton) {
        this.registerButton = registerButton;
    }
}
