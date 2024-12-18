package org.example.multiDSC.controller.listeners.LoginView;

import org.example.multiDSC.controller.MainController;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.LoginView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LabelListenerLogin implements MouseListener {

    private final LoginView loginView;
    private final ConectionBD conectionBD;
    private final Manager manager;

    public LabelListenerLogin(LoginView loginView, ConectionBD conectionBD, Manager manager) {
        this.loginView = loginView;
        this.conectionBD = conectionBD;
        this.manager = manager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        loginView.dispose();
        //MainController mainController; // Acceder al controlador principal
        //UserRegistrerView registerView = mainController.getRegister(); // Obtener la vista de registro
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
