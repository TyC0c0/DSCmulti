package org.example.multiDSC.controller.listeners.ConfAdminView;

import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.EditWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ConfAdminButtonListener implements ActionListener {

    private final Manager manager;

    public ConfAdminButtonListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Verificar si el evento fue disparado por el botón "Modificar"
        if (e.getSource() instanceof JButton) {
            JButton sourceButton = (JButton) e.getSource();

            // Obtener el ActionCommand del botón, que es el id del usuario
            String userIdStr = sourceButton.getActionCommand();
            int userId = Integer.parseInt(userIdStr); // Convertirlo a entero

            // Mostrar el id en la consola
            System.out.println("El ID del usuario que ha pulsado el botón es: " + userId);

            // Realizar la consulta para obtener los datos del usuario
            try {
                // Llamamos al método getUserData() para obtener los datos de la base de datos
                ArrayList<Map<String, String>> userDataList = manager.getConexion().getUserData();

                // Buscar el usuario con el id que acabamos de obtener
                for (Map<String, String> userData : userDataList) {
                    if (Integer.parseInt(userData.get("id")) == userId) {
                        // Si encontramos el usuario con el id correcto, abrimos la ventana de edición
                        EditWindow editWindow = new EditWindow(userData, userId);
                        manager.getMainController().setEditWindow(editWindow);
                        manager.getMainController().EditWindowAddActionListener(userData, userId);
                        editWindow.setVisible(true);
                        break;
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error al obtener los datos: " + ex.getMessage());
            }
        }
    }
}

