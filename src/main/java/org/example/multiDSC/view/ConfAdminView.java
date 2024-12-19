package org.example.multiDSC.view;

import javax.swing.*;
import java.awt.*;
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

        // Crear el panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.DARK_GRAY);

        // Panel con las etiquetas superiores
        JPanel labelsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        labelsPanel.setBackground(Color.DARK_GRAY);
        String[] etiquetas = {"Nombre:", "Correo:", "Rol:", "Acción:"};

        for (String etiqueta : etiquetas) {
            JLabel label = new JLabel(etiqueta, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            labelsPanel.add(label);
        }

        // Panel principal de campos con scroll
        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.add(labelsPanel);

        JScrollPane scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        // Conectar a la base de datos y obtener los datos de los usuarios
        ConectionBD conectionBD = new ConectionBD();
        ArrayList<Map<String, String>> userDataList = new ArrayList<>();

        try {
            conectionBD.connect();
            userDataList = conectionBD.getUserData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Crear filas dinámicas según la cantidad de usuarios
        for (Map<String, String> userData : userDataList) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 4, 10, 10));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            rowPanel.setBackground(Color.DARK_GRAY);

            JTextField nombreField = createTextField(userData.get("Nombre"));
            JTextField correoField = createTextField(userData.get("Correo"));
            JTextField rolField = createTextField(userData.get("Rol_Nombre"));
            JTextField actionField = createTextField(""); // Campo para futuras acciones.

            rowPanel.add(nombreField);
            rowPanel.add(correoField);
            rowPanel.add(rolField);
            rowPanel.add(actionField);

            fieldsPanel.add(rowPanel);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(Color.WHITE);
            fieldsPanel.add(separator);
        }

        // Añadir componentes al panel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Área con scroll

        // Añadir el panel principal al frame
        add(mainPanel);
        // Añadir componentes al panel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Área con scroll

// Panel con botones (restaurado como lo tenías antes)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.DARK_GRAY);


        JButton editarButton = new JButton("Modify");
        JButton eliminarButton = new JButton("Delete");

// Configuración de estilo para los botones
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = Color.LIGHT_GRAY;


        editarButton.setFont(buttonFont);
        editarButton.setBackground(buttonColor);
        eliminarButton.setFont(buttonFont);
        eliminarButton.setBackground(buttonColor);

// Añadir botones al panel de botones

        buttonPanel.add(editarButton);
        buttonPanel.add(eliminarButton);

// Panel inferior con un JTextField y un botón (restaurado)
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        textFieldPanel.setBackground(Color.DARK_GRAY);

        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(250, 30));
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setForeground(Color.WHITE);
        inputField.setBackground(Color.GRAY);
        inputField.setCaretColor(Color.WHITE);

        JButton enviarButton = new JButton("Search");
        enviarButton.setFont(new Font("Arial", Font.BOLD, 14));
        enviarButton.setBackground(buttonColor);

// Añadir componentes al panel inferior
        textFieldPanel.add(inputField);
        textFieldPanel.add(enviarButton);

// Añadir ambos paneles al panel principal
        mainPanel.add(buttonPanel, BorderLayout.NORTH); // Panel de botones en la parte superior
        mainPanel.add(textFieldPanel, BorderLayout.SOUTH); // Panel inferior con JTextField

        setVisible(true);
    }

    /**
     * Método auxiliar para crear JTextFields con un estilo consistente.
     * @param text El texto que mostrará el JTextField.
     * @return JTextField configurado.
     */
    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setBackground(Color.GRAY);
        textField.setBorder(null);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.BOLD, 18));
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.setPreferredSize(new Dimension(1, 22));
        textField.setMaximumSize(textField.getPreferredSize());
        textField.setEditable(false); // Deshabilitamos edición si es necesario.
        return textField;
    }
}
