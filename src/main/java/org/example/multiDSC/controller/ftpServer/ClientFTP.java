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

    // Esta clase se conecta al servidor FTP de manera local y realiza la acción de
    // listar archivos

    // Falta implementar los metodos de crear directorios, eliminar, descargar...

    // También hay que organizar los usuarios ya que los admin tienen permisos a
    // todso los directorios
    // mientras que los usuarios sin permisos de administrador solo tienen acceso a
    // su directorio

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

    public void listFilesAndDirectories(String remotePath) {
        System.out.println("Listando archivos y directorios en: " + remotePath);

        FTPFile[] files;
        try {
            files = user.listFiles(remotePath);
            if (files != null && files.length > 0) {
                for (FTPFile file : files) {
                    if (file.isDirectory()) {
                        System.out.println("[Directorio] " + file.getName());
                    } else if (file.isFile()) {
                        System.out.println(" -Archivo: " + file.getName());
                    }
                }
            } else {
                System.out.println("No existen archivos o directorios en: " + remotePath);
            }
        } catch (IOException e) {
            System.out.println("Ha habido un problema al listar los directorios...");
            e.printStackTrace();
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

    public void deleteFile(String file) {
        System.out.println("Eliminando el archivo.");
        try {
            if (user.deleteFile(file)) {
                System.out.println("Archivo " + file + " eliminado!");
            } else {
                System.out.println("Ha habido un problema eliminando el archivo " + file);
            }
        } catch (IOException e) {
            System.out.println("Ha habido un problema eliminando el archivo...");
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