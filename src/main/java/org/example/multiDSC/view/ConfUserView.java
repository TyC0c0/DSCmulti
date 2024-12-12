/**
 * The ConfUserView class represents a graphical user interface for managing user configurations.
 * It includes functionalities such as modifying user details, applying changes, and canceling edits.
 */
package org.example.multiDSC.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConfUserView extends JFrame {
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> labels;
    private ArrayList<JTextField> textFields;
    private ArrayList<JButton> botonesModificacion;
    private JPanel rightPanel;
    private JPanel botonesPanel;

    /**
     * Constructs the ConfUserView and initializes the GUI components and layout.
     * The interface includes buttons for modifying user details and applying or canceling changes.
     * @author Ivan Guerrero Romero
     * @version 1.0*/


    public ConfUserView() {
        // Inicializar listas
        buttons = new ArrayList<>();
        labels = new ArrayList<>();
        textFields = new ArrayList<>();
        botonesModificacion = new ArrayList<>();

        // Configurar el marco principal
        setTitle("Diseño de Interfaz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // Tamaño fijo para todas las pantallas
        setLayout(new BorderLayout());

        // Establecer márgenes y colores generales
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Panel izquierdo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.DARK_GRAY);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes

        // Crear botones dinámicamente
        String[] buttonNames = {"Eliminar", "Modificar"};
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            buttons.add(button);
            leftPanel.add(button);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciador
        }

        // Crear botón "Salir" separado
        JButton exitButton = new JButton("Salir");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cerrar la aplicación
            }
        });
        leftPanel.add(Box.createVerticalGlue()); // Separador flexible
        leftPanel.add(exitButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciador inferior

        // Agregar acción al botón "Modificar"
        buttons.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JTextField textField : textFields) {
                    textField.setEnabled(true); // Activar los campos de texto
                    textField.setBackground(Color.WHITE);
                }
                crearBotonesModificacion();
            }
        });

        // Panel derecho
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7, 2, 5, 5)); // 7 filas, 2 columnas
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes
        rightPanel.setBackground(Color.LIGHT_GRAY);

        // Crear etiquetas y campos de texto dinámicamente
        String[] labelsArray = {"Nickname", "Nombre", "Apellido", "Correo", "Contraseña", "DNI", "DescrpRol"};
        for (String labelName : labelsArray) {
            JLabel fieldLabel = new JLabel(labelName);
            fieldLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            JTextField textField = new JTextField();
            textField.setEnabled(false); // Desactivados inicialmente
            textField.setBackground(Color.GRAY);
            textField.setForeground(Color.BLACK);

            labels.add(fieldLabel);
            textFields.add(textField);

            rightPanel.add(fieldLabel);
            rightPanel.add(textField);
        }

        // Panel para los botones "Aplicar" y "Cancelar"
        botonesPanel = new JPanel();
        botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrar los botones
        botonesPanel.setBackground(Color.LIGHT_GRAY);
        botonesPanel.setVisible(false); // Ocultarlo inicialmente

        // Agregar paneles al marco principal
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the "Aplicar" and "Cancelar" buttons dynamically and configures their actions.
     * "Cancelar" clears the text fields, disables them, and hides the modification buttons.
     */
    private void crearBotonesModificacion() {
        if (botonesModificacion.isEmpty()) {
            // Nombres de los botones a crear
            String[] nombresBotones = {"Aplicar", "Cancelar"};

            // Crear y configurar botones dinámicamente
            for (String nombre : nombresBotones) {
                JButton boton = new JButton(nombre);
                boton.setBackground(Color.WHITE);
                boton.setForeground(Color.BLACK);
                boton.setFont(new Font("Arial", Font.BOLD, 12));

                // Agregar funcionalidad al botón "Cancelar"
                if (nombre.equals("Cancelar")) {
                    boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (JButton button : botonesModificacion) {
                                botonesPanel.remove(button);
                                buttons.get(1).setEnabled(true);
                            }
                            botonesModificacion.clear();
                            botonesPanel.setVisible(false); // Ocultar el panel de botones
                            for (JTextField textField : textFields) {
                                textField.setEnabled(false); // Desactivar los campos de texto
                                textField.setBackground(Color.GRAY);
                                textField.setText(""); // Limpiar los campos de texto
                            }
                            botonesPanel.revalidate();
                            botonesPanel.repaint();
                        }
                    });
                }

                botonesModificacion.add(boton);
                botonesPanel.add(boton);
            }

            botonesPanel.setVisible(true); // Mostrar el panel de botones
            botonesPanel.revalidate();
            botonesPanel.repaint();
        }
    }


    public static void main(String[] args) {
        // Crear y mostrar la interfaz
        ConfUserView frame = new ConfUserView();
        frame.setVisible(true);
    }
}
