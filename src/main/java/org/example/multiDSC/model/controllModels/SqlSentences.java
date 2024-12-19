package org.example.multiDSC.model.controllModels;

import java.util.ArrayList;

public class SqlSentences {
   private ArrayList<String>sencences = new ArrayList<>();
    public SqlSentences(){
        addTextToArrayList();
    }

    private void addTextToArrayList() {
        sencences.add("SELECT \"Nickname\", \"Contraseña\" FROM \"USUARIO\"");//0
        sencences.add("SELECT \"Correo\" FROM public.\"USUARIO\"");//1
        sencences.add("SELECT u.\"id\", u.\"Correo\", u.\"Nombre\", r.\"Nombre\" AS \"Rol_Nombre\"\n" +
                "FROM \"USUARIO\" u\n" +
                "JOIN \"ROLES\" r ON u.id_rol = r.id");//2


    }


    public ArrayList<String> getSencences() {
        return sencences;
    }

    public void setSencences(ArrayList<String> sencences) {
        this.sencences = sencences;
    }
}
