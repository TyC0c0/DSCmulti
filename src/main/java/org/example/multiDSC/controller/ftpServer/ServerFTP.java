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

    // Esta clase inicia el servidor FTP que espera conexiones a través del puerto 2121, aunque está en local

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
            user.setName("user"); // Nombre de usuario
            user.setPassword("1234"); // Contraseña
            user.setHomeDirectory(new File("C:/FTPserver").getAbsolutePath()); // Directorio de inicio
            user.setAuthorities(java.util.Collections.singletonList(new WritePermission())); // Permisos de escritura
            userManager.save(user);

            serverFactory.setUserManager(userManager);

            // Crear un directorio si no existe
            File homeDir = new File("C:/FTPserver");
            if (!homeDir.exists()) {
                homeDir.mkdirs();
                System.out.println("Se te ha creado un Directorio FTP: " + homeDir.getAbsolutePath());
            }

            // Iniciar el servidor FTP
            FtpServer server = serverFactory.createServer();
            System.out.println("El servidor FTP está iniciado en el puerto "+listenerFactory.getPort());
            server.start();

        } catch (Exception e) {
            System.err.println("Ha habido un arrancando el Servidor FTP: " + e.getMessage());
        }
    }
}
