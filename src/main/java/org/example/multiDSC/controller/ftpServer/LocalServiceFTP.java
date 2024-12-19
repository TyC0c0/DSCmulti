package org.example.multiDSC.controller.ftpServer;

import org.apache.commons.net.ftp.FTPFile;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.controller.ftpServer.ClientFTP;
import org.example.multiDSC.view.FTPView;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class LocalServiceFTP {

    private ClientFTP clientFTP;
    private JTree tree;

    public LocalServiceFTP(ClientFTP clientFTP, JTree tree) {
        this.clientFTP = clientFTP;
        this.tree = tree;
    }

    // This method is the most important method of delete, because the method can filter directories and files, and delete the selected one
    public void deleteSelectedFile() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            Utils.showWarningWindow(null, "No se ha seleccionado ningún archivo o directorio.", "Aviso");
            System.err.println("No se ha seleccionado ningún archivo o directorio...");
            return;
        }

        String direction = getFullPathFromNode(selectedNode);
        System.out.println("Intentando eliminar: " + direction);

        boolean success = false; // Inicializar como false por defecto
        int confirm = Utils.showConfirmDialog(null, "¿Seguro que lo quieres eliminar?", "Aviso");

        if (confirm == JOptionPane.YES_OPTION) { // Solo proceder si el usuario confirma
            if (isDirectoryNode(selectedNode)) { // Si es un directorio, eliminamos de manera recursiva
                success = clientFTP.deleteDirectoryRecursive(direction);
            } else { // Si es un archivo, eliminar un archivo
                success = clientFTP.deleteFile(direction);
            }

            if (success) {
                System.out.println("Eliminación completada correctamente: " + direction);
                reloadTree();
            } else {
                Utils.showErrorWindow(null, "No se ha podido eliminar el archivo o directorio seleccionado.", "Error");
            }
        } else {
            System.out.println("La eliminación se ha cancelada.");
        }
    }

    private String getFullPathFromNode(DefaultMutableTreeNode node) {
        StringBuilder fullPath = new StringBuilder("C:\\FTPserver"); // Ruta base física

        TreeNode[] nodes = node.getPath();
        for (int i = 1; i < nodes.length; i++) { // Omitir la raíz lógica (FTP Server)
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) nodes[i];
            String part = currentNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");
            if (!part.isEmpty()) {
                fullPath.append("\\").append(part);
            }
        }
        return fullPath.toString();
    }

    private boolean isDirectoryNode(DefaultMutableTreeNode node) {
        return node.toString().startsWith("📁");
    }

    public void reloadTree() {
        loadDirectories("/");
    }

    // This method do the function of the button rename
    public void renameFile() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            Utils.showWarningWindow(null, "Por favor, selecciona un archivo o directorio para renombrar.", "Aviso");
            return;
        }

        String oldName = selectedNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");

        // Determinar si es un archivo o un directorio
        boolean isFile = oldName.contains(".");
        String newName = JOptionPane.showInputDialog(null, "Introduce el nuevo nombre:", oldName);

        if (newName == null || newName.trim().isEmpty()) {
            Utils.showErrorWindow(null, "El nombre no puede estar vacío.", "Error");
            return;
        }

        // Validar que el archivo mantenga su extensión (ejemplo hacer rename a foto.png --> mantener el png)
        if (isFile) {
            String extension = oldName.substring(oldName.lastIndexOf(".") + 1); // Obtener la extensión del archivo
            if (!newName.contains(".")) {
                // Si no contiene un punto, agregar la extensión automáticamente
                newName += "." + extension;
            } else {
                // Verificar si el usuario cambió la extensión
                String newExtension = newName.substring(newName.lastIndexOf(".") + 1);
                if (!newExtension.equalsIgnoreCase(extension)) {
                    int response = Utils.showConfirmDialog(null, "Estás cambiando la extensión de " + extension + " a "+newExtension+".  ¿Quieres continuar?\"", "Aviso");
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
            }
        }

        try {
            // Determinar la ruta completa
            String directoryPath = "/"; // Cambia esto según tu lógica de directorio actual
            String oldPath = directoryPath + oldName;
            String newPath = directoryPath + newName;

            boolean success = clientFTP.rename(oldPath, newPath);

            if (success) {
                Utils.showConfirmDialog(null, "Renombrado exitosamente.", "Éxito");
                reloadTree();
            } else {
                Utils.showErrorWindow(null, "No se pudo renombrar el archivo o directorio.", "Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showErrorWindow(null, "Ocurrió un error al intentar renombrar.", "Error");
        }
    }

    public void loadDirectories(String directoryName) {
        DefaultMutableTreeNode mainDir = new DefaultMutableTreeNode("FTP Server");

        try {
            FTPFile[] files = clientFTP.showDirectoriesUser(directoryName);

            if (files != null && files.length > 0) {
                for (FTPFile file : files) {
                    if (file.isDirectory()) {
                        // Mostrar el nombre del directorio con el emoticono
                        DefaultMutableTreeNode dirNode = new DefaultMutableTreeNode("📁 " + file.getName());

                        // Añadir marcador para carga dinámica (En caso de que tarde en cargar algun directorio)
                        dirNode.add(new DefaultMutableTreeNode("Cargando..."));
                        mainDir.add(dirNode);
                        //completado(true) porque carga los directorios

                    } else if (file.isFile()) {
                        // Mostrar el nombre del archivo con el emoticono
                        DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode("🗎 " + file.getName());
                        mainDir.add(fileNode);

                        //completado(true) porque carga los archivos
                    }
                }
            } else {
                Utils.showInfoWindow(null, "El directorio raíz está vacío.", "Información");
                System.out.println("El directorio raíz está vacío: " + directoryName);
                //Utils.LogRegister();

            }
        } catch (Exception e) {
            Utils.showErrorWindow(null, "Error al cargar los directorios desde la raíz.", "Error");
            System.out.println("Error al cargar los directorios desde la raíz: " + directoryName);
            //Utils.LogRegister();
            e.printStackTrace();
        }

        ((DefaultTreeCellRenderer) tree.getCellRenderer()).setLeafIcon(null);

        // Asignamos el modelo al árbol (jtree)
        tree.setModel(new DefaultTreeModel(mainDir));

        // Añadimos un listener para la expansión dinámica de los directorios
        tree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            @Override
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();

                // Verificar si el nodo tiene un marcador de carga
                if (node.getChildCount() == 1 && node.getFirstChild().toString().equals("Cargando...")) {
                    // Eliminar el marcador antes de cargar contenido real
                    node.removeAllChildren();

                    // Usar el nombre del nodo para cargar la ruta
                    String parentPath = getParentPath(node);
                    loadSubDirectory(node, parentPath);

                    // Actualizar el modelo
                    ((DefaultTreeModel) tree.getModel()).reload(node);
                }
            }

            @Override
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent event) {
                // No es necesario manejar colapsos en esta implementación
            }
        });
    }

    private String getParentPath(DefaultMutableTreeNode node) {
        StringBuilder pathBuilder = new StringBuilder();
        TreeNode[] pathNodes = node.getPath();

        for (int i = 1; i < pathNodes.length; i++) {
            pathBuilder.append("/").append(pathNodes[i].toString().replace("📁 ", "").replace("🗎 ", ""));
        }
        return pathBuilder.toString();
    }

    // This method run the subdirectories of the ftp
    private void loadSubDirectory(DefaultMutableTreeNode parentNode, String directoryName) {
        try {
            FTPFile[] files = clientFTP.showDirectoriesUser(directoryName);

            if (files != null && files.length > 0) {
                for (FTPFile file : files) {
                    if (file.isDirectory()) {
                        DefaultMutableTreeNode dirNode = new DefaultMutableTreeNode("📁 " + file.getName());

                        // Añadir marcador para carga dinámica
                        dirNode.add(new DefaultMutableTreeNode("Cargando..."));
                        parentNode.add(dirNode);
                        // Añade el directorio al nodo

                    } else if (file.isFile()) {
                        DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode("🗎 " + file.getName());
                        parentNode.add(fileNode);
                        // Añade el archivo al nodo
                    }
                }
            } else {
                Utils.showInfoWindow(null, "El directorio está vacío.", "Información");
                System.out.println("El directorio está vacío: " + directoryName);
            }
        } catch (Exception e) {
            Utils.showErrorWindow(null, "Error al cargar el contenido del directorio:", "Error");
            System.err.println("Error al cargar el contenido del directorio: " + directoryName);
            e.printStackTrace();
        }
    }
}
