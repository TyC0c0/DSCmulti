package org.example.multiDSC.controller.ftpServer;

import org.apache.commons.net.ftp.FTPFile;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.controller.ftpServer.ClientFTP;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;

public class LocalServiceFTP {

    private ClientFTP clientFTP;
    private JTree tree;

    public LocalServiceFTP(ClientFTP clientFTP, JTree tree) {
        this.clientFTP = clientFTP;
        this.tree = tree;
    }

    public boolean delete(String path) {
        try {
            // Listar archivos y directorios en la ruta especificada
            FTPFile[] files = clientFTP.listFilesAndDirectories(path);

            if (files != null && files.length > 0) {
                // Si tiene contenido, eliminar recursivamente
                for (FTPFile file : files) {
                    String filePath = path + "/" + file.getName();
                    if (file.isDirectory()) {
                        // Eliminar subdirectorios recursivamente
                        if (!delete(filePath)) {
                            System.err.printf("Error al eliminar subdirectorio: %s%n", filePath);
                            return false;
                        }
                    } else {
                        // Eliminar archivo
                        if (!clientFTP.deleteFile(filePath)) {
                            System.err.printf("No se pudo eliminar archivo: %s%n", filePath);
                            return false;
                        } else {
                            System.out.printf("Archivo eliminado ", filePath);
                        }
                    }
                }
            }

            // Intentar eliminar el directorio vacío o archivo
            if (clientFTP.removeDirectory(path)) {
                System.out.printf("Directorio eliminado: %s%n", path);
                return true;
            } else if (clientFTP.deleteFile(path)) {
                System.out.printf("Archivo eliminado: %s%n", path);
                return true;
            } else {
                System.err.printf("No se pudo eliminar el archivo o directorio: %s%n", path);
                return false;
            }
        } catch (Exception e) {
            System.err.printf("Error al eliminar '%s': %s%n", path, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void deleteSelectedFile() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            System.err.println("No se ha seleccionado ningún archivo.");
            return;
        }

        String filePath = selectedNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");

        System.out.println("Intentando eliminar archivo: " + filePath);

        if (clientFTP.deleteFile(filePath)) {
            System.out.println("El archivo fue eliminado correctamente.");
            reloadTree(); // Recarga el árbol para reflejar los cambios
        } else {
            System.err.println("No se pudo eliminar el archivo.");
        }
    }


    // Eliminar el nodo seleccionado en el árbol JTree
    public void deleteFtp() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            System.err.println("No se ha seleccionado ningún archivo o directorio.");
            return;
        }

        String path = selectedNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");
        System.out.println("Intentando eliminar: " + path);

        if (delete(path)) {
            System.out.println("Eliminación completada correctamente.");
            reloadTree(); // Recargar árbol para reflejar los cambios
        } else {
            System.err.println("No se pudo eliminar el archivo o directorio.");
        }
    }

    public void reloadTree() {
        loadDirectories("/");
    }

    public void renameFile() {
        // Obtener el nodo seleccionado en el árbol
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            Utils.showWarningWindow(null, "Por favor, selecciona un archivo o directorio para renombrar.", "Aviso");
            return;
        }

        // Obtener el nombre actual del archivo o directorio
        String oldName = selectedNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");

        // Solicitar al usuario el nuevo nombre
        String newName = JOptionPane.showInputDialog(null, "Introduce el nuevo nombre:", oldName);

        if (newName == null || newName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Determinar la ruta completa
            String directoryPath = "/"; // Cambia esto según tu lógica de directorio actual
            String oldPath = directoryPath + oldName;
            String newPath = directoryPath + newName;

            boolean success = clientFTP.rename(oldPath, newPath);

            if (success) {
                JOptionPane.showMessageDialog(null, "Renombrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                reloadTree(); // Recargar el árbol para reflejar los cambios
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo renombrar el archivo o directorio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar renombrar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

        // Añadir TreeSelectionListener para manejar clicks en los nodos
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent event) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }

                // Check if the node selected is a file or a folder
                String nodeName = selectedNode.getUserObject().toString();
                if (nodeName.startsWith("📁")) {
                    nodeName = nodeName.replace("📁 ", "");
                    loadDirectoryContent(selectedNode, directoryName + "/" + nodeName);
                } else {
                    System.out.println("Archivo seleccionado: " + nodeName);
                }
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
}