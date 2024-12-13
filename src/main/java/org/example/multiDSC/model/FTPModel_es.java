package org.example.multiDSC.model;

import java.util.ArrayList;

public class FTPModel_es {
    private ArrayList<String> ftpText_es = new ArrayList<>();

    public FTPModel_es() {
        addValuesToArray_es();
    }

    public void addValuesToArray_es() {
        ftpText_es.add("Gestionar Permisos"); //Position 0
        ftpText_es.add("Subir Archivo"); //Position 1
        ftpText_es.add("Descargar Archivo"); //Position 2
        ftpText_es.add("Gestionar Directorio"); //Position 3
        ftpText_es.add("Salir"); //Position 4
    }

    public ArrayList<String> getFTPText_es() {
        return ftpText_es;
    }

    public void setFTPText_es(ArrayList<String> ftpText_es) {
        this.ftpText_es = ftpText_es;
    }
}
