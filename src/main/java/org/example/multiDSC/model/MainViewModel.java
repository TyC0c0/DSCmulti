package org.example.multiDSC.model;

import java.util.ArrayList;

/*
    this class gets the text from database to insert in mail texts
    @autor Ramón Reina González
    @version 1.0
*/

public class MainViewModel {
    private ArrayList<String> mailText_en = new ArrayList<>();
    private ArrayList<String> mailText_es = new ArrayList<>();

    public void MailView() {

        // Aquí elegiriamos una u otra en funcion del idioma seleccionado en el login
        addValuesToArray_en();
        addValuesToArray_es();
    }

    public void addValuesToArray_en() {
        mailText_en.add("Create New"); //Position 0
        mailText_en.add("Inbox"); //Position 1 (Bandeja)
        mailText_en.add("Not readed"); //Position 2
        mailText_en.add("Sended"); //Position 3
        mailText_en.add("Draft"); //Position 4 (Borrador)
        mailText_en.add("Spam"); //Position 5
        mailText_en.add("Trash bin"); //Position 6
        mailText_en.add("Refresh"); //Position 7
        mailText_en.add("Back"); //Position 8
    }

    public ArrayList<String> getMailText_en() {
        return mailText_en;
    }

    public void setMailText_en(ArrayList<String> mailText_en) {
        this.mailText_en = mailText_en;
    }

    public void addValuesToArray_es() {
        mailText_es.add("Crear nuevo");
        mailText_es.add("Bandeja");
        mailText_es.add("No leído");
        mailText_es.add("Enviado");
        mailText_es.add("Borrador");
        mailText_es.add("Spam");
        mailText_es.add("Papelera");
        mailText_es.add("Actualizar");
        mailText_es.add("Volver");
    }

    public ArrayList<String> getMailText_es() {
        return mailText_es;
    }

    public void setMailText_es(ArrayList<String> mailText_es) {
        this.mailText_es = mailText_es;
    }
}