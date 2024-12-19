package org.example.multiDSC.controller.listeners.ConfAdminView;

import org.example.multiDSC.model.controllModels.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfAdminButtonListener implements ActionListener {

    private final Manager manager;

    public ConfAdminButtonListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón Editar
        if (e.getSource() == manager.getMainController().getConfAdmin().getEditarButton()) {
            // Habilitar los JTextField para edición
            for (Component component : manager.getMainController().getConfAdmin().getFieldsPanel().getComponents()) {
                if (component instanceof JPanel) {
                    JPanel rowPanel = (JPanel) component;
                    for (Component field : rowPanel.getComponents()) {
                        if (field instanceof JTextField) {
                            JTextField textField = (JTextField) field;
                            textField.setEditable(true);
                            textField.setBackground(Color.LIGHT_GRAY); // Indicar edición con un color
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
            // Deshabilitar los JTextField
            for (Component component : manager.getMainController().getConfAdmin().getFieldsPanel().getComponents()) {
                if (component instanceof JPanel) {
                    JPanel rowPanel = (JPanel) component;
                    for (Component field : rowPanel.getComponents()) {
                        if (field instanceof JTextField) {
                            JTextField textField = (JTextField) field;
                            textField.setEditable(false);
                            textField.setBackground(Color.GRAY); // Restaurar el color original
                        }
                    }
                }
            }

            // Eliminar el botón Apply
            manager.getMainController().getConfAdmin().removeNewButton();
        }
    }
}
