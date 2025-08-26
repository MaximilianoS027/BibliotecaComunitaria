package excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a un libro que no existe
 */
public class LibroNoExisteException extends Exception {
    
    public LibroNoExisteException(String mensaje) {
        super(mensaje);
    }
    
    public LibroNoExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
