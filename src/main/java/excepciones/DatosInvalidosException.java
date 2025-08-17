package excepciones;

/**
 * Excepción lanzada cuando se proporcionan datos inválidos
 */
public class DatosInvalidosException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
