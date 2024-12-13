package org.example.multiDSC.controller.databaseConection;

public class conexionThread extends Thread{

//    private boolean keepRunning = true;
//
//    public ConexionThread(Conexion conexion) {
//        this.conexion = conexion;
//    }
//
//    @Override
//    public void run() {
//        while (keepRunning) {
//            try {
//                // Verifica si la conexión está activa, si no, intenta conectar
//                if (!conexion.estaConectado()) {
//                    conexion.conectar();
//                }
//
//                // Mantén la conexión activa y espera antes de verificar nuevamente
//                Thread.sleep(5000); // Verifica cada 5 segundos
//            } catch (SQLException e) {
//                System.err.println("Error en la conexión: " + e.getMessage());
//                System.out.println("Intentando reconectar en 5 segundos...");
//                try {
//                    Thread.sleep(5000); // Espera antes de intentar reconectar
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt(); // Restaura el estado de interrupción
//                    System.err.println("Hilo interrumpido: " + ie.getMessage());
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt(); // Restaura el estado de interrupción
//                System.err.println("Hilo interrumpido: " + e.getMessage());
//            }
//        }
//
//        // Finaliza la conexión cuando el hilo se detiene
//        conexion.finConexion();
//        System.out.println("Hilo de conexión finalizado.");
//    }
//
//    public void detenerHilo() {
//        keepRunning = false;
//
//
}
