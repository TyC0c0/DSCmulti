package org.example.multiDSC.model;

import java.util.ArrayList;
/*FTPModel - Model for the FTP window.*
@author Jose Manuel Campos Lopez
@version 1.0*/
public class FTPModel {
    private ArrayList<String> ftpText_en = new ArrayList<>();
    private ArrayList<String> ftpText_es = new ArrayList<>();

    public FTPModel() {
        addValuesToArray_en();
        addValuesToArray_es();
    }

    public void addValuesToArray_en() {
        ftpText_en.add("Manage Permissions"); //Position 0
        ftpText_en.add("Upload Folder"); //Position 1
        ftpText_en.add("Download Folder"); //Position 2
        ftpText_en.add("Manage Directory"); //Position 3
        ftpText_en.add("Exit"); //Position 4
    }

    public ArrayList<String> getFTPText_en() {
        return ftpText_en;
    }

    public void setFTPText_en(ArrayList<String> ftpText_en) {
        this.ftpText_en = ftpText_en;
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
