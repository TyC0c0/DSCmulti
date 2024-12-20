package org.example.multiDSC.controller.listeners.Email;

import org.example.multiDSC.controller.MainController;
import org.example.multiDSC.controller.smptEmail.ReceiveEmail;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.MailModel;
import org.example.multiDSC.model.viewModels.PostLoginModel;
import org.example.multiDSC.model.viewModels.SendMailModel;
import org.example.multiDSC.view.MailView;
import org.example.multiDSC.view.PostLoginView;
import org.example.multiDSC.view.SendMailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MailButtonsListener implements ActionListener {

    private Manager manager;
    private MailView mailView;

    public MailButtonsListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Obtener el botón que se presionó
        switch (command) {
            case "Create New":
                manager.getMainController().getMail().dispose();
                // Acción para crear un nuevo correo
                if (manager.getMainController().getSendMailModel() ==null){
                    manager.getMainController().setSendMailModel(new SendMailModel(manager));
                }
                manager.getMainController().setSendMail(new SendMailView(manager, manager.getMainController().getSendMailModel()));
                manager.getMainController().addSendEmailButtonsLlistener();
                manager.getMainController().getSendMail().getFrame().setVisible(true);
                break;

            case "Refresh":
                // Acción para refrescar la bandeja de entrada (revisar correos)
                manager.getMainController().getMail().getReceiveEmail().check(manager.getRevisando());
                break;

            case "Back":
                // Acción para volver a la vista anterior (por ejemplo, salir de la vista del correo)
                manager.getMainController().getMail().dispose();
                manager.getMailThread().interrupt();
                if (manager.getMainController().getPostLoginModel() ==null){
                    manager.getMainController().setPostLoginModel(new PostLoginModel(manager));
                }
                manager.getMainController().setPostLogin(new PostLoginView(manager, manager.getMainController().getPostLoginModel()));
                manager.getMainController().addPostLoginListeners();
                manager.getMainController().getPostLogin().getFrame().setVisible(true);
                break;

            default:
                break;
        }
    }
}
