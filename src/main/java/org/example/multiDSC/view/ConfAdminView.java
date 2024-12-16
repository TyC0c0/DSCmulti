package org.example.multiDSC.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * A JFrame class that displays a user configuration form.
 * This form includes labeled input fields, action buttons, and supports scrolling for dynamic content.
 * @author Ivan Guerrero Romero, Isaac Requena Santiago
 * @version 2.0*/

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

    public static void main(String args[]){
        ConfAdminView frame = new ConfAdminView();
        frame.setVisible(true);
    }

    public ConfAdminView() {
        // Configuración de la ventana
        setTitle("Configuración de Admin");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        // Inicializar los ArrayLists
        nombres = new ArrayList<>();
        botones = new ArrayList<>();

        // Crear el panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.DARK_GRAY);

        // Panel con las etiquetas superiores
        JPanel labelsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        labelsPanel.setBackground(Color.DARK_GRAY);
        String[] etiquetas = {"Nombre:", "Correo:", "Permiso:", "Rol:"};

        for (String etiqueta : etiquetas) {
            JLabel label = new JLabel(etiqueta, SwingConstants.CENTER);
            nombres.add(label);
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

        // Crear filas dinámicas
        for (int i = 0; i < 50; i++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 4, 10, 10));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            rowPanel.setBackground(Color.DARK_GRAY);

            JTextField[] textFields = new JTextField[4];
            for (int j = 0; j < textFields.length; j++) {
                final int index = j;
                textFields[j] = new JTextField();
                textFields[j].setBackground(Color.GRAY);
                textFields[j].setBorder(null);
                textFields[j].setForeground(Color.WHITE);
                textFields[j].setFont(new Font("Arial", Font.BOLD, 18));
                textFields[j].setHorizontalAlignment(JTextField.LEFT);
                textFields[j].setPreferredSize(new Dimension(1, 22));
                textFields[j].setMaximumSize(textFields[j].getPreferredSize());
                rowPanel.add(textFields[j]);
            }
            fieldsPanel.add(rowPanel);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(Color.WHITE);
            fieldsPanel.add(separator);
        }

        // Panel de botones (Crear, Modificar, Borrar) a la derecha
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.setBackground(Color.DARK_GRAY);

        String[] nombresBotones = {"Crear", "Modificar", "Borrar"};
        for (String nombreBoton : nombresBotones) {
            JButton boton = new JButton(nombreBoton);
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);
            boton.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
            boton.setBackground(Color.WHITE);
            botones.add(boton);
            buttonsPanel.add(Box.createVerticalStrut(10)); // Espaciado entre botones
            buttonsPanel.add(boton);
        }

        // Panel de búsqueda centrado
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JTextField searchField = new JTextField(20); // Barra de búsqueda
        JButton searchButton = new JButton("Buscar"); // Botón de búsqueda
        searchPanel.setBackground(Color.DARK_GRAY);
        searchButton.setBackground(Color.WHITE);
        JLabel buscarLabel = new JLabel("Buscar:");
        buscarLabel.setForeground(Color.WHITE);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Botón "Volver" en la parte inferior derecha
        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Volver a la derecha
        JButton volverButton = new JButton("Volver");
        volverPanel.setBackground(Color.DARK_GRAY);
        volverButton.setBackground(Color.WHITE);
        volverButton.setPreferredSize(new Dimension(90, 30));
        botones.add(volverButton);
        volverPanel.add(volverButton);

        // Panel combinado para la parte inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(searchPanel, BorderLayout.CENTER); // Búsqueda centrada
        bottomPanel.add(volverPanel, BorderLayout.EAST); // Botón Volver a la derecha

        // Añadir componentes al panel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);  // Área con scroll
        mainPanel.add(buttonsPanel, BorderLayout.EAST);  // Botones principales
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);  // Parte inferior: búsqueda centrada y botón "Volver"

        // Añadir el panel principal al frame
        add(mainPanel);
    }

}
//// Añadir DocumentListener para mantener la alineación al perder el foco
//textFields[j].getDocument().addDocumentListener(new DocumentListener() {
//    @Override
//    public void insertUpdate(DocumentEvent e) {
//        textFields[index].setHorizontalAlignment(JTextField.LEFT);
//    }
//
//    @Override
//    public void removeUpdate(DocumentEvent e) {
//        textFields[index].setHorizontalAlignment(JTextField.LEFT);
//    }
//
//    @Override
//    public void changedUpdate(DocumentEvent e) {
//        textFields[index].setHorizontalAlignment(JTextField.LEFT);
//    }
//});
