package com.pap.domain.model.concrete;

import com.pap.domain.model.abst.Usuario;
import com.pap.domain.model.enums.EstadoLector;
import com.pap.domain.model.enums.Zona;

import java.util.Date;
import java.util.Objects;

/**
 * Clase que representa un lector o usuario de la biblioteca
 */
public class Lector extends Usuario {
    private String direccion;
    private Date fechaRegistro;
    private EstadoLector estado;
    private Zona zona;
    
    // Constructor por defecto
    public Lector() {}
    
    // Constructor con parámetros
    public Lector(String nombre, String email, String direccion, Date fechaRegistro, EstadoLector estado, Zona zona) {
        super(nombre, email);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }
    
    // Getters y Setters
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public EstadoLector getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoLector estado) {
        this.estado = estado;
    }
    
    public Zona getZona() {
        return zona;
    }
    
    public void setZona(Zona zona) {
        this.zona = zona;
    }
    
    // Implementación del método abstracto
    @Override
    public String getTipoUsuario() {
        return "Lector";
    }
    
    // Métodos de negocio
    public boolean estaActivo() {
        return EstadoLector.ACTIVO.equals(estado);
    }
    
    public boolean estaSuspendido() {
        return EstadoLector.SUSPENDIDO.equals(estado);
    }
    
    // Métodos de Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Lector lector = (Lector) obj;
        return Objects.equals(email, lector.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }
    
    @Override
    public String toString() {
        return "Lector{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estado=" + estado +
                ", zona=" + zona +
                '}';
    }
}
