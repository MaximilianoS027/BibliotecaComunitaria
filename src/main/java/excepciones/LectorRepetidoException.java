package excepciones;

/**
 * Excepción que se lanza cuando se intenta registrar un lector
 * que ya existe en el sistema
 */
public class LectorRepetidoException extends Exception {
    
    public LectorRepetidoException() {
        super("El lector ya existe en el sistema");
    }
    
    public LectorRepetidoException(String message) {
        super(message);
    }
    
    public LectorRepetidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
