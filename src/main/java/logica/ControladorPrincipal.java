package logica;

import interfaces.IBibliotecarioControlador;
import interfaces.ILectorControlador;

/**
 * Controlador principal que facilita el acceso a los controladores específicos
 * Implementa el patrón Facade para simplificar el acceso a los controladores
 */
public class ControladorPrincipal {
    
    private static ControladorPrincipal instancia;
    private IBibliotecarioControlador bibliotecarioControlador;
    private ILectorControlador lectorControlador;
    
    private ControladorPrincipal() {
        this.bibliotecarioControlador = new BibliotecarioControlador();
        this.lectorControlador = new LectorControlador();
    }
    
    /**
     * Obtiene la instancia única del controlador principal (Singleton)
     * @return La instancia del controlador principal
     */
    public static synchronized ControladorPrincipal getInstancia() {
        if (instancia == null) {
            instancia = new ControladorPrincipal();
        }
        return instancia;
    }
    
    /**
     * Obtiene el controlador específico para bibliotecarios
     * @return Controlador de bibliotecarios
     */
    public IBibliotecarioControlador getBibliotecarioControlador() {
        return bibliotecarioControlador;
    }
    
    /**
     * Obtiene el controlador específico para lectores
     * @return Controlador de lectores
     */
    public ILectorControlador getLectorControlador() {
        return lectorControlador;
    }
}
