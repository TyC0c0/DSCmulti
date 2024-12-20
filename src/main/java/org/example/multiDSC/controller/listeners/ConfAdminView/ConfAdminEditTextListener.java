package org.example.multiDSC.controller.listeners.ConfAdminView;

import org.example.multiDSC.model.controllModels.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

public class ConfAdminEditTextListener implements ActionListener {
    private final Manager manager;
    private final Map<String, String> userData;
    private final int userId;

    public ConfAdminEditTextListener(Manager manager, Map<String, String> userData, int userId) {
        this.manager = manager;
        this.userData = userData;
        this.userId = userId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener el JComboBox para el rol
        JComboBox<String> roleComboBox = manager.getMainController().getEditWindow().getRoleComboBox();
        String rolNombre = (String) roleComboBox.getSelectedItem(); // Obtener el rol seleccionado

        int idRol;
        if ("Admin".equalsIgnoreCase(rolNombre)) {
            idRol = 1;
        } else if ("Usuario".equalsIgnoreCase(rolNombre)) {
            idRol = 2;
        } else {
            System.err.println("Rol desconocido: " + rolNombre);
            return; // Salir si el rol no es válido
        }

        // Construcción de la query para actualizar
        String updateQuery = String.format(
                "UPDATE \"USUARIO\" " +
                        "SET \"Nombre\" = '%s', \"Correo\" = '%s', \"id_rol\" = %d WHERE \"id\" = %d;",
                manager.getMainController().getEditWindow().getNameField().getText(), // Nuevo nombre
                manager.getMainController().getEditWindow().getEmailField().getText(), // Nuevo correo
                idRol,                  // id_rol calculado
                userId                  // ID del usuario
        );

        try {
            // Ejecutar la query con sqlModification
            manager.getConexion().sqlModification(updateQuery);

            // Mostrar el mensaje de éxito
            JOptionPane.showMessageDialog(
                    null,
                    "Usuario actualizado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Cerrar la ventana de edición
            manager.getMainController().getEditWindow().dispose();
            System.out.println("Usuario modificado correctamente.");
        } catch (SQLException ex) {
            // Si ocurre un error, mostrar el mensaje de error
            JOptionPane.showMessageDialog(
                    null,
                    "Error al actualizar el usuario: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            System.err.println("Error al modificar el usuario: " + ex.getMessage());
        }
    }
}
