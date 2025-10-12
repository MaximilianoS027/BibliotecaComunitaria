package logica;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un bibliotecario en el sistema
 */
@Entity
@Table(name = "bibliotecarios")
public class Bibliotecario extends Usuario {
    
    private String numeroEmpleado;
    
    // Constructor por defecto requerido por JPA
    public Bibliotecario() {}
    
    // Constructor con parámetros (sin password)
    public Bibliotecario(String numeroEmpleado, String nombre, String email) {
        super(null, nombre, email); // El ID se generará automáticamente por ManejadorBibliotecario
        this.numeroEmpleado = numeroEmpleado;
    }
    
    // Constructor con parámetros incluyendo password
    public Bibliotecario(String numeroEmpleado, String nombre, String email, String password) {
        super(null, nombre, email, password); // El ID se generará automáticamente por ManejadorBibliotecario
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
