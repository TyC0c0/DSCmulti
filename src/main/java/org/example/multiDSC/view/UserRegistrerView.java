package org.example.multiDSC.view;

import org.example.multiDSC.controller.listeners.UserRegistrerView.ButtonListenerUserRegistrer;
import org.example.multiDSC.model.UserRegistrerModel_en;

import javax.swing.*;
import java.awt.*;

public class UserRegistrerView extends JFrame {

    private JTextField emailField;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField dniField;
    private JTextField nicknameField;
    private JPasswordField passwordField;

    public UserRegistrerView() {
        setTitle("Registro de Usuario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.DARK_GRAY); // Fondo gris oscuro

        UserRegistrerModel_en userRegistrerModelEn = new UserRegistrerModel_en();

        JLabel titulo = new JLabel(userRegistrerModelEn.getLabelTexts().get(0));
        titulo.setFont(new Font("Arial", Font.BOLD, 20)); // Aumenta el tamaño del título
        titulo.setForeground(Color.WHITE); // Color del texto del título

        panel.add(titulo);

        emailField = new JTextField(15);
        nameField = new JTextField(15);
        lastNameField = new JTextField(15);
        dniField = new JTextField(15);
        nicknameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        panel.add(createFormRow(userRegistrerModelEn.getLabelTexts().get(1), emailField));
        panel.add(createFormRow(userRegistrerModelEn.getLabelTexts().get(2), nameField));
        panel.add(createFormRow(userRegistrerModelEn.getLabelTexts().get(3), lastNameField));
        panel.add(createFormRow(userRegistrerModelEn.getLabelTexts().get(4), dniField));
        panel.add(createFormRow(userRegistrerModelEn.getLabelTexts().get(5), nicknameField));
        panel.add(createFormRow(userRegistrerModelEn.getLabelTexts().get(6), passwordField));

        JButton registerButton = new JButton(userRegistrerModelEn.getLabelTexts().get(7));
        registerButton.setBackground(Color.WHITE); // Fondo blanco
        registerButton.setForeground(Color.BLACK); // Letras negras


        JButton cancelButton = new JButton(userRegistrerModelEn.getLabelTexts().get(8));
        cancelButton.setBackground(Color.WHITE); // Fondo blanco
        cancelButton.setForeground(Color.BLACK); // Letras negras
        registerButton.addActionListener(new ButtonListenerUserRegistrer(this, emailField, nameField, lastNameField, dniField, nicknameField, passwordField,registerButton,cancelButton));
        cancelButton.addActionListener(new ButtonListenerUserRegistrer(this, emailField, nameField, lastNameField, dniField, nicknameField, passwordField,registerButton,cancelButton)); // Cierra la ventana al ser pulsado

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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(UserRegistrerView::new);
        //maincontroller.addUserRegisterListener
    }
}