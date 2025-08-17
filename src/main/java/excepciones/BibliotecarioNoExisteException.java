package excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a un bibliotecario que no existe
 */
public class BibliotecarioNoExisteException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public BibliotecarioNoExisteException(String mensaje) {
        super(mensaje);
    }
}
