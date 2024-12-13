package org.example.multiDSC.view;

import javax.swing.*;
import java.awt.*;

public class PostLoginView {
    private JPanel panel1;
    private JButton FTPButton;
    private JButton MAILButton;
    private JButton CONFUSERButton;
    private JButton EXITButton;

    public PostLoginView() {
        // Crear panel principal
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 3, 10, 10)); // 2 filas, 3 columnas, espacio horizontal y vertical

        // Añadir botones al panel
        panel1.add(createButton("", "FolderImage.png", ""));
        panel1.add(createButton("", "fine_dining.png", "EMAIL"));
        panel1.add(createButton("", "cafes.png", "CONF USU"));
        panel1.add(createButton("", "fast_foods.png", "INFO", true));
    }

    private JButton createButton(String title, String imagePath, String subtitle) {
        return createButton(title, imagePath, subtitle, false);
    }

    private JButton createButton(String title, String imagePath, String subtitle, boolean highlighted) {
        // Crear botón
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        // Configurar colores si es destacado
        if (highlighted) {
            button.setBackground(Color.YELLOW);
            button.setOpaque(true);
        }

        // Añadir la imagen (cargar desde resources o usar un placeholder si no se encuentra)
        JLabel iconLabel = new JLabel();
        try {
            // Usar ClassLoader para cargar la imagen desde el classpath
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            iconLabel.setText("[Img]"); // Placeholder si falla la carga
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Crear etiquetas de texto
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);

        // Configuración adicional de las etiquetas
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Añadir componentes al botón
        button.add(iconLabel, BorderLayout.CENTER);
        button.add(titleLabel, BorderLayout.NORTH);
        button.add(subtitleLabel, BorderLayout.SOUTH);

        return button;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Usuario - XXXXXX");
        PostLoginView sectionWindow = new PostLoginView();
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setContentPane(sectionWindow.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
