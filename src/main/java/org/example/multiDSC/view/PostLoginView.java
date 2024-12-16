package org.example.multiDSC.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PostLoginView {
    private JPanel panel1;
    private static ArrayList<JButton> buttons= new ArrayList<>();

    public PostLoginView() {
        // Crear panel principal
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 3, 10, 10)); // 2 filas, 3 columnas, espacio horizontal y vertical
        panel1.setBackground(Color.DARK_GRAY);

        // Añadir botones al panel
        panel1.add(createButton("FolderImage.png"));
        panel1.add(createButton("Email.jpeg"));
        panel1.add(createButton("Config.jpeg"));
        panel1.add(createButton("Info.jpeg"));
    }

    private JButton createButton(String imagePath) {
        // Crear botón
        JButton button = new JButton();
        button.setFocusPainted(false); // Elimina el borde de foco
        button.setContentAreaFilled(false); // Quita el relleno por defecto del botón
        button.setBorderPainted(false); // Opcional: elimina los bordes del botón
        button.setOpaque(true); // Habilita el fondo opaco
        button.setBackground(Color.DARK_GRAY); // Fondo blanco para los botones

        try {
            // Cargar la imagen desde el classpath
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));

            // Escalar la imagen al tamaño del botón
            Image scaledImage = icon.getImage().getScaledInstance(250, 225, Image.SCALE_SMOOTH); // Tamaño fijo de ejemplo
            button.setIcon(new ImageIcon(scaledImage));

            // Ajustar alineación
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            button.setText("[Img]"); // Placeholder si falla la carga
        }

        buttons.add(button);
        return button;
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Usuario - XXXXXX");
        PostLoginView sectionWindow = new PostLoginView();
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setContentPane(sectionWindow.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
