package logica;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Clase abstracta que representa un usuario base en el sistema
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public abstract class Usuario {
    
    @Id
    private String id;
    private String nombre;
    private String email;
    
    // Constructor por defecto requerido por JPA
    public Usuario() {}
    
    // Constructor con parámetros
    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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
    public boolean tieneNombreValido() {
        return nombre != null && !nombre.trim().isEmpty() && nombre.length() >= 2;
    }
    
    public boolean tieneEmailValido() {
        return email != null && email.contains("@") && email.length() >= 5;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
