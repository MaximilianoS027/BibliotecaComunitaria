package logica;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

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
    
    @Column(name = "password")
    private String password;
    
    // Constructor por defecto requerido por JPA
    public Usuario() {}
    
    // Constructor con parámetros (sin password)
    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
    
    // Constructor con parámetros incluyendo password
    public Usuario(String id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Verifica si el password proporcionado es correcto
     * @param passwordToCheck Password en texto plano a verificar
     * @return true si el password es correcto
     */
    public boolean verifyPassword(String passwordToCheck) {
        return this.password != null && this.password.equals(passwordToCheck);
    }
    
    /**
     * Verifica si el usuario tiene un password configurado
     * @return true si tiene password
     */
    public boolean hasPassword() {
        return password != null && !password.trim().isEmpty();
    }
    
    // Métodos de negocio
    public boolean tieneNombreValido() {
        return nombre != null && !nombre.trim().isEmpty();
    }
    
    public boolean tieneEmailValido() {
        return email != null && email.contains("@");
    }
    
    public boolean tienePasswordValido() {
        return hasPassword();
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
