package org.example.multiDSC.model.viewModels;

import org.example.multiDSC.model.controllModels.Manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class EmailModel {

    private Manager manager;
    private String query;
    private ArrayList<String> texts= new ArrayList<>();

    public EmailModel(Manager manager){
        this.manager=manager;
        init();
        fillTexts();
    }

    public void init(){
        query= "SELECT "+ manager.getTable() +" FROM \"NOMBRES\" WHERE \"Entrada\" like \"EmailModel\"";

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
}
