//package org.example.multiDSC.controller.listeners.LoginView;
//
//import org.example.multiDSC.controller.MainController;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.HashMap;
//
///*
//    Class to do the actinos of the buttons
//    @author Alvaro Garcia Lopez
//    @version 1.0
//
//*/
//
//public class ButtonListenerLogin implements ActionListener {
//
//
//    private final MainController mainController;
//
//    public ButtonListenerLogin(MainController mainController) {
//
//        this.mainController = mainController;
//    }
//
//    //Falta que haga un insert a la base de datos
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String nickname = mainController.getLogin().getTfNickname().getText();
//        String password = String.valueOf(mainController.getLogin().getPfPassword().getPassword());
//
//        if (mainController.getLogin().getBtnCancel() == e.getSource()) {
//            mainController.getLogin().dispose(); // Cerrar la ventana
//        } else if (mainController.getLogin().getBtnOk() == e.getSource()) {
//            if (nickname.isEmpty() || password.isEmpty()) {
//                // Mostrar advertencia si alguno de los campos está vacío
//                mainController.getLogin().showWarning("The password or the user are empty");
//            } else {
//                // Obtener nicknames y contraseñas de la base de datos
//                HashMap<String, String> nicknamesAndPasswords = mainController.getManager().getConexion().getValuesFromTables();
//                System.out.println("Database records: " + nicknamesAndPasswords);
//
//                // Verificar si el nickname existe y si la contraseña coincide
//                if (nicknamesAndPasswords.containsKey(nickname)) {
//                    if (nicknamesAndPasswords.get(nickname).equals(password)) {
//                        System.out.println("Login successful!");
//                        mainController.getLogin().dispose();
//                        mainController.getPostLoginView().getFrame().setVisible(true);
//                        // Aquí puedes continuar con la lógica de inicio de sesión
//                    } else {
//                        mainController.getLogin().showWarning("The user or password is incorrect");
//                    }
//                } else {
//                    mainController.getLogin().showWarning("The user or password is incorrect");
//                }
//            }
//        }
//        System.out.println("Nickname: " + nickname);
//        System.out.println("Password: " + password);
//    }
//
//}


