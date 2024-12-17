package org.example.multiDSC.controller.ftpServer;

import java.io.File;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class ServerFTP {

    // Esta clase unicamente inicia el servidor FTP en MI DIRECTORIO (RAMÓN)

    public static void main(String[] args) {
        try {
            // Configurar el servidor FTP
            FtpServerFactory serverFactory = new FtpServerFactory();
            ListenerFactory listenerFactory = new ListenerFactory();

            // Configurar puerto FTP
            listenerFactory.setPort(2121); // Puerto personalizado
            serverFactory.addListener("default", listenerFactory.createListener());

            // Configurar usuario FTP
            PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
            UserManager userManager = userManagerFactory.createUserManager();

            BaseUser user = new BaseUser();
            user.setName("ftpuser"); // Nombre de usuario
            user.setPassword("ftp123"); // Contraseña
            user.setHomeDirectory(new File("C:/FTPserver").getAbsolutePath()); // Directorio de inicio
            user.setAuthorities(java.util.Collections.singletonList(new WritePermission())); // Permisos de escritura
            userManager.save(user);

            serverFactory.setUserManager(userManager);

            // Iniciar el servidor FTP
            FtpServer server = serverFactory.createServer();
            System.out.println("Servidor FTP iniciado en el puerto 2121...");
            server.start();

        } catch (Exception e) {
            System.err.println("Error iniciando el servidor FTP: " + e.getMessage());
        }
    }
}
