package com.pap.domain.model.concrete;

import com.pap.domain.model.abstract.Material;

import java.util.Date;
import java.util.Objects;

/**
 * Clase que representa un artículo general en la biblioteca
 */
public class Articulo extends Material {
    private String descripcion;
    private float pesoKg;
    private String dimensiones;
    
    // Constructor por defecto
    public Articulo() {}
    
    // Constructor con parámetros
    public Articulo(String id, Date fechaIngreso, String descripcion, float pesoKg, String dimensiones) {
        super(id, fechaIngreso);
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
    }
    
    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public float getPesoKg() {
        return pesoKg;
    }
    
    public void setPesoKg(float pesoKg) {
        this.pesoKg = pesoKg;
    }
    
    public String getDimensiones() {
        return dimensiones;
    }
    
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    // Implementación de métodos abstractos
    @Override
    public String getTipoMaterial() {
        return "Artículo";
    }
    
    @Override
    public String getDescripcionCompleta() {
        return "Artículo: " + descripcion + " - Peso: " + pesoKg + " kg, Dimensiones: " + dimensiones;
    }
    
    // Métodos de negocio
    public boolean tienePesoValido() {
        return pesoKg > 0;
    }
    
    public boolean tieneDescripcionValida() {
        return descripcion != null && !descripcion.trim().isEmpty();
    }
    
    public boolean tieneDimensionesValidas() {
        return dimensiones != null && !dimensiones.trim().isEmpty();
    }
    
    // Métodos de Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Articulo articulo = (Articulo) obj;
        return Objects.equals(id, articulo.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
    
    @Override
    public String toString() {
        return "Articulo{" +
                "id='" + id + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", descripcion='" + descripcion + '\'' +
                ", pesoKg=" + pesoKg +
                ", dimensiones='" + dimensiones + '\'' +
                '}';
    }
}
