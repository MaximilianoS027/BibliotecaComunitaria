package com.pap.domain.model.abst;

import java.util.Objects;

/**
 * Clase abstracta que representa un usuario genérico en el sistema
 */
public abstract class Usuario {
    protected String nombre;
    protected String email;
    
    // Constructor por defecto
    public Usuario() {}
    
    // Constructor con parámetros
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Métodos abstractos que deben ser implementados por las clases hijas
    public abstract String getTipoUsuario();
    
    // Métodos de Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(email, usuario.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", tipo='" + getTipoUsuario() + '\'' +
                '}';
    }
}
