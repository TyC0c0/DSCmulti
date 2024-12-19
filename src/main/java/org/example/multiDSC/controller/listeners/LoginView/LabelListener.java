package org.example.multiDSC.controller.listeners.LoginView;

import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.LoginView;
import org.example.multiDSC.view.UserRegistrerView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LabelListener implements MouseListener {

    private final LoginView loginView;
    private final Manager manager;

    public LabelListener(LoginView loginView, ConectionBD conectionBD, Manager manager) {
        this.loginView = loginView;
        this.manager = manager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        loginView.dispose();
        manager.getMainController().getRegister().setVisible(true); // Mostrar la vista de registro
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
