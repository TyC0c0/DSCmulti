package org.example.multiDSC.view;

import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.InfoModel;

import javax.swing.*;
import java.awt.*;

public class InfoView extends JFrame {


    private InfoModel InfoModel;

    public InfoView(Manager manager, InfoModel infoModel) {

        this.InfoModel = infoModel;

        // Configuración de la ventana principal
        setTitle("Acerca De DataConnect Solutions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con fondo amarillo claro
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(132, 198, 217)); // Fondo amarillo claro
        mainPanel.setLayout(new BorderLayout());

        // Título principal
        JLabel titleLabel = new JLabel(InfoModel.getText().get(0), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 60, 60)); // Color gris oscuro
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel central con información
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(132, 198, 217));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Desarrolladores
        JLabel developersTitle = new JLabel(InfoModel.getText().get(1));
        developersTitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        developersTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(developersTitle);
        centerPanel.add(Box.createVerticalStrut(10)); // Espacio

        String[] developers = {
                "Iván Guerrero Romero",
                "Isaac Requena Santiago",
                "Álvaro García López",
                "Ramón Reina González",
                "Jóse Manuel Campos López"
        };

        for (String dev : developers) {
            JLabel devLabel = new JLabel(dev);
            devLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            devLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(devLabel);
            centerPanel.add(Box.createVerticalStrut(5)); // Espacio entre nombres
        }

        centerPanel.add(Box.createVerticalStrut(15)); // Espacio

        // Derechos de autor
        JLabel copyrightLabel = new JLabel(InfoModel.getText().get(2));
        copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        copyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(copyrightLabel);

        JLabel rightsLabel = new JLabel(InfoModel.getText().get(3));
        rightsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rightsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(rightsLabel);

        // Cargar y escalar la imagen
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("copyright.png"));
        Image scaledImage = icon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Crear un JLabel con la imagen escalada
        JLabel iconLabel = new JLabel(scaledIcon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(10)); // Espacio
        centerPanel.add(iconLabel);

        // Agregar el panel central al principal
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Agregar el panel principal a la ventana
        add(mainPanel);

        setVisible(true);
    }
}
