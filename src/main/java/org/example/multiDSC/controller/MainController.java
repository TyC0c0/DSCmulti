package org.example.multiDSC.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.conexionThread;
import org.example.multiDSC.controller.listeners.LoginView.ButtonListenerLogin;
import org.example.multiDSC.controller.listeners.LoginView.LabelListenerLogin;
import org.example.multiDSC.controller.listeners.UserRegistrerView.ButtonListener;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.*;

import java.sql.SQLException;

@Getter
@Setter
public class MainController {

    private static ConectionBD conexion;
    private LoginView login;
    private Utils utils;
    private static Manager manager;
    private UserRegistrerView register;
    private PostLoginView postLoginView;
    MailView m;

    public MainController(){
        init();
    }

    public void init() {
        hiloConexion();
        manager= new Manager();
        utils= new Utils();
        manager.setTable(utils.switchLanguage("ingles"));
        manager.setConexion(conexion);
        manager.setMainController(this);

        // Inicializar vistas
        postLoginView = new PostLoginView();
        login = new LoginView();
        register = new UserRegistrerView(conexion); // Instanciar UserRegistrerView
        addLoginListeners();
        addUserRegisterListeners(); // Añadir listeners después de inicializar register
        login.setVisible(true);
    }

    public void hiloConexion() {
        conexion = new ConectionBD();

        try {
            conexion.connect(); // Garantiza que la conexión se establezca antes de crear el hilo
            System.out.println("Conexión inicial establecida correctamente ");

        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión inicial: " + e.getMessage());
            throw new RuntimeException("No se pudo establecer la conexión a la base de datos.");
        }

        // Inicia el hilo que mantendrá la conexión viva
        conexionThread hconect = new conexionThread(conexion);
        hconect.start();
    }

    public void addUserRegisterListeners() {
        register.getRegisterButton().addActionListener(new ButtonListener(manager));
        register.getCancelButton().addActionListener(new ButtonListener(manager));
    }

    private void addLoginListeners() {
        manager.getMainController().getLogin().getBtnOk().addActionListener(new ButtonListenerLogin(manager));
        login.getBtnCancel().addActionListener(new ButtonListenerLogin(manager));
        login.getLblDontHaveBeenRegistred().addMouseListener(new LabelListenerLogin(login,conexion,manager));
    }
    }







