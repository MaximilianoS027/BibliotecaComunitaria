package logica;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Column;

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
    
    @Column(name = "password_hash")
    private String passwordHash;
    
    @Column(name = "password_salt")
    private String passwordSalt;
    
    // Constructor por defecto requerido por JPA
    public Usuario() {}
    
    // Constructor con parámetros
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
        if (password != null && !password.trim().isEmpty()) {
            this.passwordSalt = PasswordUtil.generateSalt();
            this.passwordHash = PasswordUtil.hashPassword(password, this.passwordSalt);
        }
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
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getPasswordSalt() {
        return passwordSalt;
    }
    
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    
    /**
     * Establece el password del usuario (genera hash y salt automáticamente)
     * @param password Password en texto plano
     */
    public void setPassword(String password) {
        if (password != null && !password.trim().isEmpty()) {
            this.passwordSalt = PasswordUtil.generateSalt();
            this.passwordHash = PasswordUtil.hashPassword(password, this.passwordSalt);
        }
    }
    
    /**
     * Verifica si el password proporcionado es correcto
     * @param password Password en texto plano
     * @return true si el password es correcto
     */
    public boolean verifyPassword(String password) {
        if (passwordHash == null || passwordSalt == null || password == null) {
            return false;
        }
        return PasswordUtil.verifyPassword(password, passwordHash, passwordSalt);
    }
    
    /**
     * Verifica si el usuario tiene un password configurado
     * @return true si tiene password
     */
    public boolean hasPassword() {
        return passwordHash != null && passwordSalt != null;
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
