package org.example.multiDSC.controller.listeners.ConfUserView;

import org.example.multiDSC.model.controllModels.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConfUserViewButtonsListener implements ActionListener {
    private final Manager manager;
    public ConfUserViewButtonsListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manager.getMainController().getConfUser().getButtons().get(1)){
            for (JTextField textField : manager.getMainController().getConfUser().getTextFields()) {
                textField.setEnabled(true); // Activate the text fields
                textField.setBackground(Color.WHITE);
            }
            manager.getMainController().getConfUser().createModificationButtons();
            manager.getMainController().UserViewAddModButtonListener();
            manager.getMainController().getConfUser().getButtons().get(1).setEnabled(false);
        }else if (e.getSource() == manager.getMainController().getConfUser().getExitButton()){
            manager.getMainController().getConfUser().dispose();
            manager.getMainController().getPostLogin().getFrame().setVisible(true);
        }else if (e.getSource() == manager.getMainController().getConfUser().getModButton()){
            for (JButton button : manager.getMainController().getConfUser().getModificationButtons()) {
              manager.getMainController().getConfUser().getButtonsPanel().remove(button);
               manager.getMainController().getConfUser().getButtons().get(1).setEnabled(true);
            }
            manager.getMainController().getConfUser().getModificationButtons().clear();
            manager.getMainController().getConfUser().getButtonsPanel().setVisible(false); // Hide the button panel
            for (JTextField textField : manager.getMainController().getConfUser().getTextFields()) {
                textField.setEnabled(false); // Deactivate the text fields
                textField.setBackground(Color.GRAY);
                textField.setText(""); // Clean the text fields
            }
            manager.getMainController().getConfUser().getButtonsPanel().revalidate();
            manager.getMainController().getConfUser().getButtonsPanel().repaint();
        }else if(e.getSource() == manager.getMainController().getConfUser().getModificationButtons().get(0)){
            System.out.println("Hola, soy el botón aplicar");

            // Obtener los valores desde los campos de texto
            String nickname = manager.getMainController().getConfUser().getTextFields().get(0).getText();  // Nickname del usuario
            String nombre = manager.getMainController().getConfUser().getTextFields().get(1).getText();    // Correo
            String apellidos = manager.getMainController().getConfUser().getTextFields().get(2).getText();    // Nombre
            String correo = manager.getMainController().getConfUser().getTextFields().get(3).getText(); // Apellidos
            String contrasenia = manager.getMainController().getConfUser().getTextFields().get(4).getText();       // DNI
            String dni = manager.getMainController().getConfUser().getTextFields().get(5).getText(); // Contraseña
            String idRolString = manager.getMainController().getConfUser().getTextFields().get(6).getText(); // id_rol

            String nombreAux = manager.getUserNickname();
// Imprimir los valores
            System.out.println("Nickname: " + nickname);
            System.out.println("Nombre: " + nombre);
            System.out.println("Apellidos: " + apellidos);
            System.out.println("Correo: " + correo);
            System.out.println("Contraseña: " + contrasenia);
            System.out.println("DNI: " + dni);
            System.out.println("ID Rol: " + idRolString);

            // Validar que el id_rol es un número
            int idRol = -1;
            try {
                idRol = Integer.parseInt(idRolString);
            } catch (NumberFormatException ex) {
                System.err.println("ID de rol no válido: " + idRolString);
            }

            if (nickname != null && !nickname.isEmpty() && idRol != -1) {
                // Crear la consulta SQL para actualizar el usuario utilizando Statement
                String query = "UPDATE \"USUARIO\" SET \"Correo\" = '" + correo + "', \"Nombre\" = '" + nombre + "', \"Apellidos\" = '" + apellidos + "', \"DNI\" = '" + dni + "', \"Contraseña\" = '" + contrasenia + "', id_rol = " + idRol + " WHERE \"Nickname\" = '" + nombreAux + "'";

        // Llamar al método sqlModification con la consulta
                try {
                    manager.getConexion().sqlModification(query);
                } catch (SQLException ex) {
                    System.err.println("Error al actualizar el usuario: " + ex.getMessage());
                }
            } else {
                System.err.println("ID de usuario inválido.");
            }
        }

    }
        }


