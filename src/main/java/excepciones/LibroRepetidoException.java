package excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un libro que ya existe
 */
public class LibroRepetidoException extends Exception {
    
    public LibroRepetidoException(String mensaje) {
        super(mensaje);
    }
    
    public LibroRepetidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
