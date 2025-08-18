package datatypes;

import logica.EstadoLector;
import logica.Zona;
import java.util.Date;

/**
 * DTO para transferir datos de lectores
 */
public class DtLector {
    
    private String id;
    private String nombre;
    private String email;
    private String direccion;
    private Date fechaRegistro;
    private EstadoLector estado;
    private Zona zona;
    
    // Constructor por defecto
    public DtLector() {}
    
    // Constructor con parámetros
    public DtLector(String id, String nombre, String email, String direccion, 
                    Date fechaRegistro, EstadoLector estado, Zona zona) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
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
    
    @Override
    public String toString() {
        return "DtLector{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estado=" + estado +
                ", zona=" + zona +
                '}';
    }
}
