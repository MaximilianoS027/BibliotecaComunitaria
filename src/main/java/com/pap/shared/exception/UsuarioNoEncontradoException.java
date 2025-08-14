package com.pap.shared.exception;

/**
 * Excepción que se lanza cuando un usuario no se encuentra en el sistema
 */
public class UsuarioNoEncontradoException extends RuntimeException {
    
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
    
    public UsuarioNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UsuarioNoEncontradoException(Throwable cause) {
        super(cause);
    }
}
