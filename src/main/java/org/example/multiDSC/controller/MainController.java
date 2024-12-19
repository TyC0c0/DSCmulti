package org.example.multiDSC.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.ConexionThread;
import org.example.multiDSC.controller.listeners.ConfAdminView.ConfAdminButtonListener;
import org.example.multiDSC.controller.listeners.ConfAdminView.ConfAdminEditTextListener;
import org.example.multiDSC.controller.listeners.LoginView.LabelListener;
import org.example.multiDSC.controller.listeners.PostLogin.PostLoginButtonsListener;
import org.example.multiDSC.controller.listeners.LoginView.LoginViewButtonsListener;
import org.example.multiDSC.controller.listeners.UserRegistrerView.ButtonsListener;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.*;
import org.example.multiDSC.view.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

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
    private EditWindow editWindow;

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
        loginModel= new LoginModel(manager);
        login = new LoginView(manager, loginModel);
        addLoginListeners();
        login.setVisible(true);


//        confAdminModel = new ConfAdminModel(manager);
//        confAdmin = new ConfAdminView();
//        confAdmin.addButtonNextToDelete("Apply");
//        ConfAdminAddActionListeners();
//        confAdmin.setVisible(true);
    }

    public void ConfAdminAddActionListeners() {
        for (int i = 0; i < manager.getMainController().getConfAdmin().getModifyButtons().size(); i++){
            manager.getMainController().getConfAdmin().getModifyButtons().get(i).addActionListener(new ConfAdminButtonListener(manager));
            manager.getMainController().getConfAdmin().getDeleteButtons().get(i).addActionListener(new ConfAdminButtonListener(manager));
        }
    }
    public void EditWindowAddActionListener(Map<String, String> userData, int userId) {
        manager.getMainController().getEditWindow().getApplyButton().addActionListener(new ConfAdminEditTextListener(manager, userData, userId));
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

    public void addLoginListeners() {
        manager.getMainController().getLogin().getBtnOk().addActionListener(new LoginViewButtonsListener(manager));
        manager.getMainController().getLogin().getBtnCancel().addActionListener(new LoginViewButtonsListener(manager));
        manager.getMainController().getLogin().getLblDontHaveBeenRegistred().addMouseListener(new LabelListener(manager.getMainController().getLogin(), conexion, manager));
    }

    public void addPostLoginListeners(){
        for (JButton button : postLogin.getButtons()){
            button.addActionListener(new PostLoginButtonsListener(manager));
        }
    }

}