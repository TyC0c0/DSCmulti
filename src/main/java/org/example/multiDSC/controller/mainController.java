package org.example.multiDSC.controller;

import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.conexionThread;
import org.example.multiDSC.view.LoginView;

public class mainController {

    private LoginView login;
    ConectionBD conexion;


    public mainController(){
        init();
    }

    public void init(){
        login= new LoginView();
        hiloConexion();
    }

    public void hiloConexion(){
        conexion = new ConectionBD();
        conexionThread hconect = new conexionThread();
        hconect.start();
    }


}

