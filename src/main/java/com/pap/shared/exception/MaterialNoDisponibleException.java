package com.pap.shared.exception;

/**
 * Excepción que se lanza cuando un material no está disponible para préstamo
 */
public class MaterialNoDisponibleException extends RuntimeException {
    
    public MaterialNoDisponibleException(String message) {
        super(message);
    }
    
    public MaterialNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MaterialNoDisponibleException(Throwable cause) {
        super(cause);
    }
}
