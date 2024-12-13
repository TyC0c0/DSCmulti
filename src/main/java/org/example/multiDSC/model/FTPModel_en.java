package org.example.multiDSC.model;

import java.util.ArrayList;

/*FTPModel - Model for the FTP window.*
@author Jose Manuel Campos Lopez
@version 1.0*/
public class FTPModel_en {
    private ArrayList<String> ftpText_en = new ArrayList<>();

    public FTPModel_en() {
        addValuesToArray_en();
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

}
