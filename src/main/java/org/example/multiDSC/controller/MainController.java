package org.example.multiDSC.controller;

import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.conexionThread;
import org.example.multiDSC.controller.listeners.UserRegistrerView.ButtonListener;
import org.example.multiDSC.controller.listeners.UserRegistrerView.TextfieldListener;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.LoginView;
import org.example.multiDSC.view.UserRegistrerView;

import java.sql.SQLException;

public class MainController {

    private static ConectionBD conexion;
    private LoginView login;
    private Utils utils;
    private static Manager manager;
    private UserRegistrerView register;


    public MainController(){
        init();
    }

    public void init(){
        hiloConexion();
        manager= new Manager();
        utils= new Utils();
        manager.setTable(utils.switchLanguage("espanol"));
        manager.setConexion(conexion);
        //login= new LoginView();
        //EmailModel model= new EmailModel(manager);
        register= new UserRegistrerView();
        addUserRegisterListeners();

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

    public void addUserRegisterListeners(){
        register.getRegisterButton().addActionListener(new ButtonListener(this));
        register.getCancelButton().addActionListener(new ButtonListener(this));
        register.getNameField().addActionListener(new TextfieldListener(this));
        register.getLastNameField().addActionListener(new TextfieldListener(this));
        register.getPasswordField().addActionListener(new TextfieldListener(this));
        register.getDniField().addActionListener(new TextfieldListener(this));
        register.getNicknameField().addActionListener(new TextfieldListener(this));
        register.getEmailField().addActionListener(new TextfieldListener(this));
    }



    public UserRegistrerView getRegister() {
        return register;
    }

    public void setRegister(UserRegistrerView register) {
        this.register = register;
    }

    public static Manager getManager() {
        return manager;
    }

    public static void setManager(Manager manager) {
        MainController.manager = manager;
    }
}


