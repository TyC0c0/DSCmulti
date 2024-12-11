package org.example.multiDSC.model;

import java.util.ArrayList;
/*
Class to put all the text to the sendMailView
@autor Alvaro Garcia Lopez
@version 1.0
 */
public class SendMailViewModel_en {
    ArrayList<String>texts = new ArrayList<String>();
    public SendMailViewModel_en(){
        addTextToArray();
    }

    private void addTextToArray() {
        texts.add("To: ");//0
        texts.add("Topic: ");//1
        texts.add("Message: ");//2
        texts.add("Send");//3
        texts.add("Add File");//4
        texts.add("Cancel");//5
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<String> texts) {
        this.texts = texts;
    }
}
