package org.example.multiDSC.controller.listeners.PostLogin;

import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.*;
import org.example.multiDSC.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostLoginButtonsListener implements ActionListener {

    private Manager manager;

    public PostLoginButtonsListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (((JButton) e.getSource()).getName()) {
            case "FTP" -> {
                //COMPRUEBA QUE EL MODELO NO ESTE CREADO PARA NO DUPLICARLO
                if (manager.getMainController().getFtpModel() ==null){
                    manager.getMainController().setFtpModel(new FTPModel(manager));
                }
                manager.getMainController().setFtp(new FTPView("/", manager, manager.getMainController().getFtpModel()));
                //manager.getMainController().addFtpListeners();
                manager.getMainController().getFtp().setVisible(true);
            }
            case "EMAIL" -> {
                if (manager.getMainController().getMailModel() ==null){
                    manager.getMainController().setMailModel(new MailModel(manager));
                }
                manager.getMainController().setMail(new MailView(manager, manager.getMainController().getMailModel()));
                //manager.getMainController().addMailListeners();
                manager.getMainController().getMail().setVisible(true);
            }
            case "CONFIG" -> {
                //ADMIN
                if(manager.getUserRol()== 1){
                    if (manager.getMainController().getConfAdminModel() ==null){
                        manager.getMainController().setConfAdminModel(new ConfAdminModel(manager));
                    }
                    manager.getMainController().setConfAdmin(new ConfAdminView(manager, manager.getMainController().getConfAdminModel()));
                    //manager.getMainController().addConfigAdminListeners();
                    manager.getMainController().getConfAdmin().setVisible(true);
                }
                if(manager.getUserRol()== 2){
                    if (manager.getMainController().getConfUserModel() ==null){
                        manager.getMainController().setConfUserModel(new ConfUserModel(manager));
                    }
                    manager.getMainController().setConfUser(new ConfUserView(manager, manager.getMainController().getConfUserModel()));
                    //manager.getMainController().addConfigUserListeners();
                    manager.getMainController().getConfUser().setVisible(true);
                }
            }
            case "INFO" -> {
                if (manager.getMainController().getInfoModel() ==null){
                    manager.getMainController().setInfoModel(new InfoModel(manager));
                }
                manager.getMainController().setInfo(new InfoView(manager, manager.getMainController().getInfoModel()));
                //manager.getMainController().addInfoListeners();
                manager.getMainController().getInfo().setVisible(true);
            }
            case "LOG OUT" -> {
                Utils.resetUser(manager);
                manager.getMainController().getPostLogin().getFrame().dispose();
                manager.getMainController().setLogin(new LoginView(manager, manager.getMainController().getLoginModel()));
                manager.getMainController().addLoginListeners();
                manager.getMainController().getLogin().setVisible(true);
            }
        }
    }
}

