package excepciones;

/**
 * Excepción lanzada cuando se intenta crear un préstamo que ya existe
 */
public class PrestamoRepetidoException extends Exception {
    
    public PrestamoRepetidoException(String mensaje) {
        super(mensaje);
    }
    
    public PrestamoRepetidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
