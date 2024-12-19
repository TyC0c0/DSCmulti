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
        panel1.setLayout(new GridLayout(2, 3, 10, 10)); // 2 filas, 3 columnas, espacio horizontal y vertical
        panel1.setBackground(Color.DARK_GRAY);

        // Añadir botones al panel
        panel1.add(createButton("FolderImage.png"));
        panel1.add(createButton("Email.jpeg"));
        panel1.add(createButton("Config.jpeg"));
        panel1.add(createButton("Info.jpeg"));

        // Crear y configurar JFrame
        frame = new JFrame("Post Login View");
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        frame.setVisible(true);
    }

    private JButton createButton(String imagePath) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBackground(Color.DARK_GRAY);

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

}
