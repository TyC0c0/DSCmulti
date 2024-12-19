package org.example.multiDSC.controller.smptEmail;

import lombok.SneakyThrows;
import org.example.multiDSC.model.controllModels.Manager;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.*;
import java.util.Properties;

public class ReceiveEmail {

    private Manager manager;
    private Properties properties;
    private Session session;
    private static String emailFrom = "";
    private static String passwordFrom = "";

    public ReceiveEmail(Manager manager){
        this.manager=manager;
//        emailFrom=mainController.getCurrentUser().getEmail();
//        passwordFrom=mainController.getCurrentUser().getPassword();
        emailFrom="diegolg20037@gmail.com";
        passwordFrom="nmvvcsyzkzczkviy";
        properties= new Properties();
    }
    @SneakyThrows
    public void check(){
        System.out.println("receive");
        // Mail transfer protocol
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.imaps.host", "imap.gmail.com");
        properties.setProperty("mail.imaps.port", "993");
        properties.setProperty("mail.imaps.ssl.enable", "true");

        session= Session.getDefaultInstance(properties);

        // Modeling messages and access protocol
        Store store= session.getStore("imaps");
        store.connect("imap.gmail.com", emailFrom, passwordFrom);

        // Folder object to open email inbox
        Folder folder= store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        Message[] message = folder.getMessages();

        int z=0;
        String from;
        int start = Math.max(0, message.length - 15);
        for (int i = message.length - 1; i >= start; i--) {
            Message message1= message[i];
            from=removeExtra(message1.getFrom()[0].toString());

            DefaultListModel<String> listModel = (DefaultListModel<String>) manager.getMainController().getMail().getMailList().getModel();
            // Crear el nuevo registro
            String newEntry = z + from + "Asunto: " + message1.getSubject();

            System.out.println("Añadiendo "+ newEntry);
            // Añadir el registro al modelo
            listModel.addElement(newEntry);
            z++;
            System.out.println("si");
        }

        folder.close(true);
        store.close();
    }

    private String removeExtra(String from){
        from= from.replaceFirst("<.*?>", "");
        from="<b>"+ "De: "+ from+"</b>";
        return from;
    }
}
