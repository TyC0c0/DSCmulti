package org.example.multiDSC.model.controllModels;

import org.example.multiDSC.controller.databaseConection.ConectionBD;

public class Manager {

    private String table;
    private ConectionBD conexion;

    public ConectionBD getConexion() {
        return conexion;
    }

    public void setConexion(ConectionBD conexion) {
        this.conexion = conexion;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

}
