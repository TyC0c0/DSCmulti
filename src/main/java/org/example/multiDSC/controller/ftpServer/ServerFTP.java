package org.example.multiDSC.controller.ftpServer;

import java.io.File;
import java.io.IOException;

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
            // Validar que el Manager tiene los datos necesarios
            if (manager.getUserNickname() == null || manager.getUserNickname().isEmpty() ||
                    manager.getUserPassword() == null || manager.getUserPassword().isEmpty()) {
                throw new IllegalArgumentException("El Manager no tiene configurado el nickname o la contraseña.");
            }

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

            // Configurar el directorio raíz
            String baseDir = "C:/FTPserver";
            File adminDir = new File(baseDir + "/"+manager.getUserNickname());

            if (manager.getUserRol() == 1) { // Administrador
                user.setHomeDirectory(baseDir); // El administrador accede al directorio base completo
                if (!adminDir.exists()) {
                    adminDir.mkdirs(); // Crear carpeta admin1 si no existe
                    System.out.println("Carpeta admin1 creada automáticamente.");
                }
            } else {
                // Usuario normal: limitar acceso a su carpeta
                String userDirPath = baseDir + "/" + manager.getUserNickname();
                File userDir = new File(userDirPath);
                if (!userDir.exists()) {
                    userDir.mkdirs(); // Crear la carpeta del usuario si no existe
                }
                user.setHomeDirectory(userDirPath); // Establecemos su directorio personal como el "raíz"
            }

            user.setAuthorities(java.util.Collections.singletonList(new WritePermission())); // Permisos de escritura

            userManager.save(user);
            serverFactory.setUserManager(userManager);
            userManager.save(user);
            serverFactory.setUserManager(userManager);

            // Iniciar el servidor FTP
            FtpServer server = serverFactory.createServer();
            server.start();

            // Indicar el rol del usuario en el log
            System.out.println("Servidor FTP iniciado.");
            System.out.println("Usuario configurado como " +
                    (manager.getUserRol() == 1 ? "administrador" : "usuario normal") + ".");

        } catch (IllegalArgumentException e) {
            Utils.showErrorWindow(null, "Error de configuración del usuario: " + e.getMessage(), "Error");
        } catch (Exception e) {
            Utils.showErrorWindow(null, "Ha habido un error al arrancar el Servidor FTP. Disculpe las molestias...", "Error");
            System.err.println("Error al arrancar el servidor FTP: "+e.getMessage());
        }
    }
}
