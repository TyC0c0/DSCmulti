package org.example.multiDSC.controller.listeners.LoginView;

import org.example.multiDSC.view.LoginView;
import org.example.multiDSC.view.UserRegistrerView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LabelListenerLogin implements MouseListener {

    private final LoginView loginView;

    public LabelListenerLogin(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        loginView.dispose();
        new UserRegistrerView(); // Abre la ventana de registro de usuario

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
