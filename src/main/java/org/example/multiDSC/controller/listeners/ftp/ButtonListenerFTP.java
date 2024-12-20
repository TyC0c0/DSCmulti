package org.example.multiDSC.controller.listeners.ftp;

import org.example.multiDSC.controller.ftpServer.LocalServiceFTP;
import org.example.multiDSC.view.FTPView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ButtonListenerFTP - This class set actions for the buttons.
 *
 * @author Ramón Reina González
 * @version 1.2
 */

public class ButtonListenerFTP implements ActionListener {

    private LocalServiceFTP localServiceFTP;
    private FTPView ftpView;

    public ButtonListenerFTP (FTPView ftpView) {
        this.ftpView = ftpView;
        this.localServiceFTP = ftpView.getLocalServiceFTP();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Exit":
                ftpView.dispose();
                break;

            case "Reload":
                localServiceFTP.reloadTree();
                break;

            case "Rename":
                localServiceFTP.renameFile();
                break;

            case "Upload":
                localServiceFTP.uploadFile();
                break;
            case "Create":
                localServiceFTP.createNewDirectory();
                break;

            case "Delete":
                localServiceFTP.deleteSelectedFile();
                break;

            case "Download":
                localServiceFTP.downloadFile();
                break;

            default:
                System.err.println("Unknown command..."+command);
                break;
        }
    }
}