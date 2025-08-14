package com.pap.domain.model.enums;

/**
 * Enumeración que define los posibles estados de un lector en el sistema
 */
public enum EstadoLector {
    ACTIVO("Activo"),
    SUSPENDIDO("Suspendido");
    
    private final String descripcion;
    
    EstadoLector(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public String toString() {
        return descripcion;
    }
}
