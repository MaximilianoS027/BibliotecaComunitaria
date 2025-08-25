package excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a un libro que no existe
 */
public class LibroNoExisteException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public LibroNoExisteException(String mensaje) {
        super(mensaje);
    }
}
