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
    private boolean isAdminAccess = false;
    private String baseDirectory = "/";

    public ClientFTP(Manager manager) {
        user = new FTPClient();
        this.manager = manager;
    }

    private static final String SERVER = "localhost"; // IP del servidor FTP
    private static final int PORT = 2121; // FTP Port


    public void setAdminAccess(boolean isAdminAccess) {
        this.isAdminAccess = isAdminAccess;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public boolean connectFTP() {
        try {
            System.out.println("Cliente intentando conexión con el Servidor FTP...");

            if (manager.getUserNickname() == null || manager.getUserPassword() == null) {
                throw new IllegalArgumentException("El Manager no tiene configurados el usuario o la contraseña.");
            }

            user.connect(SERVER, PORT);

            if (!user.login(manager.getUserNickname(), manager.getUserPassword())) {
                Utils.showErrorWindow(null, "Login fallido: Usuario o contraseña incorrectos.", "Error");
                return false;
            }

            System.out.println("Login exitoso. Usuario conectado: " + manager.getUserNickname());
            configureClientPermissions(this); // Configurar permisos tras login
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println("Error en los datos del usuario: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("Error al conectarse al Servidor FTP: " + e.getMessage());
            return false;
        }
    }

    // This method enable or disable admin permissions from the loged user
    public void configureClientPermissions(ClientFTP clientFTP) {
        if (manager.getUserRol() == 1) { // Administrador
            System.out.println("Configurando cliente FTP para administrador: Acceso completo.");
            clientFTP.setAdminAccess(true);
        } else { // Usuario normal
            String userDirectory = "/" + manager.getUserNickname();
            System.out.println("Configurando cliente FTP para usuario normal: Acceso restringido.");
            clientFTP.setBaseDirectory(userDirectory); // Limita el acceso al directorio del usuario
            clientFTP.setAdminAccess(false);
        }
    }

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

    public void disconnectFTP() {
        try {
            if (user.isConnected()) {
                user.logout();
                user.disconnect();
            }
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
            FTPFile[] files = user.listFiles(directoryName); // Listar archivos en el directorio
            if (files == null || files.length == 0) {
                System.out.println("No se encontraron archivos o directorios en: " + directoryName);
            } else {
                System.out.println("Archivos/directorios encontrados en " + directoryName + ":");
                for (FTPFile file : files) {
                    System.out.println(" - " + file.getName());
                }
            }
            return files;
        } catch (IOException e) {
            System.err.println("Error al listar los directorios en: " + directoryName);
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
}