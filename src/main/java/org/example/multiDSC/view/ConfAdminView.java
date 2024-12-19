package org.example.multiDSC.view;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.ConfAdminModel;

/**
 * A JFrame class that displays a user configuration form.
 * Dynamically creates input fields based on database user data.
 * @author Ivan Guerrero Romero, Isaac Requena Santiago, Alvaro Garcia Lopez
 * @version 2.1
 */

public class ConfAdminView extends JFrame {
    @Getter
    @Setter
    private JPanel fieldsPanel;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton applyButton;
    private boolean botonAñadido = false;
    private ArrayList<JComboBox<String>> comboBoxes;
    private ArrayList<JTextField> textFields;
    private JComboBox<String> rolComboBox;

    private JPanel fieldsPanel; // Panel to hold dynamic user rows.

    /**
     * Constructs the ConfAdminView GUI.
     * Initializes components, connects to the database, and creates user input fields.
     */
    public ConfAdminView(Manager manager, ConfAdminModel confAdminModel) {
        // Configuración de la ventana
        setTitle("Configuración de Admin");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        comboBoxes = new ArrayList<>();
        textFields = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.DARK_GRAY);

        JPanel labelsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        labelsPanel.setBackground(Color.DARK_GRAY);
        String[] etiquetas = {"Nombre:", "Correo:", "Rol:", "Acción:"};

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
            JPanel rowPanel = new JPanel(new GridLayout(1, 4, 10, 10));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            rowPanel.setBackground(Color.DARK_GRAY);

            JTextField nombreField = createTextField(userData.get("Nombre"));
            JTextField correoField = createTextField(userData.get("Correo"));
            textFields.add(nombreField);
            textFields.add(correoField);

            // Crear JComboBox para roles
            String[] roles = {"Admin", "Usuario"};  // Asegúrate de que estos roles están en la base de datos
            rolComboBox = new JComboBox<>(roles);
            rolComboBox.setSelectedItem(userData.get("Rol_Nombre"));
            rolComboBox.setEnabled(false); // Desactivar inicialmente
            comboBoxes.add(rolComboBox);

            rowPanel.add(nombreField);
            rowPanel.add(correoField);
            rowPanel.add(rolComboBox);

            fieldsPanel.add(rowPanel);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(Color.WHITE);
            fieldsPanel.add(separator);
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones superior
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topButtonPanel.setBackground(Color.DARK_GRAY);

        editarButton = new JButton("Modify");
        eliminarButton = new JButton("Delete");

        topButtonPanel.add(editarButton);
        topButtonPanel.add(eliminarButton);

        mainPanel.add(topButtonPanel, BorderLayout.NORTH);

        // Panel inferior con el JTextField y el botón adicional
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(Color.DARK_GRAY);

        JTextField extraTextField = new JTextField(20);
        extraTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton extraButton = new JButton("Extra Button");
        bottomPanel.add(extraTextField);
        bottomPanel.add(extraButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
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

    public ArrayList<JComboBox<String>> getComboBoxes() {
        return comboBoxes;
    }

    public void setComboBoxes(ArrayList<JComboBox<String>> comboBoxes) {
        this.comboBoxes = comboBoxes;
    }

    public JComboBox<String> getRolComboBox() {
        return rolComboBox;
    }

    public void setRolComboBox(JComboBox<String> rolComboBox) {
        this.rolComboBox = rolComboBox;
    }
    public ArrayList<JTextField> getTextField() {
        return textFields;
    }
    public void setTextField(ArrayList<JTextField> textFields) {
        this.textFields = textFields;
    }
}
