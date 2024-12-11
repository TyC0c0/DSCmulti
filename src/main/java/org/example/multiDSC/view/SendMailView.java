package org.example.multiDSC.view;

import org.example.multiDSC.model.SendMailViewModel_en;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

/*
Class to configurate the form
@autor Alvaro Garcia Lopez
@version 1.0
 */
public class SendMailView {
    private JTextField textField1;
    private JTextField textField2;
    private JButton sendButton;
    private JButton addFileButton;
    private JButton cancelButton;
    private JTextArea textArea1;
    SendMailViewModel_en sendMailViewModelEn = new SendMailViewModel_en();
    public SendMailView() {
        // Crear el JFrame principal
        JFrame frame = new JFrame("Send Mail");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        System.out.println(sendMailViewModelEn.getTexts());
        // Configurar panel superior (para campos de texto y botones)
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // 3 filas, 2 columnas
        textField1 = new JTextField();
        textField2 = new JTextField();
        topPanel.add(new JLabel(sendMailViewModelEn.getTexts().get(0)));
        topPanel.add(textField1);
        topPanel.add(new JLabel(sendMailViewModelEn.getTexts().get(1)));
        topPanel.add(textField2);
        topPanel.add(new JLabel(sendMailViewModelEn.getTexts().get(2)));


        // Agregar botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sendButton = new JButton(sendMailViewModelEn.getTexts().get(3));
        addFileButton = new JButton(sendMailViewModelEn.getTexts().get(4));
        cancelButton = new JButton(sendMailViewModelEn.getTexts().get(5));

// Establecer colores de los botones
        sendButton.setBackground(Color.black);
        sendButton.setForeground(Color.WHITE);

        addFileButton.setBackground(Color.BLACK);
        addFileButton.setForeground(Color.WHITE);

        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);

        buttonPanel.add(addFileButton);
        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);


        // Configurar el JTextArea
        textArea1 = new JTextArea();
        textArea1.setLineWrap(true); // Habilitar saltos de línea automáticos
        textArea1.setWrapStyleWord(true); // Saltos de línea respetando palabras
        textArea1.setMargin(new Insets(10, 10, 10, 10)); // Margen interno
        JScrollPane scrollPane = new JScrollPane(textArea1); // Envolver en JScrollPane

        // Añadir los paneles al frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Hacer visible el frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SendMailView();
    }
}
