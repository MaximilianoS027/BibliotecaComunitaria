package excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un artículo especial que ya existe
 * Utilizada para implementar idempotencia "suave"
 */
public class ArticuloEspecialRepetidoException extends Exception {
    
    public ArticuloEspecialRepetidoException(String mensaje) {
        super(mensaje);
    }
    
    public ArticuloEspecialRepetidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
