package org.example.multiDSC.view;

import org.apache.commons.net.ftp.FTPFile;
import org.example.multiDSC.controller.ftpServer.ClientFTP;
import org.example.multiDSC.controller.listeners.ftp.ButtonListenerFTP;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.FTPModel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

/**
 * FTPView - Main view for the FTP functions of the app.
 *
 * @author Ramón Reina González, Alvaro Garcia Lopez
 * @version 1.3
 */

public class FTPView extends JFrame {

    // Attributes
    private FTPModel FTPModel;
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
    private ClientFTP clientFTP;

    // Constructor
    public FTPView(String directoryName, Manager manager, FTPModel ftpModel) {
        
        this.FTPModel = ftpModel;
        
        // Starting elements
        clientFTP = new ClientFTP();
        if (!clientFTP.connectFTP()) {
            System.out.println("Error de conexión con el servidor FTP...");
            return;
        }

        setTitle("FTP Window");
        setLayout(new BorderLayout()); // Use BorderLayout for the main frame
        setSize(700, 500); // Adjust size dynamically based on content
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exit on close
        // Top panel for the reload button and rename components
        topPanel = new JPanel();
        topRightPanel = new JPanel();

        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.X_AXIS));

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(Color.DARK_GRAY);

        reloadButton = createSizedButton(FTPModel.getText().get(5), true);
        renameFieldText = new JTextField();
        renameFieldText.setPreferredSize(new Dimension(200, 30));
        renameButton = createSizedButton(FTPModel.getText().get(6), false);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        topPanel.add(reloadButton);
        topPanel.add(Box.createRigidArea(new Dimension(78, 0))); // Add space between components
        topPanel.add(renameFieldText);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between components
        topPanel.add(renameButton);

        // Left panel with buttons
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Stack vertically

        managePermissionsButton = createSizedButton(FTPModel.getText().get(0), false);
        uploadFolderButton = createSizedButton(FTPModel.getText().get(1), false);
        downloadFolderButton = createSizedButton(FTPModel.getText().get(2), false);
        manageDirectoryButton = createSizedButton(FTPModel.getText().get(3), false);
        downloadButton = createSizedButton(FTPModel.getText().get(7),false);

        leftPanel.setBackground(Color.DARK_GRAY); // Fondo gris suave para el panel izquierdo

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
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Centra el botón
        exitPanel.setBackground(Color.DARK_GRAY);
        exitButton = createSizedButton(FTPModel.getText().get(4),false);
        exitButton.setPreferredSize(new Dimension(100, 30)); // Ajusta el tamaño del botón
        exitButton.setActionCommand("Exit");
        exitButton.addActionListener(new ButtonListenerFTP(this));
        exitPanel.add(exitButton);

        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(panelTree, BorderLayout.CENTER);
        rightPanel.add(exitPanel, BorderLayout.SOUTH); // Añade el panel del botón Exit

        // Panel inferior
        downPanel.setBackground(Color.BLACK); // Fondo negro para el panel inferior
        downPanel.setForeground(Color.WHITE); // Texto blanco

        // Add panels to the main frame
        add(leftPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH); // Top panel with reload and rename
        add(rightPanel, BorderLayout.CENTER);
        add(downPanel, BorderLayout.SOUTH);

        loadDirectories(directoryName);
        setVisible(true);
    }

    public void loadDirectories(String directoryName) {
        DefaultMutableTreeNode mainDir = new DefaultMutableTreeNode(directoryName.equals("/") ? "Main FTP" : directoryName);

        try {
            // Cogemos los directorios de un usuario para cargarlo
            FTPFile[] files = clientFTP.showDirectoriesUser(directoryName);

            if (files != null && files.length > 0) {
                for (FTPFile f : files) {
                    // Filter server FTP files
                    if (f.isDirectory()) {
                        mainDir.add(new DefaultMutableTreeNode("📁 "+f.getName()));
                    } else if (f.isFile()) {
                        mainDir.add(new DefaultMutableTreeNode("🗎 "+f.getName()));
                    } else if (f.isSymbolicLink()) {
                        mainDir.add(new DefaultMutableTreeNode("[Link] "+f.getName()));
                    } else if (f.isUnknown()) {
                        mainDir.add(new DefaultMutableTreeNode("[?] "+f.getName() + " (Unknown file)"));
                    }
                }
            } else {
                System.out.println("No hay directorios disponibles para este usuario.");
            }
        } catch (Exception e) {
            System.out.println("Ha habido un problema en la carga de directorios");
            e.printStackTrace();
        }
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setLeafIcon(new ImageIcon("Delete Icon")); // This delete the icon in the tree

        tree.setModel(new DefaultTreeModel(mainDir));

        // Añadir TreeSelectionListener para manejar clics en los nodos
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent event) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }
                String nodeName = selectedNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");
                loadDirectoryContent(selectedNode, directoryName+ "/" + nodeName);
            }
        });
    }

    private void loadDirectoryContent(DefaultMutableTreeNode parentNode, String directoryName) {
        try {
            // Obtener el contenido del directorio seleccionado
            FTPFile[] files = clientFTP.showDirectoriesUser(directoryName);

            if (files != null && files.length > 0) {
                parentNode.removeAllChildren(); // Limpia el nodo actual antes de añadir contenido

                for (FTPFile f : files) {
                    if (f.isDirectory()) {
                        parentNode.add(new DefaultMutableTreeNode("📁 " + f.getName())); // Subdirectorio
                    } else if (f.isFile()) {
                        parentNode.add(new DefaultMutableTreeNode("🗎 " + f.getName())); // Archivo
                    }
                }
                ((DefaultTreeModel) tree.getModel()).reload(parentNode); // Recargar nodo en el árbol
            } else {
                System.out.println("El directorio está vacío: " + directoryName);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el contenido del directorio: " + directoryName);
            e.printStackTrace();
        }
    }


    private JButton createSizedButton(String text, boolean isReloadButton) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE); // Fondo blanco para los botones
        button.setForeground(Color.BLACK); // Texto negro
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setMaximumSize(isReloadButton ? new Dimension(30, 40) : new Dimension(150, 30)); // Button size
        return button;
    }
    
}
