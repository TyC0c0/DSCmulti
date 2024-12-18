package org.example.multiDSC.model.controllModels;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.MainController;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.controller.databaseConection.ConexionThread;

@Getter
@Setter
public class Manager {

    private String table;
    private ConectionBD conexion;
    private MainController mainController;
    private String userEmail;
    private String userPassword;
    private ConexionThread conexionThread;

}
