package org.example.multiDSC.controller.listeners.LoginView;

import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.controller.databaseConection.ConexionThread;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.PostLoginModel;
import org.example.multiDSC.view.PostLoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/*
    Class to do the actinos of the buttons
    @author Alvaro Garcia Lopez, Isaac Requena Santiago
    @version 1.3

*/

public class ButtonsListener implements ActionListener {


    private final Manager manager;

    public ButtonsListener(Manager manager) {

        this.manager = manager;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = manager.getMainController().getLogin().getTfNickname().getText();
        String password = String.valueOf(manager.getMainController().getLogin().getPfPassword().getPassword());

        if (manager.getMainController().getLogin().getBtnCancel() == e.getSource()) {
            manager.getConexionThread().stopThread();
            manager.getMainController().getLogin().dispose(); // Cerrar la ventana
        } else if (manager.getMainController().getLogin().getBtnOk() == e.getSource()) {
            if (nickname.isEmpty() || password.isEmpty()) {
                // Mostrar advertencia si alguno de los campos está vacío
               Utils.showErrorWindow(null, "Complete all the information", "The password or the user are empty");
            } else {
                // Obtener nicknames y contraseñas de la base de datos
                HashMap<String, String> nicknamesAndPasswords = manager.getConexion().getValuesFromTables();

                // Verificar si el nickname existe y si la contraseña coincide
                if (nicknamesAndPasswords.containsKey(nickname)) {
                    if (nicknamesAndPasswords.get(nickname).equals(password)) {
                        System.out.println("Login successful!");
                        manager.getMainController().getLogin().dispose();

                        manager.getMainController().setPostLoginModel(new PostLoginModel(manager));
                        manager.getMainController().setPostLogin(new PostLoginView());
                        //addlisteners
                        //setvisibleTrue

                        Utils.LogRegister(manager, "Un usuario se ha conectado a traves de una cuenta", true);

                    } else {
                        Utils.showErrorWindow(null,"The user or password is incorrect","It's incorrect or doesn't exist");
                    }
                } else {
                    Utils.showErrorWindow(null,"The user or password is incorrect","It's incorrect or doesn't exist");
                }
            }
        }

    }

}


