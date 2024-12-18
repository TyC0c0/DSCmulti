package org.example.multiDSC.controller.listeners.ftp;

import org.example.multiDSC.view.FTPView;
import org.example.multiDSC.view.PostLoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerFTP implements ActionListener {

    private FTPView ftpView;

    public ButtonListenerFTP (FTPView ftpView) {
        this.ftpView = ftpView;
    }

//    public void deteleDirectory() {
//        String selectedDir = ftpView
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Exit":
                ftpView.dispose();
                new PostLoginView();
                break;

            default:
                System.out.println("Unknown command..."+command);
                break;
        }
    }
}
