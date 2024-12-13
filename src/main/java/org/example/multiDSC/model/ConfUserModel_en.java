package org.example.multiDSC.model;

import java.util.ArrayList;

public class ConfUserModel_en {
    private ArrayList<String> confUserText_en = new ArrayList<>();

    public ConfUserModel_en() {
        addValuesToArray_en();
    }

    public void addValuesToArray_en() {
        confUserText_en.add("Interface Design"); //Position 0
        confUserText_en.add("Eliminate"); //Position 1
        confUserText_en.add("Modify"); //Position 2
        confUserText_en.add("Exit"); //Position 3
        confUserText_en.add("Apply"); //Position 4
        confUserText_en.add("Cancel"); //Position 5
        confUserText_en.add("Nickname"); //Position 6
        confUserText_en.add("Name"); //Position 7
        confUserText_en.add("Last Name"); //Position 8
        confUserText_en.add("Email"); //Position 9
        confUserText_en.add("Password"); //Position 10
        confUserText_en.add("DNI"); //Position 11
        confUserText_en.add("Rol Description"); //Position 12
    }

    public ArrayList<String> getConfUserText_en() {
        return confUserText_en;
    }

    public void setConfUserText_en(ArrayList<String> confUserText_en) {
        this.confUserText_en = confUserText_en;
    }

}
