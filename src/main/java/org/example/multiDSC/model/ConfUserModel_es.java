package org.example.multiDSC.model;

import java.util.ArrayList;

public class ConfUserModel_es {
    private ArrayList<String> confUserText_es = new ArrayList<>();

    public ConfUserModel_es() {
        addValuesToArray_es();
    }

    public void addValuesToArray_es() {
        confUserText_es.add("Diseño Interfaz"); //Position 0
        confUserText_es.add("Eliminar"); //Position 1
        confUserText_es.add("Modificar"); //Position 2
        confUserText_es.add("Salir"); //Position 3
        confUserText_es.add("Aplicar"); //Position 4
        confUserText_es.add("Cancelar"); //Position 5
        confUserText_es.add("Apodo"); //Position 6
        confUserText_es.add("Nombre"); //Position 7
        confUserText_es.add("Apellido"); //Position 8
        confUserText_es.add("Correo electrónico"); //Position 9
        confUserText_es.add("Contraseña"); //Position 10
        confUserText_es.add("DNI"); //Position 11
        confUserText_es.add("Descripción Rol"); //Position 12
    }

    public ArrayList<String> getConfUserText_es() {
        return confUserText_es;
    }

    public void setConfUserText_es(ArrayList<String> confUserText_es) {
        this.confUserText_es = confUserText_es;
    }

}
