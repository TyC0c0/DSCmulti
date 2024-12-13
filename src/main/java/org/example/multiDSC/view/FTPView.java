package org.example.multiDSC.view;

import org.example.multiDSC.model.FTPModel_en;

import javax.swing.*;
import java.awt.*;

/*FTPView - Main view for the FTP functions of the app.*
@author Jose Manuel Campos Lopez
@version 1.0*/
public class FTPView extends JFrame {

    // Attributes
    private FTPModel_en model;
    private JPanel panelMain;
    private JScrollPane panelTree;
    private JTree tree;
    private JButton managePermissionsButton;
    private JButton uploadFolderButton;
    private JButton downloadFolderButton;
    private JButton manageDirectoryButton;
    private JButton exitButton;
    private JPanel leftPanel; // 4 rows, 1 column
    private JPanel rightPanel;
    // Constructor
    public FTPView() {
        // Starting elements
        JFrame frameFTP = new JFrame("FTP Window");
        frameFTP.setLayout(new BorderLayout()); // Use BorderLayout for the main frame

        model = new FTPModel_en();

        // Left panel with buttons
        leftPanel = new JPanel(new GridLayout(4, 1, 5, 5)); // 4 rows, 1 column
        managePermissionsButton = new JButton(model.getFTPText_en().get(0));
        uploadFolderButton = new JButton(model.getFTPText_en().get(1));
        downloadFolderButton = new JButton(model.getFTPText_en().get(2));
        manageDirectoryButton = new JButton(model.getFTPText_en().get(3));

        leftPanel.add(managePermissionsButton);
        leftPanel.add(uploadFolderButton);
        leftPanel.add(downloadFolderButton);
        leftPanel.add(manageDirectoryButton);

        // Right panel with tree and bottom button
        rightPanel = new JPanel(new BorderLayout());
        tree = new JTree();
        panelTree = new JScrollPane(tree);
        exitButton = new JButton(model.getFTPText_en().get(4));

        rightPanel.add(panelTree, BorderLayout.CENTER);
        rightPanel.add(exitButton, BorderLayout.SOUTH);

        // Add panels to the main frame
        frameFTP.add(leftPanel, BorderLayout.WEST);
        frameFTP.add(rightPanel, BorderLayout.CENTER);

        // Create window format
        frameFTP.setSize(400, 600); // Adjust size dynamically based on content
        frameFTP.setVisible(true);
        frameFTP.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new FTPView();
        System.out.println("Hello world");
    }
}
