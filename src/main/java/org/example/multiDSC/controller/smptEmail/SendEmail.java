package org.example.multiDSC.controller.smptEmail;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.model.controllModels.Manager;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class SendEmail extends JFrame {

    private static String emailFrom = "";
    private static String passwordFrom = "";
    private String emailTo;
    private String subject;
    private String content;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    private File[] files;
    private String filesNames;


    public SendEmail(Manager manager) {

        emailFrom= manager.getUserEmail();
        passwordFrom= manager.getUserPassword();
        mProperties = new Properties();
        filesNames="";
    }

    public void createEmail(JTextField to,JTextArea message, JTextField subjectt) {
        emailTo = to.getText().trim();
        subject = subjectt.getText().trim();
        content = message.getText().trim();

        // Mail transfer protocol
        mProperties.put("mail.smtp.starttls.enable", "true");
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.port", "587");
        mProperties.put("mail.smtp.auth", "true");

        mSession = Session.getDefaultInstance(mProperties);


        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setText(content, "ISO-8859-1", "html");


        } catch (AddressException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect("smtp.gmail.com", emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
