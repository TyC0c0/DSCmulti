package org.example.multiDSC.controller.listeners.ConfAdminView;

import org.example.multiDSC.model.controllModels.Manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConfAdminDeleteButton implements ActionListener {
    private final Manager manager;


    public ConfAdminDeleteButton(Manager manager) {
        this.manager = manager;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String query = String.format("DELETE FROM \"USUARIO\" WHERE \"Nickname\" = %s;", manager.getUserNickname());
        try {
            manager.getConexion().sqlModification(query);
            manager.getMainController().getEditWindow().dispose();
            System.out.println("Usuario eliminado correctamente.");
        } catch (
                SQLException ex) {
            System.err.println("Error al eliminar el usuario: " + ex.getMessage());
        }
    }
}
