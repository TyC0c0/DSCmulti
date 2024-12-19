package org.example.multiDSC.controller.ftpServer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ClientFTP - ClientFTP ...
 *
 * @author Ramón Reina González
 * @version 1.0
 */

public class ClientFTP {

    private static final String SERVER = "localhost"; // IP del servidor FTP
    private static final int PORT = 2121; // Puerto FTP
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";

    private FTPClient user;

    public ClientFTP() {

        user = new FTPClient();
    }

    public FTPClient getFtpClient() {
        return user;
    }

    // Esta clase se conecta al servidor FTP de manera local y realiza la acción de
    // listar archivos

    // Falta implementar los metodos de crear directorios, eliminar, descargar...

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

            if (!user.login(ADMIN_USER, ADMIN_PASS)) {
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
            System.out.println("Usted se ha desconectado del servido correctamente.");
        } catch (IOException e) {
            System.out.println("Ha habido un problema al desconectarse...");
            e.printStackTrace();
        }
    }

    public boolean deleteFile(String path) {
        try {
            boolean success = user.deleteFile(path);
            if (success) {
                System.out.printf("Archivo eliminado correctamente: %s%n", path);
            } else {
                System.err.printf("No se pudo eliminar el archivo: %s%n", path);
            }
            return success;
        } catch (IOException e) {
            System.err.printf("Error al eliminar el archivo: %s. Mensaje: %s%n", path, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeDirectory(String path) {
        try {
            boolean success = user.removeDirectory(path);
            if (success) {
                System.out.printf("Directorio eliminado correctamente: %s%n", path);
            } else {
                System.err.printf("No se pudo eliminar el directorio: %s%n", path);
            }
            return success;
        } catch (IOException e) {
            System.err.printf("Error al eliminar el directorio: %s. Mensaje: %s%n", path, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public FTPFile[] listFilesAndDirectories(String remotePath) {
        System.out.println("Listando archivos y directorios en: " + remotePath);

        FTPFile[] files;
        try {
            // Llamada al cliente FTP para listar archivos en la ruta remota
            files = user.listFiles(remotePath); // 'user' es el objeto FTPClient que maneja la conexión

            if (files == null || files.length == 0) {
                System.out.println("No existen archivos o directorios en: " + remotePath);
                return new FTPFile[0]; // Devolver un array vacío si no hay resultados
            }

            // Iterar sobre los archivos y directorios devueltos
            for (FTPFile file : files) {
                if (file.isDirectory()) {
                    System.out.println("[Directorio] " + file.getName());
                } else if (file.isFile()) {
                    System.out.println(" -Archivo: " + file.getName());
                } else {
                    System.out.println("[Otro tipo] " + file.getName());
                }
            }

            return files; // Devolver el array de FTPFile con los resultados
        } catch (IOException e) {
            // Manejo de errores al listar archivos y directorios
            System.err.println("Ha habido un problema al listar los directorios...");
            e.printStackTrace();
            return new FTPFile[0]; // Devolver un array vacío en caso de error
        }
    }

    public void createDirectory(String dir) {
        System.out.println("Creando un nuevo Directorio.");
        try {
            if (user.makeDirectory(dir)) {
                System.out.println("Se ha creado un directorio: " + dir);
            } else {
                System.out.println("Ha habido un problema con el directorio...");
            }
        } catch (IOException e) {
            System.out.println("Ha habido un problema a la hora de crear un directorio.");
            e.printStackTrace();
        }
    }

    public void dowloadFile(String localFile, String remoteFile) {
        System.out.println("Descargando archivo.");
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            if (user.retrieveFile(remoteFile, fos)) {
                System.out.println("Archivo descargado: " + localFile);
            } else {
                System.out.println("Error al descargar " + remoteFile);
            }
        } catch (IOException e) {
            System.out.println("Ha habido un problema descargando el archivo...");
            e.printStackTrace();
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


    public static void main(String[] args) {
        ClientFTP cliente = new ClientFTP();

        // Existe un propiedad que es exactamente igual que esta linea pero (ADMIN_USER,
        // ADMIN_PASS)
        if (cliente.connectFTP()) {
            cliente.listFilesAndDirectories("/");
            //cliente.createDirectory("/nuevoDirectorio");
            //cliente.dowloadFile("/localFile", "/remoteFile");
            //cliente.deleteFile("/archivo_a_eliminar.txt");
            cliente.disconnectFTP();
        }
    }
}