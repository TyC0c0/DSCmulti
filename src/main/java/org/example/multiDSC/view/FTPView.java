package org.example.multiDSC.view;

import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.ConexionThread;
import org.example.multiDSC.controller.ftpServer.ClientFTP;
import org.example.multiDSC.controller.ftpServer.LocalServiceFTP;
import org.example.multiDSC.controller.listeners.ftp.ButtonListenerFTP;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.FTPModel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.sql.SQLException;

/**
 * FTPView - Main view for the FTP functions of the app.
 *
 * @author Ramón Reina González, Alvaro Garcia Lopez
 * @version 1.5
 */

public class FTPView extends JFrame {

    // Attributes
    private FTPModel model;
    private JScrollPane panelTree;
    private JTree tree;
    private JTextField renameFieldText;

    private JButton reloadButton;
    private JButton renameButton;
    private JButton createButton;
    private JButton deleteButton;
    private JButton downloadButton;
    private JButton exitButton;

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel topPanel;
    private JPanel topRightPanel;
    private JPanel downPanel;

    private ClientFTP clientFTP;
    private LocalServiceFTP localServiceFTP;

    // Constructor
    public FTPView(String directoryName, Manager manager, FTPModel ftpModel) {
        this.model = ftpModel;

        initializeFTPClient();
        initializeLocalServiceFTP(directoryName);
        setupMainFrame();
        initializeComponents();
        setupPanels();
        setupActions();
        localServiceFTP.loadDirectories(directoryName);

        setVisible(true);
    }

    private void initializeFTPClient() {
        clientFTP = new ClientFTP();
        if (!clientFTP.connectFTP()) {
            Utils.showErrorWindow(this, "Error de conexión con el servidor FTP.", "Error");
            throw new RuntimeException("Failed to connect to FTP server");
        }
    }

    private void initializeLocalServiceFTP(String directoryName) {
        tree = new JTree();
        this.localServiceFTP = new LocalServiceFTP(clientFTP, tree);
    }

    private void setupMainFrame() {
        setTitle("FTP Window");
        setLayout(new BorderLayout());
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializeComponents() {
        topPanel = new JPanel();
        topRightPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel(new BorderLayout());
        downPanel = new JPanel(new BorderLayout());

        reloadButton = createSizedButton(model.getText().get(5), true);
        renameFieldText = new JTextField();
        renameFieldText.setPreferredSize(new Dimension(200, 30));
        renameButton = createSizedButton(model.getText().get(6), false);
        createButton = createSizedButton(model.getText().get(0), false);
        deleteButton = createSizedButton(model.getText().get(1), false);
        downloadButton = createSizedButton(model.getText().get(2), false);
        exitButton = createSizedButton(model.getText().get(4), false);

        setupTreeRenderer();
    }

    private void setupTreeRenderer() {
        tree.setBackground(new Color(100, 100, 100));
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setBackgroundNonSelectionColor(new Color(100, 100, 100));
        renderer.setBackgroundSelectionColor(new Color(150, 150, 150));
        renderer.setTextNonSelectionColor(Color.WHITE);
        renderer.setTextSelectionColor(Color.WHITE);
    }

    private void setupPanels() {
        setupTopPanel();
        setupLeftPanel();
        setupRightPanel();
        setupDownPanel();

        add(leftPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.CENTER);
        add(downPanel, BorderLayout.SOUTH);
    }

    private void setupTopPanel() {
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        topPanel.add(reloadButton);
        topPanel.add(Box.createRigidArea(new Dimension(78, 0)));
        topPanel.add(renameFieldText);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(renameButton);
    }

    private void setupLeftPanel() {
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.DARK_GRAY);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(createButton);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 40)));
        leftPanel.add(deleteButton);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 40)));
        leftPanel.add(downloadButton);
        leftPanel.add(Box.createVerticalGlue());
    }

    private void setupRightPanel() {
        JScrollPane panelTree = new JScrollPane(tree);
        panelTree.setBackground(new Color(100, 100, 100));

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(Color.DARK_GRAY);
        exitButton.setPreferredSize(new Dimension(100, 30));
        exitPanel.add(exitButton);

        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(panelTree, BorderLayout.CENTER);
        rightPanel.add(exitPanel, BorderLayout.SOUTH);
    }

    private void setupDownPanel() {
        downPanel.setBackground(Color.BLACK);
        downPanel.setForeground(Color.WHITE);
    }

    private void setupActions() {
        reloadButton.setActionCommand("Reload");
        reloadButton.addActionListener(new ButtonListenerFTP(this));
        renameButton.setActionCommand("Rename");
        renameButton.addActionListener(new ButtonListenerFTP(this));
        createButton.setActionCommand("Create");
        createButton.addActionListener(new ButtonListenerFTP(this));
        deleteButton.setActionCommand("Delete");
        deleteButton.addActionListener(new ButtonListenerFTP(this));
        downloadButton.setActionCommand("Download");
        downloadButton.addActionListener(new ButtonListenerFTP(this));
        exitButton.setActionCommand("Exit");
        exitButton.addActionListener(new ButtonListenerFTP(this));
    }

    private JButton createSizedButton(String text, boolean isReloadButton) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(isReloadButton ? new Dimension(30, 40) : new Dimension(150, 30));
        return button;
    }

    public LocalServiceFTP getLocalServiceFTP() {
        return localServiceFTP;
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        Utils utils = new Utils();
        ConectionBD conexion = new ConectionBD();
        try {
            conexion.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConexionThread hconect = new ConexionThread(conexion);

        manager.setTable(utils.switchLanguage("ingles"));
        manager.setConexion(conexion);
        manager.setConexionThread(hconect);

        FTPModel model = new FTPModel(manager);
        FTPView ftpView = new FTPView("/", manager, model);
        ftpView.setVisible(true);
    }
}