package org.example.multiDSC.model;

import java.util.ArrayList;
/*
class to get all the text and add it to the login
@autor Alvaro Garcia Lopez
@version 1.0
*/
public class VistaFormModel {
    private ArrayList<String> loginText = new ArrayList<String>();
    public void VistaForm(){
        addStringToArray();
    }

    private void addStringToArray() {
        loginText.add("Login");//0
        loginText.add("Welcome to the");//1
        loginText.add("Username");//2
        loginText.add("Password");//3
        loginText.add("Ok");//4
        loginText.add("Cancel");//5
        loginText.add("Login form");//6
    }

    public ArrayList<String> getLoginText() {
        return loginText;
    }

    public void setLoginText(ArrayList<String> loginText) {
        this.loginText = loginText;
    }
}
