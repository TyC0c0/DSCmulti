package org.example.multiDSC.controller.databaseConection;

public class conexionThread extends Thread {

    private final ConectionBD conexion;
    private boolean keepRunning = true;

    public conexionThread(ConectionBD conexion) {

        this.conexion = conexion;
    }

    @Override
    public void run() {
        while (keepRunning) {
            try {
                // Verifica si la conexión está activa; si no, intenta reconectar
                if (!conexion.isConnected()) {
                    System.out.println("Conexión perdida. Intentando reconectar...");
                    conexion.connect();
                }

                // Espera antes de verificar nuevamente
                Thread.sleep(30000); // Reintenta cada 30 segundos

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaura el estado de interrupción
                System.err.println("Hilo interrumpido: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error en la conexión: " + e.getMessage());
                try {
                    Thread.sleep(5000); // Espera antes de intentar nuevamente
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Cierra la conexión al detener el hilo
        conexion.closeConnection();
        System.out.println("Hilo de conexión finalizado.");
    }

    public void stopThread() {
        keepRunning = false;
        this.interrupt(); // Interrumpe el hilo para salir del estado de espera
    }
}

