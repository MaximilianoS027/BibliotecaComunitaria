package logica;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa un bibliotecario en el sistema
 */
@Entity
@Table(name = "bibliotecarios")
public class Bibliotecario {
    
    @Id
    private String numeroEmpleado;
    private String nombre;
    private String email;
    
    // Constructor por defecto requerido por JPA
    public Bibliotecario() {}
    
    // Constructor con parámetros
    public Bibliotecario(String numeroEmpleado, String nombre, String email) {
        this.numeroEmpleado = numeroEmpleado;
        this.nombre = nombre;
        this.email = email;
    }
    
    // Getters y Setters
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }
    
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }
    
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
    
    // Métodos de negocio
    public boolean tieneNumeroEmpleadoValido() {
        return numeroEmpleado != null && !numeroEmpleado.trim().isEmpty();
    }
    
    public boolean tieneNombreValido() {
        return nombre != null && !nombre.trim().isEmpty() && nombre.length() >= 2;
    }
    
    public boolean tieneEmailValido() {
        return email != null && email.contains("@") && email.length() >= 5;
    }
    
    @Override
    public String toString() {
        return "Bibliotecario{" +
                "numeroEmpleado='" + numeroEmpleado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
