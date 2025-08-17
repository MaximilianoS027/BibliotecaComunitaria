package interfaces;

import logica.Controlador;

/**
 * Fábrica Singleton para crear instancias del controlador
 */
public class Fabrica {
    private static Fabrica instancia = null;
    private static IControlador controlador = null;
    
    private Fabrica() {}
    
    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }
    
    public IControlador getIControlador() {
        if (controlador == null) {
            controlador = new Controlador();
        }
        return controlador;
    }
}
