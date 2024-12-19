package org.example.multiDSC.model.viewModels;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.model.controllModels.Manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
public class MailModel {

    private Manager manager;
    private String query;
    private ArrayList<String> text = new ArrayList<>();

    public MailModel(Manager manager){
        this.manager=manager;
        init();
        fillTexts();
    }

    public void init(){
        query= "SELECT "+ manager.getTable() +" FROM \"NOMBRES\" WHERE \"Entrada\" like 'EmailModel'";

    }
    public void fillTexts(){

        Map<Integer, Object> results = null;
        try {
            results = manager.getConexion().lecturaSQL(query);

            // Imprimir resultados
            for (Map.Entry<Integer, Object> entry : results.entrySet()) {
                System.out.println("Row: " + entry.getKey() + " Value: " + entry.getValue());

                text.add(entry.getValue().toString());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
