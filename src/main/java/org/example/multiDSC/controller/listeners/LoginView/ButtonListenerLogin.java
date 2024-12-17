package org.example.multiDSC.controller.listeners.LoginView;

import org.example.multiDSC.controller.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Class to do the actinos of the buttons
    @author Alvaro Garcia Lopez
    @version 1.0

*/

public class ButtonListenerLogin implements ActionListener {


    private final MainController mainController;

    public ButtonListenerLogin(MainController mainController) {
        this.mainController = mainController;
    }

    //Falta que haga un insert a la base de datos

    @Override
    public void actionPerformed(ActionEvent e) {
        String email =mainController.getLogin().getTfNickname().getText();
        String password = String.valueOf(mainController.getLogin().getPfPassword().getPassword());

        if (mainController.getLogin().getBtnCancel() == e.getSource()) {
            mainController.getLogin().dispose(); // Cerrar la ventana
        } else if (mainController.getLogin().getBtnOk() == e.getSource()) {
            if(email.isEmpty() || password.isEmpty()){
                //AÑADIR UN METODO PARA QUE SALGA UN AVISO DE QUE UNO DE LOS CAMPOS NO ESTAN RELLENOS
                mainController.getLogin().showWarning("The password or the user are empty");
            }
            //ADD  A METHOD THAT TAKE THE VALUES OF THE COLUMNS IN THE DATA BASE AND CHECK IF THEY ARE THE SAME OR NOT
            //else if(mainController.getManager().getConexion().){

            }
            System.out.println(email);
            System.out.println(password);
        }
    }

