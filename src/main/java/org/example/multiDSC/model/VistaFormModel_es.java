package org.example.multiDSC.model;

import java.util.ArrayList;
/*
Class to set all the text to the view
@autor Alvaro Garcia Lopez
@version 1.0
 */
public class VistaFormModel_es {
    private ArrayList<String> loginText = new ArrayList<String>();

    public void VistaFormModel_es() {
        addStringToArray();
    }

    private void addStringToArray() {
        loginText.add("Login");//0
        loginText.add("Bienvenido al");//1
        loginText.add("Nombre Usuario");//2
        loginText.add("Contraseña");//3
        loginText.add("Vale");//4
        loginText.add("Cancelar");//5
        loginText.add("Formulario Login");//6
    }

    public ArrayList<String> getLoginText() {
        return loginText;
    }

    public void setLoginText(ArrayList<String> loginText) {
        this.loginText = loginText;
    }
}
