package org.example.multiDSC.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.ConexionThread;
import org.example.multiDSC.controller.listeners.ConfAdminView.ConfAdminButtonListener;
import org.example.multiDSC.controller.listeners.ConfAdminView.ConfAdminEditTextListener;
import org.example.multiDSC.controller.listeners.LoginView.LabelListener;
import org.example.multiDSC.controller.listeners.LoginView.LoginViewButtonsListener;
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
    private ConexionThread hconect;

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
        manager.setConexionThread(hconect);

        // Inicializar vista
//        loginModel= new LoginModel(manager);
//        login = new LoginView(manager, loginModel);
//        addLoginListeners();
//        login.setVisible(true);

         //ALVARO
//        LoginModel model= new LoginModel(manager);
//        login = new LoginView(manager, model);
//        addLoginListeners();
//        registerModel = new UserRegisterModel(manager);
//        register = new UserRegistrerView(manager, registerModel);
//        addUserRegisterListeners();
//        getLogin().setVisible(true);


//        confAdminModel = new ConfAdminModel(manager);
//        confAdmin = new ConfAdminView();
//        confAdmin.addButtonNextToDelete("Apply");
//        ConfAdminAddActionListeners();
//        confAdmin.setVisible(true);
    }

    private void ConfAdminAddActionListeners() {
       manager.getMainController().getConfAdmin().getEditarButton().addActionListener(new ConfAdminButtonListener(manager));
        manager.getMainController().getConfAdmin().getApplyButton().addActionListener(new ConfAdminButtonListener(manager));
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
        hconect = new ConexionThread(conexion);
        hconect.start();
    }

    public void addUserRegisterListeners() {
        register.getRegisterButton().addActionListener(new ButtonsListener(manager));
        register.getCancelButton().addActionListener(new ButtonsListener(manager));
    }

    private void addLoginListeners() {
        manager.getMainController().getLogin().getBtnOk().addActionListener(new LoginViewButtonsListener(manager));
        manager.getMainController().getLogin().getBtnCancel().addActionListener(new LoginViewButtonsListener(manager));
        manager.getMainController().getLogin().getLblDontHaveBeenRegistred().addMouseListener(new LabelListener(manager.getMainController().getLogin(), conexion, manager));
    }

}