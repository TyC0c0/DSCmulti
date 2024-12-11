package org.example.multiDSC.view;

import javax.swing.*;

public class SectionWindow {
    private JPanel panel1;
    private JButton FTPButton;
    private JButton MAILButton;
    private JButton CONFUSERButton;
    private JButton EXITButton;

    public static void main(String[] args) {
        // Crear el frame
        JFrame frame = new JFrame("Section Window");

        // Crear una instancia de la clase
        SectionWindow sectionWindow = new SectionWindow();

        // Añadir el panel diseñado al frame
        frame.setContentPane(sectionWindow.panel1);

        // Configurar comportamiento al cerrar la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajustar tamaño automáticamente
        frame.pack();

        // Centramos la ventana
        frame.setLocationRelativeTo(null);

        // Hacer la ventana visible
        frame.setVisible(true);
    }
}
