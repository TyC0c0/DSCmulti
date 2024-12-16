package org.example.multiDSC.controller.listeners.LoginView;

import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.view.LoginView;
import org.example.multiDSC.view.UserRegistrerView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LabelListenerLogin implements MouseListener {

    private final LoginView loginView;
    private final ConectionBD conectionBD;

    public LabelListenerLogin(LoginView loginView, ConectionBD conectionBD) {
        this.loginView = loginView;
        this.conectionBD = conectionBD;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        loginView.dispose();
        new UserRegistrerView(conectionBD); // Abre la ventana de registro de usuario

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // No se necesita implementación
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No se necesita implementación
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // No se necesita implementación
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // No se necesita implementación
    }
}
