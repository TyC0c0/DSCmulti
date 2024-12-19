package org.example.multiDSC.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.conexionThread;
import org.example.multiDSC.controller.listeners.LoginView.LabelListener;
import org.example.multiDSC.controller.listeners.UserRegistrerView.ButtonsListener;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.*;
import org.example.multiDSC.view.*;
import java.sql.SQLException;

@Getter
@Setter
public class MainController {

    private static ConectionBD conexion;
    private static Manager manager;
    private Utils utils;

    private LoginView login;
    private LoginModel loginModel;
    private UserRegistrerView register;
    private UserRegisterModel registerModel;
    private SendMailView sendMail;
    private SendMailModel sendMailModel;
    private PostLoginView postLogin;
    private PostLoginModel postLoginModel;
    private MailView mail;
    private MailModel mailModel;
    private InfoView info;
    private InfoModel infoModel;
    private FTPView ftp;
    private FTPModel ftpModel;
    private ConfUserView confUser;
    private ConfUserModel confUserModel;
    private ConfAdminView confAdmin;
    private ConfAdminModel confAdminModel;

    public MainController() {
        init();
    }

    public void init() {
        hiloConexion();
        manager = new Manager();
        utils = new Utils();
        manager.setTable(utils.switchLanguage("ingles"));
        manager.setConexion(conexion);
        manager.setMainController(this);

        // Inicializar vista
        loginModel= new LoginModel(manager);
        login = new LoginView(manager, loginModel);
        addLoginListeners();
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
        register.getRegisterButton().addActionListener(new ButtonsListener(manager));
        register.getCancelButton().addActionListener(new ButtonsListener(manager));
    }

    private void addLoginListeners() {
        manager.getMainController().getLogin().getBtnOk().addActionListener(new org.example.multiDSC.controller.listeners.LoginView.ButtonsListener(manager));
        login.getBtnCancel().addActionListener(new org.example.multiDSC.controller.listeners.LoginView.ButtonsListener(manager));
        login.getLblDontHaveBeenRegistred().addMouseListener(new LabelListener(login,conexion,manager));
    }
    }







