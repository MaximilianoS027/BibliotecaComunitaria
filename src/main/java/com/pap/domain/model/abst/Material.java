package com.pap.domain.model.abst;

import java.util.Date;
import java.util.Objects;

/**
 * Clase abstracta que representa cualquier material que puede ser prestado en la biblioteca
 */
public abstract class Material {
    protected String id;
    protected Date fechaIngreso;
    
    // Constructor por defecto
    public Material() {}
    
    // Constructor con parámetros
    public Material(String id, Date fechaIngreso) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    // Métodos abstractos que deben ser implementados por las clases hijas
    public abstract String getTipoMaterial();
    public abstract String getDescripcionCompleta();
    
    // Métodos de Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Material material = (Material) obj;
        return Objects.equals(id, material.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Material{" +
                "id='" + id + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", tipo='" + getTipoMaterial() + '\'' +
                '}';
    }
}
