package org.example.multiDSC.model.viewModels;

import org.example.multiDSC.model.controllModels.Manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ConfUserModel {
    private Manager manager;
    private String query;
    private ArrayList<String> text = new ArrayList<>();

    public ConfUserModel(Manager manager){
        this.manager=manager;
        init();
        fillTexts();
    }

    public void init(){
        query= "SELECT "+ manager.getTable() +" FROM \"NOMBRES\" WHERE \"Entrada\" like \"ConfUserModel\"";

    }
    public void fillTexts(){

        Map<Integer, Object> results = null;
        try {
            results = manager.getConexion().lecturaSQL(query);

            // Imprimir resultados
            for (Map.Entry<Integer, Object> entry : results.entrySet()) {
                System.out.println("Row: " + entry.getKey() + " Value: " + entry.getValue());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }
}
