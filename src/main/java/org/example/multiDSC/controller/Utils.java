package org.example.multiDSC.controller;

import javax.swing.*;
import java.awt.*;

public class Utils {



    public String switchLanguage(String language) {
        switch (language) {

            case "espanol":
                return "\"Nom_es\"";

            case "english":
                return "Nom_en";
        }
        return "";
    }

    public static void showErrorWindow(Component parentComponent, String errorMessage, String title) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, title, JOptionPane.ERROR_MESSAGE);
    }
}
