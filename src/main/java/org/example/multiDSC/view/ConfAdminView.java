package org.example.multiDSC.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A JFrame class that displays a user configuration form.
 * This form includes labeled input fields, action buttons, and supports scrolling for dynamic content.
 * @author Ivan Guerrero Romero
 * @version 1.0*/

public class ConfAdminView extends JFrame {

    /**
     * List of JLabels used to display the headers for the input fields.
     */
    private ArrayList<JLabel> nombres;

    /**
     * List of JButtons representing the action buttons.
     */
    private ArrayList<JButton> botones;

    /**
     * Panel containing all input fields and dynamic rows.
     */
    private JPanel fieldsPanel;

    /**
     * Constructs the ConfUserView GUI.
     * Initializes the components, layouts, and adds dynamic content.
     */
    public ConfAdminView() {
        // Configuración de la ventana
        setTitle("Configuración de Usuario");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Inicializar los ArrayLists
        nombres = new ArrayList<>();
        botones = new ArrayList<>();

        // Crear el panel principal con un BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear un panel con un layout vertical para los campos de entrada
        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        // Panel para etiquetas encima de todo
        JPanel labelsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        String[] etiquetas = {"Nombre:", "Correo:", "Permiso:", "Rol:"};

        for (String etiqueta : etiquetas) {
            JLabel label = new JLabel(etiqueta, SwingConstants.CENTER);
            nombres.add(label);
            labelsPanel.add(label);
        }

        // Añadir el panel de etiquetas al principio
        fieldsPanel.add(labelsPanel);

        // Scroll para el panel de campos
        JScrollPane scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Crear algunos campos de ejemplo dinámicos usando bucles
        for (int i = 0; i < 50; i++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 4, 10, 10));
            // Espaciado vertical entre filas
            rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JTextField[] textFields = new JTextField[4];
            for (int j = 0; j < textFields.length; j++) {
                textFields[j] = new JTextField(15);
                rowPanel.add(textFields[j]);
            }

            fieldsPanel.add(rowPanel);
        }

        // Panel para los botones
        JPanel buttonsPanel = new JPanel(new BorderLayout(10, 10));

        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Espaciado vertical

        String[] nombresBotones = {"Crear", "Modificar", "Borrar"};
        for (String nombreBoton : nombresBotones) {
            JButton boton = new JButton(nombreBoton);
            boton.setPreferredSize(new Dimension(65, 20)); // Ajustar tamaño del botón
            botones.add(boton);
            actionButtonsPanel.add(boton);
        }

        JButton volverButton = new JButton("Volver");
        botones.add(volverButton);

        JPanel volverButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        volverButtonPanel.add(volverButton);

        buttonsPanel.add(actionButtonsPanel, BorderLayout.CENTER);
        buttonsPanel.add(volverButtonPanel, BorderLayout.SOUTH);

        // Añadir los paneles al panel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.EAST);

        // Añadir el panel principal al frame
        add(mainPanel);
    }

}
