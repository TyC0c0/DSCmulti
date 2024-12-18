package org.example.multiDSC.controller.listeners.LoginView;

import org.example.multiDSC.controller.MainController;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.PostLoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/*
    Class to do the actinos of the buttons
    @author Alvaro Garcia Lopez
    @version 1.1

*/

public class ButtonListenerLogin implements ActionListener {


    private final Manager manager;

    public ButtonListenerLogin(Manager manager) {

        this.manager = manager;
    }

    //Falta que haga un insert a la base de datos


    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = manager.getMainController().getLogin().getTfNickname().getText();
        String password = String.valueOf(manager.getMainController().getLogin().getPfPassword().getPassword());

        if (manager.getMainController().getLogin().getBtnCancel() == e.getSource()) {
            manager.getMainController().getLogin().dispose(); // Cerrar la ventana
        } else if (manager.getMainController().getLogin().getBtnOk() == e.getSource()) {
            if (nickname.isEmpty() || password.isEmpty()) {
                // Mostrar advertencia si alguno de los campos está vacío
               Utils.showErrorWindow(null, "Complete all the information", "The password or the user are empty");
            } else {
                // Obtener nicknames y contraseñas de la base de datos
                HashMap<String, String> nicknamesAndPasswords = manager.getMainController().getManager().getConexion().getValuesFromTables();
                System.out.println("Database records: " + nicknamesAndPasswords);

                // Verificar si el nickname existe y si la contraseña coincide
                if (nicknamesAndPasswords.containsKey(nickname)) {
                    if (nicknamesAndPasswords.get(nickname).equals(password)) {
                        System.out.println("Login successful!");
                        manager.getMainController().getLogin().dispose();
                        manager.getMainController().getPostLoginView().getFrame().setVisible(true);
                        // Aquí puedes continuar con la lógica de inicio de sesión
                    } else {
                        Utils.showErrorWindow(null,"The user or password is incorrect","It's incorrect or doesn't exist");
                    }
                } else {
                    Utils.showErrorWindow(null,"The user or password is incorrect","It's incorrect or doesn't exist");
                }
            }
        }
        System.out.println("Nickname: " + nickname);
        System.out.println("Password: " + password);
    }

}


