package excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a un préstamo que no existe
 */
public class PrestamoNoExisteException extends Exception {
    
    public PrestamoNoExisteException(String mensaje) {
        super(mensaje);
    }
    
    public PrestamoNoExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
