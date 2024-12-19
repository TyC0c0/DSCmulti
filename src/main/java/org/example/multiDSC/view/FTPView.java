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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.sql.SQLException;

/**
 * FTPView - Main view for the FTP functions of the app.
 *
 * @author Ramón Reina González, Alvaro Garcia Lopez
 * @version 1.6
 */

public class FTPView extends JFrame {

    // Attributes
    private FTPModel model;
    private JTree tree;
    private JLabel label;

    private JButton uploadButton;
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
        configureTreeSelectionListener();
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

        label = new JLabel("/");

        // Crear el JLabel para mostrar la ruta
        label.setOpaque(true); // Hacer que el JLabel tenga un fondo visible
        label.setBackground(Color.WHITE); // Fondo blanco para parecerse a un campo de texto
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        label.setPreferredSize(new Dimension(500, 30));
        label.setHorizontalAlignment(SwingConstants.LEFT); // Alinear el texto a la izquierda
        label.setFont(new Font("Arial", Font.PLAIN, 16));

        reloadButton = createSizedButton(model.getText().get(0), true);
        renameButton = createSizedButton(model.getText().get(1), false);
        createButton = createSizedButton(model.getText().get(2), false);
        uploadButton = createSizedButton(model.getText().get(3), false);
        deleteButton = createSizedButton(model.getText().get(4), false);
        downloadButton = createSizedButton(model.getText().get(5), false);
        exitButton = createSizedButton(model.getText().get(6), false);

        setupTreeRenderer();
    }

    private void setupTreeRenderer() {
        tree.setBackground(new Color(100, 100, 100));
        tree.setBorder(BorderFactory.createEmptyBorder());
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
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(reloadButton);
        topPanel.add(Box.createRigidArea(new Dimension(78, 0)));
        topPanel.add(label);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(renameButton);
    }

    private void setupLeftPanel() {
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.DARK_GRAY);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(createButton);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 40)));
        leftPanel.add(uploadButton);
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

        uploadButton.setActionCommand("Upload");
        uploadButton.addActionListener(new ButtonListenerFTP(this));

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

    // This method set the root of the file in a label in the view
    private void configureTreeSelectionListener() {
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    TreeNode[] pathNodes = selectedNode.getPath();
                    StringBuilder entireDirBuilder = new StringBuilder();

                    for (TreeNode node : pathNodes) {
                        String nodeName = node.toString().replace("📁 ", "").replace("🗎 ", "");
                        entireDirBuilder.append("/").append(nodeName);
                    }

                    String entireDir = entireDirBuilder.toString(); // Ruta completa
                    label.setText(entireDir); // Actualizar el campo de texto con la ruta progresiva
                }
            }
        });
    }

    public JLabel getLabel() {
        return label;
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