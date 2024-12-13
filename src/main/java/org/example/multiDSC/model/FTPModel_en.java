package org.example.multiDSC.model;

import java.util.ArrayList;

/*FTPModel - Model for the FTP window.*
@author Jose Manuel Campos Lopez, Alvaro Garcia Lopez
@version 1.1*/
public class FTPModel_en {
    private ArrayList<String> ftpText_en = new ArrayList<>();

    public FTPModel_en() {
        addValuesToArray_en();
    }

    public void addValuesToArray_en() {
        ftpText_en.add("Create Directory"); //Position 0
        ftpText_en.add("Delete Directory"); //Position 1
        ftpText_en.add("Modify Directory"); //Position 2
        ftpText_en.add("Delete File"); //Position 3
        ftpText_en.add("Exit"); //Position 4
        ftpText_en.add("Reload");//Position 5
        ftpText_en.add("Rename");//Position 6
        ftpText_en.add("Download File");//Position 7
    }

    public ArrayList<String> getFTPText_en() {
        return ftpText_en;
    }

    public void setFTPText_en(ArrayList<String> ftpText_en) {
        this.ftpText_en = ftpText_en;
    }

}
