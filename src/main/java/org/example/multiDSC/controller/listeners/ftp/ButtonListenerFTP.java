package org.example.multiDSC.controller.listeners.ftp;

import org.example.multiDSC.controller.ftpServer.ClientFTP;
import org.example.multiDSC.controller.ftpServer.LocalServiceFTP;
import org.example.multiDSC.view.FTPView;
import org.example.multiDSC.view.PostLoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerFTP implements ActionListener {

    private LocalServiceFTP localServiceFTP;
    private FTPView ftpView;
    private ClientFTP clientFTP;

    public ButtonListenerFTP (FTPView ftpView) {
        this.ftpView = ftpView;
        this.localServiceFTP = ftpView.getLocalServiceFTP();
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
                //new PostLoginView();
                break;

            case "Reload":
                localServiceFTP.reloadTree();
                break;

            case "Rename":
                localServiceFTP.renameFile();
                break;

            case "Create":

                clientFTP.createDirectory("");
                break;

            case "Delete":
                localServiceFTP.deleteFtp();
                break;

            case "Download":
                break;


            default:
                System.out.println("Unknown command..."+command);
                break;
        }
    }
}
