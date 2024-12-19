package org.example;

import org.example.multiDSC.controller.Utils;
import org.example.multiDSC.controller.MainController;

/**
 * Clase principal que inicia la aplicación llamando al mainControler.
 *
 * <p>Esta clase actúa como el punto de entrada para la ejecución de la aplicación.
 * Inicializa el controlador principal y gestiona errores potenciales que pueden
 * surgir durante la inicialización.</p>
 *
 * <h2>Uso de la clase Main</h2>
 *
 * <h2>Consideraciones</h2>
 * <ul>
 *   <li>Asegúrate de que todos los recursos externos necesarios estén disponibles (como bases de datos o servicios de red).</li>
 *   <li>El programa maneja excepciones críticas y muestra un mensaje de error al usuario en caso de fallo.</li>
 * </ul>
 *
 * @author Isaac Requena Santiago
 * @version 1.0
 * @since 2024-12-18
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     *
     * <p>Este método inicializa el mainController y maneja excepciones que puedan ocurrir
     * durante la ejecución. Si ocurre un error grave, muestra un mensaje al usuario.</p>
     *
     * @param args argumentos pasados, no utilizados actualmente.
     */
    public static void main(String[] args) {
        try {

            // Inicialización del controlador principal
            MainController controller = new MainController();
            System.out.println("Programa inicializado correctamente.");

        } catch (RuntimeException e) {
            // Manejo de errores en tiempo de ejecución
            System.err.println("Error al iniciar el programa: " + e.getMessage());
            Utils.showErrorWindow(null, "Error grave: " + e.getMessage(), "Error de Conexión");
        } catch (Exception e) {
            // Captura de errores generales no previstos
            System.err.println("Se produjo un error inesperado: " + e.getMessage());
            Utils.showErrorWindow(null, "Error inesperado: " + e.getMessage(), "Error Desconocido");
        }
    }
}
