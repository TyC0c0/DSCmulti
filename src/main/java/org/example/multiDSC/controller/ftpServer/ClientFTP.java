package org.example.multiDSC.controller.ftpServer;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class ClientFTP {

    // Esta es la clase cliente que se conecta al servidor FTP

    private static final String SERVER = "192.168.1.98"; // IP del servidor FTP
    private static final int PORT = 21; // Puerto FTP
    private static final String USER = "ftpuser";
    private static final String PASS = "ftp123";

    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();

        try {
            System.out.println("Conectando al servidor FTP con el puerto "+PORT);
            ftpClient.connect(SERVER, PORT);
            ftpClient.enterLocalPassiveMode();

            if (ftpClient.login(USER, PASS)) {
                System.out.println("Conexión exitosa. Listando archivos...");
                String[] files = ftpClient.listNames("/");
                if (files != null) {
                    for (String file : files) {
                        System.out.println("Archivo encontrado: " + file);
                    }
                } else {
                    System.out.println("No se encontraron archivos.");
                }
                ftpClient.logout();
            } else {
                System.out.println("Error: No se pudo autenticar.");
            }
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
                System.out.println("Desconectado del servidor FTP.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
