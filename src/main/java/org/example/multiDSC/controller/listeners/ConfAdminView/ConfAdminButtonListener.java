package org.example.multiDSC.controller.listeners.ConfAdminView;

import org.example.multiDSC.model.controllModels.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConfAdminButtonListener implements ActionListener {

    private final Manager manager;

    public ConfAdminButtonListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón Editar
        if (e.getSource() == manager.getMainController().getConfAdmin().getEditarButton()) {
            // Habilitar los JTextField y JComboBox para edición
            for (Component component : manager.getMainController().getConfAdmin().getFieldsPanel().getComponents()) {
                if (component instanceof JPanel) {
                    JPanel rowPanel = (JPanel) component;
                    for (Component field : rowPanel.getComponents()) {
                        if (field instanceof JTextField) {
                            JTextField textField = (JTextField) field;
                            textField.setEditable(true);
                            textField.setBackground(Color.LIGHT_GRAY); // Indicar edición con un color
                        } else if (field instanceof JComboBox) {
                            JComboBox comboBox = (JComboBox) field;
                            comboBox.setEnabled(true);
                        }
                    }
                }
            }

            // Agregar el botón Apply si aún no está
            manager.getMainController().getConfAdmin().addButtonNextToDelete("Apply");

            // Asignar el ActionListener dinámicamente al nuevo botón Apply
            JButton applyButton = manager.getMainController().getConfAdmin().getApplyButton();
            if (applyButton != null) {
                applyButton.addActionListener(this); // Asociar el mismo ActionListener
            }
        }

        // Botón Apply
        else if (e.getSource() == manager.getMainController().getConfAdmin().getApplyButton()) {
            // Deshabilitar los JTextField y JComboBox
            for (Component component : manager.getMainController().getConfAdmin().getFieldsPanel().getComponents()) {
                if (component instanceof JPanel) {
                    JPanel rowPanel = (JPanel) component;
                    String correo = "";
                    String nuevoCorreo = "";
                    String nombre = "";
                    String rolNombre = "";
                    for (Component field : rowPanel.getComponents()) {
                        if (field instanceof JTextField) {
                            JTextField textField = (JTextField) field;
                            textField.setEditable(false);
                            textField.setBackground(Color.GRAY); // Restaurar el color original
                            if (textField.getColumns() == 20) { // Suponiendo que el campo de correo tiene 20 columnas
                                nuevoCorreo = textField.getText();
                            } else {
                                nombre = textField.getText();
                            }
                        } else if (field instanceof JComboBox) {
                            JComboBox comboBox = (JComboBox) field;
                            comboBox.setEnabled(false);
                            rolNombre = (String) comboBox.getSelectedItem();
                        }
                    }

                    // Actualizar usuario en la base de datos
                    try {
                        String updateQuery = "UPDATE \"USUARIO\" u " +
                                "SET \"Correo\" = '" + nuevoCorreo + "', \"Nombre\" = '" + nombre + "' " +
                                "FROM \"ROLES\" r " +
                                "WHERE u.id_rol = r.id " +
                                "AND r.\"Nombre\" = '" + rolNombre + "'" +
                                "RETURNING u.\"Correo\", u.\"Nombre\", r.\"Nombre\" AS \"Rol_Nombre\"";
                        manager.getConexion().sqlModification(updateQuery);
                    } catch (SQLException ex) {
                        System.err.println("Error al actualizar datos: " + ex.getMessage());
                    }
                }
            }

            // Eliminar el botón Apply
            manager.getMainController().getConfAdmin().removeNewButton();
        }
    }
}
