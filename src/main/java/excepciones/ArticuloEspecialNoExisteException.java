package excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a un artículo especial que no existe
 */
public class ArticuloEspecialNoExisteException extends Exception {
    
    public ArticuloEspecialNoExisteException(String mensaje) {
        super(mensaje);
    }
    
    public ArticuloEspecialNoExisteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
