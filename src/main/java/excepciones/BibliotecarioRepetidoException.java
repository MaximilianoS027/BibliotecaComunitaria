package excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un bibliotecario que ya existe
 */
public class BibliotecarioRepetidoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public BibliotecarioRepetidoException(String mensaje) {
        super(mensaje);
    }
}
