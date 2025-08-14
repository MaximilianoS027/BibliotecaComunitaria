package com.pap.domain.model.concrete;

import com.pap.domain.model.abstract.Usuario;

import java.util.Objects;

/**
 * Clase que representa un bibliotecario o empleado de la biblioteca
 */
public class Bibliotecario extends Usuario {
    private String numeroEmpleado;
    
    // Constructor por defecto
    public Bibliotecario() {}
    
    // Constructor con parámetros
    public Bibliotecario(String nombre, String email, String numeroEmpleado) {
        super(nombre, email);
        this.numeroEmpleado = numeroEmpleado;
    }
    
    // Getters y Setters
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }
    
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }
    
    // Implementación del método abstracto
    @Override
    public String getTipoUsuario() {
        return "Bibliotecario";
    }
    
    // Métodos de negocio
    public boolean tieneNumeroEmpleadoValido() {
        return numeroEmpleado != null && !numeroEmpleado.trim().isEmpty();
    }
    
    // Métodos de Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Bibliotecario bibliotecario = (Bibliotecario) obj;
        return Objects.equals(numeroEmpleado, bibliotecario.numeroEmpleado);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroEmpleado);
    }
    
    @Override
    public String toString() {
        return "Bibliotecario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' +
                '}';
    }
}
