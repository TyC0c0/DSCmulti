package org.example.multiDSC.view;

import org.example.multiDSC.model.FTPModel_en;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * FTPView - Main view for the FTP functions of the app.
 *
 * @version 1.1
 */
public class FTPView extends JFrame {

    // Attributes
    private FTPModel_en model;
    private JScrollPane panelTree;
    private JTree tree;
    private JTextField renameFieldText;
    private JButton reloadButton;
    private JButton renameButton;
    private JButton managePermissionsButton;
    private JButton uploadFolderButton;
    private JButton downloadFolderButton;
    private JButton manageDirectoryButton;
    private JButton exitButton;
    private JButton downloadButton;
    private JPanel leftPanel; // 4 rows, 1 column
    private JPanel rightPanel;
    private JPanel topPanel;
    private JPanel topRightPanel;
    private JPanel downPanel;

    // Constructor
    public FTPView() {
        // Starting elements
        JFrame frameFTP = new JFrame("FTP Window");
        frameFTP.setLayout(new BorderLayout()); // Use BorderLayout for the main frame

        model = new FTPModel_en();

        // Top panel for the reload button and rename components
        topPanel = new JPanel();
        topRightPanel = new JPanel();

        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.X_AXIS));

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        reloadButton = createSizedButton(model.getFTPText_en().get(5), true);
        renameFieldText = new JTextField();
        renameFieldText.setPreferredSize(new Dimension(200, 30));
        renameButton = createSizedButton(model.getFTPText_en().get(6), false);


        topPanel.add(reloadButton);
        topPanel.add(Box.createRigidArea(new Dimension(78, 0))); // Add space between components
        topPanel.add(renameFieldText);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between components
        topPanel.add(renameButton);

        // Left panel with buttons
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Stack vertically

        managePermissionsButton = createSizedButton(model.getFTPText_en().get(0), false);
        uploadFolderButton = createSizedButton(model.getFTPText_en().get(1), false);
        downloadFolderButton = createSizedButton(model.getFTPText_en().get(2), false);
        manageDirectoryButton = createSizedButton(model.getFTPText_en().get(3), false);
        downloadButton = createSizedButton(model.getFTPText_en().get(7),false);

        leftPanel.setBackground(new Color(10, 10, 10)); // Fondo gris suave para el panel izquierdo

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(managePermissionsButton);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 40)));
        leftPanel.add(uploadFolderButton);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 40)));
        leftPanel.add(downloadFolderButton);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 40)));
        leftPanel.add(manageDirectoryButton);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 40)));
        leftPanel.add(downloadButton);
        leftPanel.add(Box.createVerticalGlue());

        // Right panel with tree and exit button
        rightPanel = new JPanel(new BorderLayout());
        downPanel = new JPanel(new BorderLayout());

        // Right panel with tree and bottom button
        rightPanel.setBackground(Color.BLACK); // Fondo negro para el panel derecho
        rightPanel.setForeground(Color.WHITE); // Texto blanco

        tree = new JTree();
        tree.setBackground(new Color(100, 100, 100));

        // Set the cell renderer to change the background color of the tree nodes
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setBackgroundNonSelectionColor(new Color(100, 100, 100));
        renderer.setBackgroundSelectionColor(new Color(150, 150, 150));
        renderer.setTextNonSelectionColor(Color.WHITE);
        renderer.setTextSelectionColor(Color.WHITE);

        panelTree = new JScrollPane(tree);
        panelTree.setBackground(new Color(100, 100, 100));

        // Panel para el botón Exit con FlowLayout
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centra el botón
        exitPanel.setBackground(new Color(10, 10, 10));
        exitButton = createSizedButton(model.getFTPText_en().get(4),false);
        exitButton.setPreferredSize(new Dimension(100, 30)); // Ajusta el tamaño del botón
        exitPanel.add(exitButton);

        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(panelTree, BorderLayout.CENTER);
        rightPanel.add(exitPanel, BorderLayout.SOUTH); // Añade el panel del botón Exit

        // Panel inferior
        downPanel.setBackground(Color.BLACK); // Fondo negro para el panel inferior
        downPanel.setForeground(Color.WHITE); // Texto blanco

        // Add panels to the main frame
        frameFTP.add(topPanel, BorderLayout.NORTH); // Top panel with reload and rename
        frameFTP.add(leftPanel, BorderLayout.WEST);
        frameFTP.add(rightPanel, BorderLayout.CENTER);
        frameFTP.add(downPanel, BorderLayout.SOUTH);

        // Create window format
        frameFTP.setSize(700, 500); // Adjust size dynamically based on content
        frameFTP.setVisible(true);
        frameFTP.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JButton createSizedButton(String text, boolean isReloadButton) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE); // Fondo blanco para los botones
        button.setForeground(Color.BLACK); // Texto negro
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setMaximumSize(isReloadButton ? new Dimension(30, 40) : new Dimension(150, 30)); // Button size
        return button;
    }

    public static void main(String[] args) {
        new FTPView();
        System.out.println("Hello world");
    }
}
