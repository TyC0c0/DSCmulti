package org.example.multiDSC.controller.listeners.Email;

import org.example.multiDSC.model.controllModels.Manager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MailLabelListener implements MouseListener {

    private Manager manager;

    public MailLabelListener(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String labelText = ((javax.swing.JLabel) e.getSource()).getText();
        // Llamar al método de la vista para actualizar la lista de correos según la categoría seleccionada
//        manager.getMainController().getMail().updateMailList(labelText);
    }

    // Métodos restantes de MouseListener que no se modifican
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}

