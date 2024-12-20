package org.example.multiDSC.controller.ftpServer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.model.controllModels.Manager;

import java.io.*;

/**
 * ClientFTP - The ClientFTP is the user which connect to the ServerFTP.
 * This user will have his own directory in the FTP.
 *
 * @author Ramón Reina González
 * @version 1.6
 */

public class ClientFTP {

    private FTPClient user;
    private Manager manager;

    public ClientFTP(Manager manager) {
        user = new FTPClient();
        this.manager = manager;
    }

    private static final String SERVER = "localhost"; // IP del servidor FTP
    private static final int PORT = 2121; // FTP Port

    private boolean isAdminAccess = false;
    private String baseDirectory = "/";

    public void setAdminAccess(boolean isAdminAccess) {
        this.isAdminAccess = isAdminAccess;
    }

    public boolean isAdminAccess() {
        return isAdminAccess;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    // This method enable or disable admin permissions from the loged user
    public void configureClientPermissions(ClientFTP clientFTP) {
        if (manager.getUserRol() == 1) { // Administrador
            System.out.println("Configurando cliente FTP para administrador: Acceso completo.");
            clientFTP.setAdminAccess(true);
        } else { // Usuario normal
            System.out.println("Configurando cliente FTP para usuario normal: Acceso restringido.");
            clientFTP.setBaseDirectory("/" + manager.getUserNickname()); // Limita el acceso al directorio del usuario
            clientFTP.setAdminAccess(false);
        }
    }

    // También hay que organizar los usuarios ya que los admin tienen permisos a
    // todso los directorios
    // mientras que los usuarios sin permisos de administrador solo tienen acceso a
    // su directorio

    public boolean rename(String oldName, String newName) {
        try {
            boolean success = user.rename(oldName, newName);
            if (success) {
                System.out.println("Archivo renombrado!");
                return true;
            } else {
                System.out.println("Error al renombrar...");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean connectFTP() {
        try {
            System.out.println("Intentando conexión con el Servidor FTP...");
            user.connect(SERVER, PORT);

            System.out.println(manager.getUserNickname());
            System.out.println(manager.getUserPassword());

            if (!user.login(manager.getUserNickname(), manager.getUserPassword())) {
                Utils.showErrorWindow(null, "Ha habido un problema en el Login (Usuario o contraseña incorrectos)", "Error");
                System.out.println("Ha habido un problema en el login (Usuario o contraseña incorrectos)");
                return false;
            } else {
                System.out.println("Login correcto! Acceso permitido...");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Ha habido un error al conectarse al Servidor FTP...");
            e.printStackTrace();
            return false;
        }
    }

    public void disconnectFTP() {
        try {
            user.logout();
            user.disconnect();
            System.out.println("El cliente se ha desconectado del servidor correctamente.");
        } catch (IOException e) {
            System.out.println("Ha habido un problema al desconectarse...");
            e.printStackTrace();
        }
    }

    public boolean deleteFile(String direction) {
        try {
            File file = new File(direction);
            if (!file.exists()) {
                System.err.printf("El archivo no existe en el servidor: ", direction);
                return false;
            }

            boolean success = file.delete();
            if (success) {
                System.out.printf("Archivo eliminado correctamente: %s%n", direction);
            } else {
                System.err.printf("No se pudo eliminar el archivo: %s%n", direction);
            }
            return success;
        } catch (Exception e) {
            System.err.printf("Error al eliminar el archivo: %s. Mensaje: %s%n", direction, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean createDirectory(String direction) {
        try {
            return user.makeDirectory(direction);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDirectoryRecursive(String direction) {
        try {
            File directory = new File(direction);
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.printf("El directorio no existe o no es válido: %s%n", direction);
                return false;
            }

            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        System.out.println("Procesando subdirectorio: " + file.getAbsolutePath());
                        if (!deleteDirectoryRecursive(file.getAbsolutePath())) {
                            System.err.printf("No se pudo eliminar el subdirectorio: %s%n", file.getAbsolutePath());
                            return false;
                        }
                    } else {
                        System.out.println("Intentando eliminar archivo: " + file.getAbsolutePath());
                        if (!deleteFile(file.getAbsolutePath())) {
                            System.err.printf("No se pudo eliminar el archivo: %s%n", file.getAbsolutePath());
                            return false;
                        }
                    }
                }
            }

            System.out.println("Intentando eliminar el directorio vacío: " + direction);
            boolean success = directory.delete();
            if (!success) {
                System.err.printf("No se pudo eliminar el directorio vacío: %s%n", direction);
            }
            return success;
        } catch (Exception e) {
            System.err.printf("Error al intentar eliminar el directorio '%s': %s%n", direction, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public FTPFile[] showDirectoriesUser(String directoryName) {
        try {
            return user.listFiles(directoryName);
        } catch (IOException e) {
            System.out.println("Ha habido un error listando los directorios del usuario... "+directoryName);
            e.printStackTrace();
            return new FTPFile[0];
        }
    }

    public boolean uploadFile(String localFilePath, String serverFilePath) {
        try (InputStream input = new FileInputStream(localFilePath)) {
            return user.storeFile(serverFilePath, input);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean downloadFile(String serverFilePath, String localFilePath) {
        try (OutputStream output = new FileOutputStream(localFilePath)) {
            return user.retrieveFile(serverFilePath, output);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        ClientFTP cliente = new ClientFTP(manager);

        // Existe un propiedad que es exactamente igual que esta linea pero (ADMIN_USER,
        // ADMIN_PASS)
        if (cliente.connectFTP()) {
            //cliente.listFilesAndDirectories("/");
            cliente.disconnectFTP();
        }
    }
}