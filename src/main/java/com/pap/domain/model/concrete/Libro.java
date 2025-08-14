package com.pap.domain.model.concrete;

import com.pap.domain.model.abstract.Material;

import java.util.Date;
import java.util.Objects;

/**
 * Clase que representa un libro en la biblioteca
 */
public class Libro extends Material {
    private String titulo;
    private String cantidadPaginas;
    
    // Constructor por defecto
    public Libro() {}
    
    // Constructor con parámetros
    public Libro(String id, Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getCantidadPaginas() {
        return cantidadPaginas;
    }
    
    public void setCantidadPaginas(String cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
    
    // Implementación de métodos abstractos
    @Override
    public String getTipoMaterial() {
        return "Libro";
    }
    
    @Override
    public String getDescripcionCompleta() {
        return "Libro: " + titulo + " (" + cantidadPaginas + " páginas)";
    }
    
    // Métodos de negocio
    public boolean tieneTituloValido() {
        return titulo != null && !titulo.trim().isEmpty();
    }
    
    public boolean tienePaginasValidas() {
        return cantidadPaginas != null && !cantidadPaginas.trim().isEmpty();
    }
    
    // Métodos de Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Libro libro = (Libro) obj;
        return Objects.equals(id, libro.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", titulo='" + titulo + '\'' +
                ", cantidadPaginas='" + cantidadPaginas + '\'' +
                '}';
    }
}
