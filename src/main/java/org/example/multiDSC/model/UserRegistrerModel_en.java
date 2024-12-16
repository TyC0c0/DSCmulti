package org.example.multiDSC.model;

import java.util.ArrayList;

/*
Class to get all the text to the sing up view
@autor Alvaro Garcia Lopez
@version 1.0
 */

public class UserRegistrerModel_en {
    private ArrayList<String> labelTexts = new ArrayList<>();

    public UserRegistrerModel_en() {
        initializeLabels();
    }

    private void initializeLabels() {
        labelTexts.add("Sing up!");//0
        labelTexts.add("Mail");//1
        labelTexts.add("Name");//2
        labelTexts.add("Last Names");//3
        labelTexts.add("DNI");//4
        labelTexts.add("Nickname");//5
        labelTexts.add("Password");//6
        labelTexts.add("Ok");//7
        labelTexts.add("Cancel");//8
    }

    public ArrayList<String> getLabelTexts() {
        return labelTexts;
    }
}
