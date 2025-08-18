package excepciones;

/**
 * Excepción que se lanza cuando se intenta acceder a un lector
 * que no existe en el sistema
 */
public class LectorNoExisteException extends Exception {
    
    public LectorNoExisteException() {
        super("El lector no existe en el sistema");
    }
    
    public LectorNoExisteException(String message) {
        super(message);
    }
    
    public LectorNoExisteException(String message, Throwable cause) {
        super(message, cause);
    }
}
