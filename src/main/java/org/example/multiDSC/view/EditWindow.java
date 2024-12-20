package org.example.multiDSC.view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class EditWindow extends  JFrame{
    private final Map<String, String> userData;
    private final int userId;
    private JButton applyButton;
    private JTextField nameField;
    private JTextField emailField;
    private String rol;
    private JComboBox<String> roleComboBox;

    public EditWindow(Map<String, String> userData, int userId) {
        this.userData = userData;
        this.userId = userId;
        // Crear la ventana de edición

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel de la ventana
        JPanel panel = new JPanel(new GridLayout(4, 2));

        // Campos de texto
        nameField = new JTextField(userData.get("Nombre"));
        emailField = new JTextField(userData.get("Correo"));
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Usuario"});
        roleComboBox.setSelectedItem(userData.get("Rol_Nombre"));
        rol = userData.get("Rol_Nombre");
        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);
        panel.add(new JLabel("Correo:"));
        panel.add(emailField);
        panel.add(new JLabel("Rol:"));
        panel.add(roleComboBox);

        // Botón para aplicar la modificación
        applyButton = new JButton("Aplicar");

        panel.add(applyButton);
        add(panel);
        //setVisible(true);
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public void setApplyButton(JButton applyButton) {
        this.applyButton = applyButton;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public void setRoleComboBox(JComboBox<String> roleComboBox) {
        this.roleComboBox = roleComboBox;
    }
}
