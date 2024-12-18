package org.example.multiDSC.model;

import java.util.ArrayList;

public class SqlSentences {
   private ArrayList<String>sencences = new ArrayList<>();
    public SqlSentences(){
        addTextToArrayList();
    }

    private void addTextToArrayList() {
        sencences.add("SELECT \"Nickname\", \"Contraseña\" FROM \"USUARIO\"");//0
        sencences.add("SELECT \"Correo\" FROM public.\"USUARIO\"");//1
    }

    public ArrayList<String> getSencences() {
        return sencences;
    }

    public void setSencences(ArrayList<String> sencences) {
        this.sencences = sencences;
    }
}
