package org.example.multiDSC.view;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ConfAdminView extends JFrame {
    @Getter
    @Setter
    private JPanel fieldsPanel;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton applyButton;
    private boolean botonAñadido = false;

    public ConfAdminView() {
        setTitle("Configuración de Admin");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.DARK_GRAY);

        JPanel labelsPanel = new JPanel(new GridLayout(1, 10, 10, 10));
        labelsPanel.setBackground(Color.DARK_GRAY);
        String[] etiquetas = {"Nombre:", "Correo:", "Rol:"};

        for (String etiqueta : etiquetas) {
            JLabel label = new JLabel(etiqueta, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            labelsPanel.add(label);
        }

        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.add(labelsPanel);

        JScrollPane scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        ConectionBD conectionBD = new ConectionBD();
        ArrayList<Map<String, String>> userDataList = new ArrayList<>();

        try {
            conectionBD.connect();
            userDataList = conectionBD.getUserData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (Map<String, String> userData : userDataList) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 10, 10, 10));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            rowPanel.setBackground(Color.DARK_GRAY);

            JTextField nombreField = createTextField(userData.get("Nombre"));
            JTextField correoField = createTextField(userData.get("Correo"));
            JTextField rolField = createTextField(userData.get("Rol_Nombre"));

            rowPanel.add(nombreField);
            rowPanel.add(correoField);
            rowPanel.add(rolField);

            fieldsPanel.add(rowPanel);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(Color.WHITE);
            fieldsPanel.add(separator);
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topButtonPanel.setBackground(Color.DARK_GRAY);

        editarButton = new JButton("Modify");
        eliminarButton = new JButton("Delete");

        topButtonPanel.add(editarButton);
        topButtonPanel.add(eliminarButton);

        mainPanel.add(topButtonPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(Color.DARK_GRAY);

        JTextField extraTextField = new JTextField(20);
        extraTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton extraButton = new JButton("Search");
        bottomPanel.add(extraTextField);
        bottomPanel.add(extraButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JCheckBox createCheckBox(String permisos) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setBackground(Color.DARK_GRAY);
        checkBox.setForeground(Color.WHITE);
        checkBox.setFont(new Font("Arial", Font.BOLD, 18));
        checkBox.setSelected("true".equalsIgnoreCase(permisos) || "1".equals(permisos)); // Establecer el estado según el valor booleano
        return checkBox;
    }


    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setBackground(Color.GRAY);
        textField.setBorder(null);
        textField.setEditable(false);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.BOLD, 18));
        textField.setHorizontalAlignment(JTextField.LEFT);
        return textField;
    }

    public void addButtonNextToDelete(String buttonText) {
        if (!botonAñadido) {
            applyButton = new JButton(buttonText);
            applyButton.setFont(new Font("Arial", Font.BOLD, 14));
            applyButton.setBackground(Color.WHITE);

            JPanel buttonPanel = (JPanel) eliminarButton.getParent();
            buttonPanel.add(applyButton);
            botonAñadido = true;

            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }

    public void removeNewButton() {
        if (botonAñadido && applyButton != null) {
            JPanel buttonPanel = (JPanel) applyButton.getParent();
            buttonPanel.remove(applyButton);
            botonAñadido = false;
            applyButton = null;

            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }

    public JButton getEditarButton() {
        return editarButton;
    }

    public void setEditarButton(JButton editarButton) {
        this.editarButton = editarButton;
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public void setApplyButton(JButton applyButton) {
        this.applyButton = applyButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }

    public void setEliminarButton(JButton eliminarButton) {
        this.eliminarButton = eliminarButton;
    }
}
