package org.example.multiDSC.controller.listeners.ConfAdminView;

import org.example.multiDSC.model.controllModels.Manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        System.out.println("no he modificado porque no hago nada todavia " + userId);
    }
}
