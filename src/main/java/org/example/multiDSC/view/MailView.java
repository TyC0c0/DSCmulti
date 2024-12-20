package org.example.multiDSC.view;

import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.MailModel;
import org.example.multiDSC.controller.smptEmail.ReceiveEmail;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * MailView - Represents the main view of a mail application.*
 * @author Ivan Guerrero Romero, Isaac Requena Santiago
 * @version 2.0
 */

@Getter
@Setter
public class MailView extends JFrame implements Runnable {

    private MailModel model;
    private JPanel mainPanel;
    private ArrayList<JButton> buttonList;
    private JList<String> mailList;
    private JScrollPane scrollPane;
    private ReceiveEmail receiveEmail;
    private boolean keepChecking = true;
    private int recharges = 0;

    public MailView(Manager manager, MailModel mailModel) {
        this.model = mailModel;
        // Configuración del marco
        setTitle("Mail");
        setSize(900, 500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Panel izquierdo
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.setBackground(Color.DARK_GRAY);
        panelLeft.setPreferredSize(new Dimension(200, getHeight())); // Ampliar el ancho del panel negro

        // Crear lista de botones
        buttonList = new ArrayList<>();
        String[] buttonLabels = {"Create New", "Refresh", "Back"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFocusPainted(false); // Desactiva el borde de enfoque
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setMaximumSize(new Dimension(150, 40)); // Botones más largos
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonList.add(button);
        }

        // Agregar botones y etiquetas al panel izquierdo
        panelLeft.add(Box.createRigidArea(new Dimension(0, 15))); // Espaciado superior
        panelLeft.add(buttonList.get(0)); // Create New
        panelLeft.add(Box.createRigidArea(new Dimension(0, 30))); // Mayor separación

        String[] labels = {"Inbox", "Unread", "Sent", "Draft", "Spam", "Trash"};
        for (String label : labels) {
            JLabel lbl = new JLabel(label);
            lbl.setForeground(Color.WHITE);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            lbl.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Márgenes superior e inferior
            panelLeft.add(lbl);
            panelLeft.add(Box.createRigidArea(new Dimension(0, 20))); // Más separación entre etiquetas
        }

        // Extender el panel izquierdo completamente
        JPanel panelLeftWrapper = new JPanel(new BorderLayout());
        panelLeftWrapper.setBackground(Color.DARK_GRAY);
        panelLeftWrapper.add(panelLeft, BorderLayout.NORTH);
        mainPanel.add(panelLeftWrapper, BorderLayout.WEST);

        // Panel central
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.darkGray);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen para el panel central
        DefaultListModel<String> listModel = new DefaultListModel<>();
        mailList = new JList<>(listModel);
        mailList.setBackground(Color.LIGHT_GRAY); // Color de fondo del JList
        mailList.setForeground(Color.BLACK); // Color del texto del JList

        // Agregar el JList a un JScrollPane para permitir el desplazamiento
        scrollPane = new JScrollPane(mailList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde del JScrollPane
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar botones Refresh y Back al panel central debajo del JList
        JPanel centerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton refreshButton = buttonList.get(1);
        JButton backButton = buttonList.get(2);
        centerButtonPanel.setBackground(Color.darkGray);
        refreshButton.setPreferredSize(new Dimension(300, 25)); // Alargar botón Refresh
        backButton.setPreferredSize(new Dimension(300, 25)); // Alargar botón Back
        centerButtonPanel.add(refreshButton); // Refresh
        centerButtonPanel.add(backButton); // Back
        centerPanel.add(centerButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Configurar texto desde el modelo
        buttonList.get(0).setText(model.getText().get(0));
        String[] texts = model.getText().toArray(new String[0]);
        refreshButton.setText(texts[7]);
        backButton.setText(texts[8]);

        // Agregar Listener para el JList
        mailList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = mailList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    try {
                        // Obtener el mensaje seleccionado desde ReceiveEmail
                        Message selectedMessage = receiveEmail.getMessage(selectedIndex);
                        if (selectedMessage != null) {
                            String content = getMessageContent(selectedMessage);
                            // Mostrar el contenido del mensaje en un JOptionPane
                            JOptionPane.showMessageDialog(
                                    this,
                                    content,
                                    "Contenido del Mensaje",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "No se encontró el mensaje.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Error al cargar el contenido del mensaje: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });


        // Inicializar la clase ReceiveEmail para cargar los correos
        receiveEmail = new ReceiveEmail(manager);
    }

    @SneakyThrows
    @Override
    public void run() {
        while (keepChecking) {
            checkEmails();
            try {
                Thread.sleep(10000);  // Espera de 10 segundos antes de revisar los correos nuevamente
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e1) {
                System.out.println("Error al revisar correos: " + e1.getMessage());
                e1.printStackTrace();
            }
            setVisible(true);
        }
    }

    private void checkEmails() {
        receiveEmail.check();  // Verifica los correos en el servidor
        System.out.println("Inbox recargado " + recharges);
        recharges++;
    }

    // Método para obtener el contenido de un mensaje
    private String getMessageContent(Message message) throws Exception {
        Object content = message.getContent();
        if (content == null) {
            return "El mensaje no tiene contenido.";
        }
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                builder.append(bodyPart.getContent().toString()).append("\n");
            }
            return builder.toString();
        }
        return "No se puede mostrar el contenido del mensaje.";
    }
}


