package org.example.multiDSC.model.viewModels;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.view.ConfAdminView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
public class ConfAdminModel {
    private Manager manager;
    private String query;
    private ArrayList<String> text = new ArrayList<>();

    public ConfAdminModel(Manager manager){
        this.manager=manager;
        init();
        fillTexts();
    }


    public void init(){
        query= "SELECT "+ manager.getTable() +" FROM \"NOMBRES\" WHERE \"Entrada\" like 'ConfAdminModel'";

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

