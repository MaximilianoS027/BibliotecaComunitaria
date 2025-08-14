package com.pap.domain.model.enums;

/**
 * Enumeración que define los posibles estados de un préstamo en el sistema
 */
public enum EstadoPrestamo {
    PENDIENTE("Pendiente"),
    EN_CURSO("En Curso"),
    DEVUELTO("Devuelto");
    
    private final String descripcion;
    
    EstadoPrestamo(String descripcion) {
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
