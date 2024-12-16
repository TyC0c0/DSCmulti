package org.example.multiDSC.controller.listeners.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Class to do the actinos of the buttons
    @author Alvaro Garcia Lopez
    @version 1.0

*/

public class ButtonListenerLogin implements ActionListener {
    private final JButton btnCancel;
    private final JButton btnOk;
    private final JTextField tfNickname;
    private final JPasswordField pfPassword;
    private final JDialog parentDialog;

    public ButtonListenerLogin(JButton btnOk, JButton btnCancel, JTextField tfNickname, JPasswordField pfPassword, JDialog parentDialog) {
        this.btnOk = btnOk;
        this.btnCancel = btnCancel;
        this.tfNickname = tfNickname;
        this.pfPassword = pfPassword;
        this.parentDialog = parentDialog;
    }

    //Falta que haga un insert a la base de datos

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = tfNickname.getText();
        String password = String.valueOf(pfPassword.getPassword());

        if (btnCancel == e.getSource()) {
            parentDialog.dispose(); // Cerrar la ventana
        } else if (btnOk == e.getSource()) {
            System.out.println(email);
            System.out.println(password);
        }
    }
}
