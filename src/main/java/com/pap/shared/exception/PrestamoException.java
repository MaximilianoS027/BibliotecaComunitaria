package com.pap.shared.exception;

/**
 * Excepción que se lanza cuando ocurre un error en las operaciones de préstamo
 */
public class PrestamoException extends RuntimeException {
    
    public PrestamoException(String message) {
        super(message);
    }
    
    public PrestamoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PrestamoException(Throwable cause) {
        super(cause);
    }
}
