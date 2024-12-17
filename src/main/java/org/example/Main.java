package org.example;

import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.controller.mainController;

/**
 * Main - Main class that calls the Controller of the app.
 *
 * @author Isaac Requena Santiago
 * @version 1.0
 */

public class Main {
    public static void main(String[] args) {
        try {
            mainController controller = new mainController();

            System.out.println("Programa inicializado correctamente.");
        } catch (RuntimeException e) {
            System.err.println("Error al iniciar el programa: " + e.getMessage());
            Utils.showErrorWindow(null, "Error grave: " + e.getMessage(), "Error de Conexión");
        }
    }

}