package org.example.multiDSC.controller.ftpServer;

import java.io.File;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.model.controllModels.Manager;

/**
 * ServerFTP - ServerFTP ...
 *
 * @author Ramón Reina González
 * @version 1.0
 */

public class ServerFTP {

    private Manager manager;

    public ServerFTP(Manager manager) {
        this.manager = manager;
    }
    // Esta clase inicia el servidor FTP que espera conexiones a través del puerto 2121, aunque está en local

    public void initServer() {
        try {
            // Configurar el servidor FTP
            FtpServerFactory serverFactory = new FtpServerFactory();
            ListenerFactory listenerFactory = new ListenerFactory();

            // Configurar puerto FTP
            listenerFactory.setPort(2121);
            serverFactory.addListener("default", listenerFactory.createListener());

            // Configurar usuario FTP
            PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
            UserManager userManager = userManagerFactory.createUserManager();

            BaseUser user = new BaseUser();
            user.setName(manager.getUserNickname());
            user.setPassword(manager.getUserPassword());

            String homeDirPath = "C:/FTPserver/" + manager.getUserNickname(); // Directorio personal del usuario
            File homeDir = new File(homeDirPath);

            user.setHomeDirectory(homeDir.getAbsolutePath()); // Directorio de inicio del usuario
            user.setAuthorities(java.util.Collections.singletonList(new WritePermission())); // Permisos de escritura

            userManager.save(user);
            serverFactory.setUserManager(userManager);

            // Crear el directorio de inicio si no existe
            if (!homeDir.exists()) {
                homeDir.mkdirs();
                System.out.println("Se creó el Directorio FTP para el usuario " + manager.getUserNickname() + ": " + homeDir.getAbsolutePath());
            }

            // Iniciar el servidor FTP
            FtpServer server = serverFactory.createServer();
            System.out.println("El servidor FTP está iniciado en el puerto " + listenerFactory.getPort());
            server.start();

        } catch (Exception e) {
            Utils.showErrorWindow(null, "Ha habido un error al arrancar el Servidor FTP. Disculpe las molestias...", "Error");
            System.err.println("Error al arrancar el servidor FTP: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        //manager.setUserNickname("");
        //manager.setUserPassword("");

        ServerFTP serverFTP = new ServerFTP(manager);
        serverFTP.initServer();
    }
}
