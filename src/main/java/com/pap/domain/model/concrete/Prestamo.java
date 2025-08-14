package com.pap.domain.model.concrete;

import com.pap.domain.model.abstract.Material;
import com.pap.domain.model.abstract.Usuario;
import com.pap.domain.model.enums.EstadoPrestamo;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un préstamo en la biblioteca
 */
public class Prestamo {
    private String id;
    private Usuario lector;
    private Usuario bibliotecario;
    private List<Material> materiales;
    private Date fechaSolicitud;
    private Date fechaDevolucion;
    private EstadoPrestamo estado;
    
    // Constructor por defecto
    public Prestamo() {}
    
    // Constructor con parámetros
    public Prestamo(String id, Usuario lector, Usuario bibliotecario, List<Material> materiales, 
                    Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado) {
        this.id = id;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.materiales = materiales;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Usuario getLector() {
        return lector;
    }
    
    public void setLector(Usuario lector) {
        this.lector = lector;
    }
    
    public Usuario getBibliotecario() {
        return bibliotecario;
    }
    
    public void setBibliotecario(Usuario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }
    
    public List<Material> getMateriales() {
        return materiales;
    }
    
    public void setMateriales(List<Material> materiales) {
        this.materiales = materiales;
    }
    
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public EstadoPrestamo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    // Métodos de negocio
    public boolean estaPendiente() {
        return EstadoPrestamo.PENDIENTE.equals(estado);
    }
    
    public boolean estaEnCurso() {
        return EstadoPrestamo.EN_CURSO.equals(estado);
    }
    
    public boolean estaDevuelto() {
        return EstadoPrestamo.DEVUELTO.equals(estado);
    }
    
    public boolean tieneMateriales() {
        return materiales != null && !materiales.isEmpty();
    }
    
    public int getCantidadMateriales() {
        return materiales != null ? materiales.size() : 0;
    }
    
    // Métodos de Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Prestamo prestamo = (Prestamo) obj;
        return Objects.equals(id, prestamo.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Prestamo{" +
                "id='" + id + '\'' +
                ", lector=" + (lector != null ? lector.getNombre() : "null") +
                ", bibliotecario=" + (bibliotecario != null ? bibliotecario.getNombre() : "null") +
                ", materiales=" + getCantidadMateriales() +
                ", fechaSolicitud=" + fechaSolicitud +
                ", fechaDevolucion=" + fechaDevolucion +
                ", estado=" + estado +
                '}';
    }
}
