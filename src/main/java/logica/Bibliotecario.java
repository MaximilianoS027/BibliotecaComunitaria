package logica;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entidad JPA que representa un bibliotecario en el sistema
 */
@Entity
@Table(name = "bibliotecarios")
public class Bibliotecario extends Usuario {
    
    private String numeroEmpleado;
    
    // Constructor por defecto requerido por JPA
    public Bibliotecario() {}
    
    // Constructor con parámetros
    public Bibliotecario(String id, String numeroEmpleado, String nombre, String email) {
        super(id, nombre, email);
        this.numeroEmpleado = numeroEmpleado;
    }
    
    // Constructor con parámetros (sin ID para compatibilidad)
    public Bibliotecario(String numeroEmpleado, String nombre, String email) {
        super(numeroEmpleado, nombre, email); // Usar numeroEmpleado como ID
        this.numeroEmpleado = numeroEmpleado;
    }
    
    // Getters y Setters
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }
    
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }
    
    // Métodos de negocio
    public boolean tieneNumeroEmpleadoValido() {
        return numeroEmpleado != null && !numeroEmpleado.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return "Bibliotecario{" +
                "id='" + getId() + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
