package org.example.multiDSC.controller.ftpServer;

import org.apache.commons.net.ftp.FTPFile;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.view.FTPView;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * LocalServiceFTP - This class have the corrects methods of the view FTPView.
 *
 * @author Ramón Reina González
 * @version 1.2
 */

public class LocalServiceFTP {

    private ClientFTP clientFTP;
    private JTree tree;
    private FTPView ftpView;

    // Constructor
    public LocalServiceFTP(ClientFTP clientFTP, JTree tree, FTPView ftpView) {
        this.clientFTP = clientFTP;
        this.tree = tree;
        this.ftpView = ftpView;
    }

    // This method is the function of the button Create Directory
    public void createNewDirectory() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            Utils.showWarningWindow(null, "Por favor, selecciona un directorio donde crear el nuevo directorio.", "Aviso");
            return;
        }

        // Comprobaciones
        System.out.println("Nodo seleccionado: " + selectedNode.getUserObject());
        System.out.println("Es directorio: " + isDirectoryNode(selectedNode));

        if (!isDirectoryNode(selectedNode)) {
            Utils.showErrorWindow(null, "El elemento seleccionado no es un directorio.", "Error");
            return;
        }

        String parentPath = getDirNodeCreate(selectedNode);

        String newDirName = Utils.showInputDialog(null, "Introduce el nombre del nuevo directorio:", "Crear Directorio");

        if (newDirName == null || newDirName.trim().isEmpty()) {
            Utils.showErrorWindow(null, "El nombre del directorio no puede estar vacío.", "Error");
            return;
        }

        try {
            String newDirPath = parentPath + "/" + newDirName;
            boolean success = clientFTP.createDirectory(newDirPath);

            List<String> expandedPaths = saveTreeState();

            if (success) {
                Utils.showInfoWindow(null, "El directorio se ha creado exitosamente.", "Éxito");
                reloadTree();
                restoreTreeState(expandedPaths); // Rescatamos el estado del jtree
            } else {
                Utils.showErrorWindow(null, "No se pudo crear el directorio. Comprueba los permisos o el nombre.", "Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showErrorWindow(null, "Ha habido un error al intentar crear el directorio.", "Error");
        }
    }

    private String getDirNodeCreate(DefaultMutableTreeNode node) {
        StringBuilder fullDirection = new StringBuilder();

        TreeNode[] nodes = node.getPath();
        for (int i = 0; i < nodes.length; i++) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) nodes[i];
            String part = currentNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");
            if (!part.isEmpty()) {
                if (i == 0 && part.equals("FTP Server")) {
                    fullDirection.append("/"); // El nodo raíz representa /
                } else {
                    fullDirection.append(part).append("/");
                }
            }
        }
        if (fullDirection.length() > 1 && fullDirection.charAt(fullDirection.length() - 1) == '/') {
            fullDirection.deleteCharAt(fullDirection.length() - 1);
        }
        return fullDirection.toString();
    }

    private boolean isDirectoryNodeCreate(DefaultMutableTreeNode node) {
        Object userObject = node.getUserObject();
        if (userObject instanceof String) {
            String nodeName = (String) userObject;
            return nodeName.equals("FTP Server") || nodeName.startsWith("📁");
        }
        return false;
    }

    // This method is the most important method of delete, because the method can filter directories and files, and delete the selected one
    public void deleteSelectedFile() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            Utils.showWarningWindow(null, "No se ha seleccionado ningún archivo o directorio.", "Aviso");
            System.err.println("No se ha seleccionado ningún archivo o directorio...");
            return;
        }

        String direction = getDirNodeDelete(selectedNode);
        System.out.println("Intentando eliminar: " + direction);

        boolean success = false; // Inicializar como false por defecto
        int confirm = Utils.showConfirmDialog(null, "¿Seguro que lo quieres eliminar?", "Aviso");

        List<String> expandedPaths = saveTreeState();

        if (confirm == JOptionPane.YES_OPTION) { // Solo proceder si el usuario confirma
            if (isDirectoryNodeDelete(selectedNode)) { // Si es un directorio, eliminamos de manera recursiva
                success = clientFTP.deleteDirectoryRecursive(direction);
            } else { // Si es un archivo, eliminar un archivo
                success = clientFTP.deleteFile(direction);
            }

            if (success) {
                System.out.println("Eliminación completada correctamente: " + direction);
                reloadTree();
                restoreTreeState(expandedPaths); // Rescatamos cómo se había quedado el jtree
            } else {
                Utils.showErrorWindow(null, "No se ha podido eliminar el archivo o directorio seleccionado.", "Error");
            }
        } else {
            System.out.println("La eliminación se ha cancelada.");
        }
    }

    private String getDirNodeDelete(DefaultMutableTreeNode node) {
        StringBuilder fullDirection = new StringBuilder("C:\\FTPserver"); // Ruta base física

        TreeNode[] nodes = node.getPath();
        for (int i = 1; i < nodes.length; i++) { // Omitir la raíz lógica (FTP Server)
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) nodes[i];
            String part = currentNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");
            if (!part.isEmpty()) {
                fullDirection.append("\\").append(part);
            }
        }
        return fullDirection.toString();
    }

    private boolean isDirectoryNodeDelete(DefaultMutableTreeNode node) {
        Object userObject = node.getUserObject();
        if (userObject instanceof String) {
            String nodeName = (String) userObject;
            return nodeName.startsWith("📁");
        }
        return false;
    }

    private String getFullPathFromNode(DefaultMutableTreeNode node) {
        StringBuilder fullDirection = new StringBuilder();

        TreeNode[] nodes = node.getPath();
        for (int i = 0; i < nodes.length; i++) { // Incluimos todos los nodos, incluida la raíz
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) nodes[i];
            String part = currentNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");
            if (!part.isEmpty()) {
                if (i == 0 && part.equals("FTP Server")) {
                    fullDirection.append("/"); // La raíz se interpreta como "/"
                } else {
                    fullDirection.append(part).append("/");
                }
            }
        }

        // Eliminamos la última /
        if (fullDirection.length() > 1 && fullDirection.charAt(fullDirection.length() - 1) == '/') {
            fullDirection.deleteCharAt(fullDirection.length() - 1);
        }
        return fullDirection.toString();
    }

    private boolean isDirectoryNode(DefaultMutableTreeNode node) {
        Object userObject = node.getUserObject();
        if (userObject instanceof String) {
            String nodeName = (String) userObject;
            // Considera "FTP Server" como un directorio válido
            return nodeName.equals("FTP Server") || nodeName.startsWith("📁");
        }
        return false;
    }

    public void reloadTree() {
        loadDirectories("/");
        JLabel label = ftpView.getLabel();
        label.setText("/FTP Server");
    }

    // This method do the function of the button rename
    public void renameFile() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            Utils.showWarningWindow(null, "Por favor, selecciona un archivo o directorio para renombrar.", "Aviso");
            return;
        }

        String oldName = selectedNode.getUserObject().toString().replace("📁 ", "").replace("🗎 ", "");
        String oldPath = getFullPathFromNode(selectedNode);

        // Determinar si es un archivo o un directorio
        boolean isFile = oldName.contains(".");
        String newName = Utils.showInputDialog(null, "Introduce el nuevo nombre: ", "Rename");

        if (newName == null || newName.trim().isEmpty()) {
            Utils.showErrorWindow(null, "El nombre no puede estar vacío.", "Error");
            return;
        }

        // Validar caracteres en el nombre
        if (!newName.matches("[a-zA-Z0-9._-]+")) {
            Utils.showErrorWindow(null, "El nombre contiene caracteres no válidos.", "Error");
            return;
        }

        // Validar si el nombre es igual al anterior
        if (oldName.equals(newName)) {
            Utils.showInfoWindow(null, "El nuevo nombre es igual al nombre actual. No se realizaron cambios.", "Información");
            return;
        }

        // Validar la extensión si es un archivo
        if (isFile) {
            String extension = oldName.substring(oldName.lastIndexOf(".") + 1); // Obtener la extensión del archivo
            if (!newName.contains(".")) {
                newName += "." + extension;
            } else {
                String newExtension = newName.substring(newName.lastIndexOf(".") + 1);
                if (!newExtension.equalsIgnoreCase(extension)) {
                    int response = Utils.showConfirmDialog(null, "Estás cambiando la extensión de " + extension + " a " + newExtension + ". ¿Quieres continuar?", "Aviso");
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
            }
        }

        // Construir la nueva ruta
        String newPath = oldPath.substring(0, oldPath.lastIndexOf("/") + 1) + newName;

        // Intentar renombrar
        try {
            boolean success = clientFTP.rename(oldPath, newPath);

            if (success) {
                Utils.showInfoWindow(null, "Renombrado exitosamente.", "Éxito");
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                selectedNode.setUserObject((isFile ? "🗎 " : "📁 ") + newName);
                model.nodeChanged(selectedNode);
            } else {
                Utils.showErrorWindow(null, "No se pudo renombrar el archivo o directorio.", "Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showErrorWindow(null, "Ocurrió un error inesperado al intentar renombrar.", "Error");
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

    // This method save the statement of the jtree in specific moment, so i can rescure when i want
    private List<String> saveTreeState() {
        List<String> expandedPaths = new ArrayList<>();
        Enumeration<TreePath> expanded = tree.getExpandedDescendants(new TreePath(tree.getModel().getRoot()));

        if (expanded != null) {
            while (expanded.hasMoreElements()) {
                TreePath path = expanded.nextElement();
                expandedPaths.add(path.toString());
            }
        }

        return expandedPaths;
    }

    private TreePath getTreePath(String[] parts) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        List<DefaultMutableTreeNode> pathNodes = new ArrayList<>();
        pathNodes.add(node);

        for (int i = 1; i < parts.length; i++) {
            boolean found = false;
            for (int j = 0; j < node.getChildCount(); j++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(j);
                if (child.toString().equals(parts[i])) {
                    node = child;
                    pathNodes.add(node);
                    found = true;
                    break;
                }
            }
            if (!found) return null; // Si no se encuentra la ruta completa, devuelve null
        }

        return new TreePath(pathNodes.toArray());
    }

    private void restoreTreeState(List<String> expandedPaths) {
        for (String pathString : expandedPaths) {
            String[] parts = pathString.replace("[", "").replace("]", "").split(", ");
            TreePath path = getTreePath(parts);
            if (path != null) {
                tree.expandPath(path);
            }
        }
    }

    public void uploadFile() {
        // Abrir el explorador de archivos para seleccionar un archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo para subir: ");
        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No se seleccionó ningún archivo.");
            return;
        }

        // Obtener el archivo seleccionado
        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile == null || !selectedFile.isFile()) {
            Utils.showErrorWindow(null, "El archivo seleccionado no es válido.", "Error");
            return;
        }

        // Obtener la ruta seleccionada en el árbol (JTree)
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            Utils.showWarningWindow(null, "Por favor, selecciona un directorio en el servidor donde subir el archivo.", "Aviso");
            return;
        }

        if (!isDirectoryNodeCreate(selectedNode)) {
            Utils.showErrorWindow(null, "Por favor, selecciona un directorio válido para subir el archivo.", "Error");
            return;
        }

        String serverPath = getDirNodeCreate(selectedNode);

        List<String> expandedPaths = saveTreeState();

        // Subir el archivo al servidor FTP
        try {
            boolean success = clientFTP.uploadFile(selectedFile.getAbsolutePath(), serverPath + "/" + selectedFile.getName());

            if (success) {
                Utils.showInfoWindow(null, "El archivo se subió correctamente al servidor.", "Éxito");
                // guardar el tree
                reloadTree(); // Actualizar el árbol para reflejar el cambio
                restoreTreeState(expandedPaths); // Restaurar el estado del árbol
            } else {
                Utils.showErrorWindow(null, "No se pudo subir el archivo al servidor. Verifica los permisos.", "Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showErrorWindow(null, "Ocurrió un error al intentar subir el archivo.", "Error");
        }
    }

    public void downloadFile() {
        // Seleccionar el nodo en el árbol
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            Utils.showWarningWindow(null, "Por favor, selecciona un archivo para descargar.", "Aviso");
            return;
        }

        // Verificar que sea un archivo
        if (isDirectoryNodeDelete(selectedNode)) {
            Utils.showErrorWindow(null, "No puedes descargar un directorio. Por favor, selecciona un archivo.", "Error");
            return;
        }

        // Obtener la ruta del archivo en el servidor FTP
        String serverFilePath = getFullPathFromNode(selectedNode);

        // Configurar el explorador de archivos para guardar el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo");
        fileChooser.setSelectedFile(new File(selectedNode.getUserObject().toString().replace("🗎 ", "")));

        int result = fileChooser.showSaveDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No se seleccionó una ubicación para guardar el archivo.");
            return;
        }

        // Obtener la ubicación seleccionada para guardar el archivo
        File saveLocation = fileChooser.getSelectedFile();

        // Descargar el archivo del servidor FTP
        try {
            boolean success = clientFTP.downloadFile(serverFilePath, saveLocation.getAbsolutePath());

            if (success) {
                Utils.showInfoWindow(null, "El archivo se descargó correctamente.", "Éxito");
            } else {
                Utils.showErrorWindow(null, "No se pudo descargar el archivo. Verifica los permisos o la conexión.", "Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showErrorWindow(null, "Ocurrió un error al intentar descargar el archivo.", "Error");
        }
    }
}
