package excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un libro que ya existe
 */
public class LibroRepetidoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public LibroRepetidoException(String mensaje) {
        super(mensaje);
    }
}
