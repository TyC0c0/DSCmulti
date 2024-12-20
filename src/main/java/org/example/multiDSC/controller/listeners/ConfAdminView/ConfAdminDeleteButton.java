package org.example.multiDSC.controller.listeners.ConfAdminView;

import org.example.multiDSC.model.controllModels.Manager;

import javax.swing.*;
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
        // Verificar si el evento fue disparado por un JButton
        if (e.getSource() instanceof JButton) {
            JButton sourceButton = (JButton) e.getSource();

            // Obtener el ActionCommand del botón, que es el id del usuario
            String userIdStr = sourceButton.getActionCommand();
            int userId = Integer.parseInt(userIdStr); // Convertir el id a entero

            // Mostrar el cuadro de confirmación
            int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    "¿Estás seguro de que deseas eliminar este usuario?",
                    "Confirmación de eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // Si el usuario pulsa "Sí" (OK), proceder con la eliminación
            if (confirmation == JOptionPane.YES_OPTION) {
                // Crear la consulta SQL para eliminar el usuario con el id correspondiente
                String query = String.format("DELETE FROM \"USUARIO\" WHERE \"id\" = %d;", userId);

                try {
                    // Ejecutar la consulta para eliminar al usuario
                    manager.getConexion().sqlModification(query);

                    // Mostrar el mensaje de éxito
                    JOptionPane.showMessageDialog(
                            null,
                            "Usuario eliminado correctamente.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );


                    System.out.println("Usuario eliminado correctamente.");
                } catch (SQLException ex) {
                    // Si ocurre un error en la eliminación, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al eliminar el usuario: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    System.err.println("Error al eliminar el usuario: " + ex.getMessage());
                }
            } else {
                // Si el usuario cancela, no se hace nada
                System.out.println("Eliminación cancelada.");
            }
        }
    }
}
