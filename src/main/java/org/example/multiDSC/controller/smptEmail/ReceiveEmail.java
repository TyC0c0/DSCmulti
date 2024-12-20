package org.example.multiDSC.controller.smptEmail;

import lombok.SneakyThrows;
import org.example.multiDSC.model.controllModels.Manager;
import javax.mail.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Properties;

public class ReceiveEmail {

    private Manager manager;
    private Properties properties;
    private Session session;
    private static String emailFrom = "";
    private static String passwordFrom = "";
    private ArrayList<Message> messages = new ArrayList<>();  // Lista para almacenar los mensajes

    public ReceiveEmail(Manager manager) {
        this.manager = manager;
        emailFrom = manager.getUserEmail();
        passwordFrom = manager.getUserPassword();
        properties = new Properties();
    }

    @SneakyThrows
    public void check() {
        System.out.println("Checking emails...");

        // Configuración del protocolo de correo
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.imaps.host", "imap.gmail.com");
        properties.setProperty("mail.imaps.port", "993");
        properties.setProperty("mail.imaps.ssl.enable", "true");

        session = Session.getDefaultInstance(properties);

        // Conexión al almacén de correos
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", emailFrom, passwordFrom);

        // Abrir la carpeta de entrada (INBOX)
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        // Obtener los mensajes
        messages.clear();  // Limpiar la lista de mensajes antes de agregar nuevos
        Message[] messageArray = folder.getMessages();

        // Limpiar la lista antes de añadir nuevos correos
        DefaultListModel<String> listModel = (DefaultListModel<String>) manager.getMainController().getMail().getMailList().getModel();
        listModel.clear();

        // Iterar sobre los mensajes y añadirlos al modelo
        for (int i = messageArray.length - 1; i >= 0; i--) {
            Message message = messageArray[i];
            messages.add(message);  // Guardar el mensaje en la lista

            // Procesar el remitente
            String from = removeExtra(message.getFrom()[0].toString());

            // Crear el nuevo registro para el correo
            String newEntry = "De: " + from + " | Asunto: " + message.getSubject();

            // Añadir el registro al modelo de la lista
            listModel.addElement(newEntry);
        }

        // Cerrar conexiones
        folder.close(false);
        store.close();
    }

    private String removeExtra(String from) {
        // Limpiar el remitente del correo
        if (from.contains("<")) {
            from = from.replaceAll("<.*?>", "").trim();
        }
        return from;
    }

    // Método para obtener el mensaje por índice
    public Message getMessage(int index) {
        if (index >= 0 && index < messages.size()) {
            return messages.get(index);  // Devolver el mensaje desde la lista
        }
        return null;  // Si el índice no es válido, devolver null
    }
}

