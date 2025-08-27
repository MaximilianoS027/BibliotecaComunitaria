package logica;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Entidad JPA que representa un lector en el sistema
 */
@Entity
@Table(name = "lectores")
public class Lector extends Usuario {
    
    private String direccion;
    
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    
    @Enumerated(EnumType.STRING)
    private EstadoLector estado;
    
    @Enumerated(EnumType.STRING)
    private Zona zona;
    
    // Constructor por defecto requerido por JPA
    public Lector() {}
    
    // Constructor con parámetros
    public Lector(String id, String nombre, String email, String direccion, 
                  Date fechaRegistro, EstadoLector estado, Zona zona) {
        super(id, nombre, email);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }
    
    // Constructor con parámetros (sin ID para que ManejadorLector lo genere secuencialmente)
    public Lector(String nombre, String email, String direccion, 
                  Date fechaRegistro, EstadoLector estado, Zona zona) {
        super(null, nombre, email);  // Pasar null para que ManejadorLector genere ID secuencial (L1, L2, L3...)
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
    
    // Métodos de negocio
    public boolean tieneDireccionValida() {
        return direccion != null && !direccion.trim().isEmpty() && direccion.length() >= 5;
    }
    
    public boolean tieneFechaRegistroValida() {
        return fechaRegistro != null && fechaRegistro.before(new Date());
    }
    
    public boolean esActivo() {
        return EstadoLector.ACTIVO.equals(estado);
    }
    
    @Override
    public String toString() {
        return "Lector{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estado=" + estado +
                ", zona=" + zona +
                '}';
    }
}
