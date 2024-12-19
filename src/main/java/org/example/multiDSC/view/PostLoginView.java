package org.example.multiDSC.view;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.PostLoginModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class PostLoginView {
    private PostLoginModel model;
    private JPanel panel1;
    private ArrayList<JButton> buttons;
    private JFrame frame; // Cambiado de estático a no estático

    public PostLoginView(Manager manager, PostLoginModel postLoginModel) {

        this.model = postLoginModel;

        // Inicializar lista de botones
        buttons = new ArrayList<>();

        // Crear panel principal
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout()); // Cambiar a GridBagLayout
        panel1.setBackground(Color.DARK_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Añadir botones al panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(createButton("FolderImage.png", "FTP"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel1.add(createButton("Email.jpeg", "EMAIL"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        panel1.add(createSmallButton("Close.png", "LOG OUT"), gbc); // Nuevo botón en el centro

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel1.add(createButton("Config.jpeg", "CONFIG"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panel1.add(createButton("Info.jpeg", "INFO"), gbc);

        // Crear y configurar JFrame
        frame = new JFrame("Post Login View");
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }

    private JButton createButton(String imagePath, String name) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBackground(Color.DARK_GRAY);
        button.setName(name);

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(250, 225, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            button.setText("[Img]");
        }

        buttons.add(button);
        return button;
    }

    private JButton createSmallButton(String imagePath, String name) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBackground(Color.DARK_GRAY);
        button.setName(name);

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH); // Imagen más pequeña
            button.setIcon(new ImageIcon(scaledImage));
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            button.setText("[Img]");
        }

        buttons.add(button);
        return button;
    }
}
