package org.example.multiDSC.view;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.listeners.ConfAdminView.ConfAdminButtonListener;
import org.example.multiDSC.model.controllModels.Manager;

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

@Getter
@Setter
public class ConfAdminView extends JFrame {
    private final Manager manager;
    private ConfAdminModel confAdminModel;
    private JPanel fieldsPanel;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton applyButton;
    private boolean botonAñadido = false;
    private ArrayList<JComboBox<String>> comboBoxes;
    private ArrayList<JTextField> textFields;
    private JComboBox<String> rolComboBox;
    private ArrayList<JButton> modifyButtons = new ArrayList<>();
    private ArrayList<JButton> deleteButtons = new ArrayList<>();

    public ConfAdminView(Manager manager, ConfAdminModel confAdminModel) {
        this.manager = manager;
        this.confAdminModel=confAdminModel;
        setTitle("Configuración de Admin");
        setSize(1500, 500);  // Tamaño más grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        comboBoxes = new ArrayList<>();
        textFields = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.DARK_GRAY);

        JPanel labelsPanel = new JPanel(new GridLayout(1, 5, 10, 10));  // Añadí una columna extra para los botones
        labelsPanel.setBackground(Color.DARK_GRAY);
        String[] etiquetas = {"Nombre:", "Correo:", "Rol:", "", ""};  // Última columna para los botones

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

       // ConectionBD conectionBD = new ConectionBD();
        ArrayList<Map<String, String>> userDataList = new ArrayList<>();

        try {
            //conectionBD.connect();
            manager.getConexion().connect();
            userDataList = manager.getConexion().getUserData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (Map<String, String> userData : userDataList) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 5, 10, 10));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            rowPanel.setBackground(Color.DARK_GRAY);

            // Obtener el id de la base de datos
            String userId = userData.get("id");  // Aquí obtienes el id

            // Crear los campos de texto y otros elementos
            JTextField nombreField = createTextField(userData.get("Nombre"));
            JTextField correoField = createTextField(userData.get("Correo"));
            textFields.add(nombreField);
            textFields.add(correoField);

            // Crear JComboBox para roles
            String[] roles = {"Admin", "Usuario"};
            rolComboBox = new JComboBox<>(roles);
            rolComboBox.setSelectedItem(userData.get("Rol_Nombre"));
            rolComboBox.setEnabled(false);
            comboBoxes.add(rolComboBox);

            // Botones de Modify y Delete
            JButton modifyButton = new JButton("Modify");
            modifyButton.setActionCommand(userId); // Set the userId as ActionCommand
            JButton deleteButton = new JButton("Delete");

            modifyButtons.add(modifyButton);
            deleteButtons.add(deleteButton);

            modifyButton.setPreferredSize(new Dimension(5, 30));
            deleteButton.setPreferredSize(new Dimension(5, 30));

            // Añadir el listener al botón "Modificar"
            //modifyButton.addActionListener(new ConfAdminButtonListener(manager));

            // Agregar los componentes a la fila
            rowPanel.add(nombreField);
            rowPanel.add(correoField);
            rowPanel.add(rolComboBox);
            rowPanel.add(modifyButton);
            rowPanel.add(deleteButton);

            // Añadir la fila al panel
            fieldsPanel.add(rowPanel);

            // Separador entre filas
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(Color.WHITE);
            fieldsPanel.add(separator);
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);

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


}
